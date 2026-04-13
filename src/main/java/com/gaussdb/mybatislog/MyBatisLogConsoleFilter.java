package com.gaussdb.mybatislog;

import com.intellij.execution.filters.ConsoleInputFilterProvider;
import com.intellij.execution.filters.InputFilter;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.gaussdb.mybatislog.gui.MyBatisLogManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBatisLogConsoleFilter implements Filter, InputFilter {
    
    public static final String PREPARING_KEY = "MyBatisGaussDBLog.Preparing";
    public static final String PARAMETERS_KEY = "MyBatisGaussDBLog.Parameters";
    public static final String KEYWORDS_KEY = "MyBatisGaussDBLog.Keywords";
    
    private static final Pattern PREPARING_PATTERN = Pattern.compile(".*Preparing: (.+)");
    private static final Pattern PARAMETERS_PATTERN = Pattern.compile(".*Parameters: (.+)");
    
    private final Project project;
    private String preparingSql;
    private List<String> parameters = new ArrayList<>();
    
    public MyBatisLogConsoleFilter(Project project) {
        this.project = project;
    }
    
    @Nullable
    @Override
    public Result applyFilter(@NotNull String line, int entireLength) {
        MyBatisLogManager manager = MyBatisLogManager.getInstance(project);
        if (manager == null || !manager.isRunning()) {
            return null;
        }
        
        Matcher preparingMatcher = PREPARING_PATTERN.matcher(line);
        if (preparingMatcher.matches()) {
            preparingSql = preparingMatcher.group(1);
            parameters.clear();
            return null;
        }
        
        Matcher parametersMatcher = PARAMETERS_PATTERN.matcher(line);
        if (parametersMatcher.matches()) {
            String paramsStr = parametersMatcher.group(1);
            parseParameters(paramsStr);
            
            if (preparingSql != null && !parameters.isEmpty()) {
                String restoredSql = restoreSql(preparingSql, parameters);
                manager.println(restoredSql);
                preparingSql = null;
                parameters.clear();
            }
            return null;
        }
        
        return null;
    }
    
    private void parseParameters(String paramsStr) {
        parameters.clear();
        String[] parts = paramsStr.split("(?=\\s*\\([^)]+\\)\\s*\\(|\\s*\\([^)]+\\)\\s*$)");
        for (String part : parts) {
            part = part.trim();
            if (!part.isEmpty()) {
                int parenStart = part.indexOf('(');
                int parenEnd = part.lastIndexOf(')');
                if (parenStart >= 0 && parenEnd > parenStart) {
                    String value = part.substring(parenStart + 1, parenEnd).trim();
                    String type = part.substring(parenEnd + 1).trim();
                    parameters.add(value);
                } else {
                    parameters.add(part);
                }
            }
        }
    }
    
    private String restoreSql(String sql, List<String> params) {
        String result = sql;
        int paramIndex = 0;
        
        while (result.contains("?") && paramIndex < params.size()) {
            String param = params.get(paramIndex);
            String replacement = formatParameter(param);
            result = result.replaceFirst("\\?", replacement);
            paramIndex++;
        }
        
        return result;
    }
    
    private String formatParameter(String param) {
        if (param == null || param.equals("null")) {
            return "NULL";
        }
        
        if (param.matches("-?\\d+(\\.\\d+)?")) {
            return param;
        }
        
        if (param.startsWith("'") && param.endsWith("'")) {
            return param;
        }
        
        return "'" + param.replace("'", "''") + "'";
    }
    
    @NotNull
    @Override
    public List<String> applyFilter(@NotNull String text, @NotNull ConsoleViewContentType contentType) {
        List<String> result = new ArrayList<>();
        result.add(text);
        return result;
    }
}