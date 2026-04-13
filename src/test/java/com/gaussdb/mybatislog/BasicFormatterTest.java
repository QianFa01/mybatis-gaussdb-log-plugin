package com.gaussdb.mybatislog;

import org.junit.Test;
import static org.junit.Assert.*;

public class BasicFormatterTest {
    
    @Test
    public void testFormatSelect() {
        BasicFormatter formatter = new BasicFormatter();
        String sql = "SELECT id, name FROM users WHERE id = 1";
        String result = formatter.format(sql);
        
        assertTrue(result.contains("SELECT"));
        assertTrue(result.contains("FROM"));
        assertTrue(result.contains("WHERE"));
    }
    
    @Test
    public void testFormatInsert() {
        BasicFormatter formatter = new BasicFormatter();
        String sql = "INSERT INTO users (id, name) VALUES (1, 'test')";
        String result = formatter.format(sql);
        
        assertTrue(result.contains("INSERT"));
        assertTrue(result.contains("VALUES"));
    }
    
    @Test
    public void testFormatUpdate() {
        BasicFormatter formatter = new BasicFormatter();
        String sql = "UPDATE users SET name = 'test' WHERE id = 1";
        String result = formatter.format(sql);
        
        assertTrue(result.contains("UPDATE"));
        assertTrue(result.contains("SET"));
        assertTrue(result.contains("WHERE"));
    }
    
    @Test
    public void testFormatDelete() {
        BasicFormatter formatter = new BasicFormatter();
        String sql = "DELETE FROM users WHERE id = 1";
        String result = formatter.format(sql);
        
        assertTrue(result.contains("DELETE"));
        assertTrue(result.contains("FROM"));
        assertTrue(result.contains("WHERE"));
    }
}