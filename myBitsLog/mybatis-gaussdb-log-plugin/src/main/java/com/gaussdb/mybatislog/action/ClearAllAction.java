package com.gaussdb.mybatislog.action;

import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

public class ClearAllAction extends DumbAwareAction {
    
    private final ConsoleViewImpl consoleView;
    
    public ClearAllAction(ConsoleViewImpl consoleView) {
        this.consoleView = consoleView;
    }
    
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        consoleView.clear();
    }
}