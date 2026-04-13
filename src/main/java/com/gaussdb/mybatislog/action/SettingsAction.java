package com.gaussdb.mybatislog.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.gaussdb.mybatislog.gui.MyBatisLogManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SettingsAction extends DumbAwareAction {
    
    private final MyBatisLogManager manager;
    
    public SettingsAction(MyBatisLogManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (Objects.isNull(project)) {
            return;
        }
        
        com.intellij.ide.util.PropertiesComponent props = 
            com.intellij.ide.util.PropertiesComponent.getInstance(project);
        
        String preparingPrefix = Messages.showInputDialog(
            project, 
            "Preparing prefix (e.g., 'Preparing: '):", 
            "MyBatis GaussDB Log Settings",
            Messages.getInformationIcon(),
            props.getValue(com.gaussdb.mybatislog.MyBatisLogConsoleFilter.PREPARING_KEY, "Preparing: "),
            null);
        
        if (preparingPrefix != null) {
            props.setValue(com.gaussdb.mybatislog.MyBatisLogConsoleFilter.PREPARING_KEY, preparingPrefix);
            manager.setPreparing(preparingPrefix);
        }
        
        String parametersPrefix = Messages.showInputDialog(
            project,
            "Parameters prefix (e.g., 'Parameters: '):",
            "MyBatis GaussDB Log Settings",
            Messages.getInformationIcon(),
            props.getValue(com.gaussdb.mybatislog.MyBatisLogConsoleFilter.PARAMETERS_KEY, "Parameters: "),
            null);
        
        if (parametersPrefix != null) {
            props.setValue(com.gaussdb.mybatislog.MyBatisLogConsoleFilter.PARAMETERS_KEY, parametersPrefix);
            manager.setParameters(parametersPrefix);
        }
    }
}