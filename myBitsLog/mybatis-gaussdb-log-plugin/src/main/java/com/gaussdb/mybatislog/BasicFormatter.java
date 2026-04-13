package com.gaussdb.mybatislog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicFormatter {
    
    private static final List<TokenRule> TOKEN_RULES = new ArrayList<>();
    
    static {
        TOKEN_RULES.add(new TokenRule("(?i)^SELECT\\b", TokenType.SELECT));
        TOKEN_RULES.add(new TokenRule("(?i)^FROM\\b", TokenType.FROM));
        TOKEN_RULES.add(new TokenRule("(?i)^WHERE\\b", TokenType.WHERE));
        TOKEN_RULES.add(new TokenRule("(?i)^AND\\b", TokenType.AND));
        TOKEN_RULES.add(new TokenRule("(?i)^OR\\b", TokenType.OR));
        TOKEN_RULES.add(new TokenRule("(?i)^ORDER\\b", TokenType.ORDER));
        TOKEN_RULES.add(new TokenRule("(?i)^GROUP\\b", TokenType.GROUP));
        TOKEN_RULES.add(new TokenRule("(?i)^HAVING\\b", TokenType.HAVING));
        TOKEN_RULES.add(new TokenRule("(?i)^LIMIT\\b", TokenType.LIMIT));
        TOKEN_RULES.add(new TokenRule("(?i)^OFFSET\\b", TokenType.OFFSET));
        TOKEN_RULES.add(new TokenRule("(?i)^INSERT\\b", TokenType.INSERT));
        TOKEN_RULES.add(new TokenRule("(?i)^UPDATE\\b", TokenType.UPDATE));
        TOKEN_RULES.add(new TokenRule("(?i)^DELETE\\b", TokenType.DELETE));
        TOKEN_RULES.add(new TokenRule("(?i)^SET\\b", TokenType.SET));
        TOKEN_RULES.add(new TokenRule("(?i)^VALUES\\b", TokenType.VALUES));
        TOKEN_RULES.add(new TokenRule("(?i)^JOIN\\b", TokenType.JOIN));
        TOKEN_RULES.add(new TokenRule("(?i)^LEFT\\b", TokenType.LEFT));
        TOKEN_RULES.add(new TokenRule("(?i)^RIGHT\\b", TokenType.RIGHT));
        TOKEN_RULES.add(new TokenRule("(?i)^INNER\\b", TokenType.INNER));
        TOKEN_RULES.add(new TokenRule("(?i)^ON\\b", TokenType.ON));
        TOKEN_RULES.add(new TokenRule("(?i)^AS\\b", TokenType.AS));
        TOKEN_RULES.add(new TokenRule("(?i)^DISTINCT\\b", TokenType.DISTINCT));
        TOKEN_RULES.add(new TokenRule("(?i)^UNION\\b", TokenType.UNION));
        TOKEN_RULES.add(new TokenRule("(?i)^CREATE\\b", TokenType.CREATE));
        TOKEN_RULES.add(new TokenRule("(?i)^ALTER\\b", TokenType.ALTER));
        TOKEN_RULES.add(new TokenRule("(?i)^DROP\\b", TokenType.DROP));
        TOKEN_RULES.add(new TokenRule("(?i)^TRUNCATE\\b", TokenType.TRUNCATE));
        TOKEN_RULES.add(new TokenRule("(?i)^GRANT\\b", TokenType.GRANT));
        TOKEN_RULES.add(new TokenRule("(?i)^REVOKE\\b", TokenType.REVOKE));
        TOKEN_RULES.add(new TokenRule("(?i)^EXECUTE\\b", TokenType.EXECUTE));
        TOKEN_RULES.add(new TokenRule("(?i)^CALL\\b", TokenType.CALL));
    }

    public String format(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            return sql;
        }
        
        StringBuilder result = new StringBuilder();
        String trimmedSql = sql.trim();
        int indentLevel = 0;
        boolean isNewLine = true;
        int i = 0;
        
        while (i < trimmedSql.length()) {
            String remaining = trimmedSql.substring(i);
            boolean matched = false;
            
            for (TokenRule rule : TOKEN_RULES) {
                Matcher matcher = rule.pattern.matcher(remaining);
                if (matcher.find()) {
                    String keyword = matcher.group();
                    
                    if (shouldNewline(rule.type, indentLevel)) {
                        result.append("\n");
                        result.append(getIndent(indentLevel));
                        isNewLine = true;
                    } else if (!isNewLine) {
                        result.append(" ");
                    }
                    
                    result.append(keyword);
                    i += keyword.length();
                    
                    indentLevel = adjustIndent(rule.type, indentLevel);
                    isNewLine = false;
                    matched = true;
                    break;
                }
            }
            
            if (!matched) {
                if (remaining.startsWith("(")) {
                    result.append("(");
                    i++;
                    indentLevel++;
                } else if (remaining.startsWith(")")) {
                    indentLevel--;
                    result.append(")");
                    i++;
                } else if (remaining.startsWith(",")) {
                    result.append(",");
                    i++;
                    if (isNewLine) {
                        result.append("\n");
                        result.append(getIndent(indentLevel));
                    }
                } else if (remaining.startsWith(";")) {
                    result.append(";");
                    i++;
                    result.append("\n");
                    isNewLine = true;
                    indentLevel = 0;
                } else {
                    char c = remaining.charAt(0);
                    if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                        i++;
                    } else {
                        result.append(c);
                        i++;
                        isNewLine = false;
                    }
                }
            }
        }
        
        return result.toString();
    }
    
    private boolean shouldNewline(TokenType type, int indentLevel) {
        return type == TokenType.SELECT 
            || type == TokenType.FROM
            || type == TokenType.WHERE
            || type == TokenType.ORDER
            || type == TokenType.GROUP
            || type == TokenType.HAVING
            || type == TokenType.LIMIT
            || type == TokenType.OFFSET
            || type == TokenType.INSERT
            || type == TokenType.UPDATE
            || type == TokenType.DELETE
            || type == TokenType.SET
            || type == TokenType.VALUES
            || type == TokenType.JOIN
            || type == TokenType.LEFT
            || type == TokenType.RIGHT
            || type == TokenType.INNER
            || type == TokenType.UNION
            || type == TokenType.CREATE
            || type == TokenType.ALTER
            || type == TokenType.DROP
            || type == TokenType.TRUNCATE
            || type == TokenType.GRANT
            || type == TokenType.REVOKE;
    }
    
    private int adjustIndent(TokenType type, int currentIndent) {
        if (type == TokenType.WHERE || type == TokenType.ORDER || type == TokenType.GROUP 
            || type == TokenType.HAVING || type == TokenType.SET || type == TokenType.VALUES) {
            return currentIndent + 1;
        }
        return currentIndent;
    }
    
    private String getIndent(int level) {
        return "  ".repeat(Math.max(0, level));
    }
    
    private enum TokenType {
        SELECT, FROM, WHERE, AND, OR, ORDER, GROUP, HAVING, LIMIT, OFFSET,
        INSERT, UPDATE, DELETE, SET, VALUES, JOIN, LEFT, RIGHT, INNER, ON, AS, DISTINCT, UNION,
        CREATE, ALTER, DROP, TRUNCATE, GRANT, REVOKE, EXECUTE, CALL
    }
    
    private static class TokenRule {
        final Pattern pattern;
        final TokenType type;
        
        TokenRule(String regex, TokenType type) {
            this.pattern = Pattern.compile(regex);
            this.type = type;
        }
    }
}