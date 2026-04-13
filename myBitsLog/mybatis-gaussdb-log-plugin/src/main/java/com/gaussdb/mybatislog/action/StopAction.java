package com.gaussdb.mybatislog.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.gaussdb.mybatislog.gui.MyBatisLogManager;
import org.jetbrains.annotations.NotNull;

public class StopAction extends DumbAwareAction {
    
    private final MyBatisLogManager manager;
    
    public StopAction(MyBatisLogManager manager) {
        this.manager = manager;
    }
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        manager.stop();
    }
    
    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(manager.isRunning());
    }
}