package com.gaussdb.mybatislog.gui;

import com.intellij.execution.Executor;
import com.intellij.execution.ExecutorRegistry;
import com.intellij.openapi.util.IconLoader;
import com.gaussdb.mybatislog.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class MyBatisLogExecutor extends Executor {
    
    public static final String TOOL_WINDOW_ID = "MyBatisGaussDBLog";
    private static final String EXECUTOR_ID = "MyBatisGaussDBLogExecutor";
    
    public static MyBatisLogExecutor getInstance() {
        return ExecutorRegistry.getInstance().getExecutorById(EXECUTOR_ID);
    }
    
    @NotNull
    @Override
    public String getToolWindowId() {
        return TOOL_WINDOW_ID;
    }
    
    @NotNull
    @Override
    public Icon getToolWindowIcon() {
        return Icons.GAUSSDB;
    }
    
    @NotNull
    @Override
    public Icon getIcon() {
        return Icons.GAUSSDB;
    }
    
    @NotNull
    @Override
    public String getIconPath() {
        return "/icons/gaussdb.svg";
    }
    
    @NotNull
    @Override
    public String getId() {
        return EXECUTOR_ID;
    }
    
    @NotNull
    @Override
    public String getActionName() {
        return "MyBatis GaussDB Log";
    }
    
    @NotNull
    @Override
    public String getStartActionText() {
        return "Start MyBatis GaussDB Log";
    }
    
    @Nullable
    @Override
    public String getHelpId() {
        return null;
    }
    
    @Override
    public String toString() {
        return getId();
    }
}