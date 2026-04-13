package com.gaussdb.mybatislog.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.gaussdb.mybatislog.gui.MyBatisLogManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RerunAction extends DumbAwareAction {
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        final Project project = e.getProject();
        if (Objects.isNull(project)) {
            return;
        }
        
        MyBatisLogManager manager = MyBatisLogManager.getInstance(project);
        if (Objects.nonNull(manager)) {
            com.intellij.openapi.util.Disposer.dispose(manager);
        }
        MyBatisLogManager.createInstance(project).run();
    }
}