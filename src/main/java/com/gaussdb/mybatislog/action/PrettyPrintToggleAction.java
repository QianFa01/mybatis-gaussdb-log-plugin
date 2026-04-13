package com.gaussdb.mybatislog.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;

public class PrettyPrintToggleAction extends ToggleAction implements DumbAware {
    
    @Override
    public boolean isSelected(@NotNull AnActionEvent e) {
        return e.getProject() != null && 
            com.intellij.ide.util.PropertiesComponent.getInstance(e.getProject())
                .getBoolean(PrettyPrintToggleAction.class.getName(), true);
    }
    
    @Override
    public void setSelected(@NotNull AnActionEvent e, boolean state) {
        if (e.getProject() != null) {
            com.intellij.ide.util.PropertiesComponent.getInstance(e.getProject())
                .setValue(PrettyPrintToggleAction.class.getName(), state, true);
        }
    }
}