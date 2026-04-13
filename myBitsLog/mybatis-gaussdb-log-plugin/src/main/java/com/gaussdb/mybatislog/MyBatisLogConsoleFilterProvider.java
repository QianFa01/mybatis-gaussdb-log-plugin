package com.gaussdb.mybatislog;

import com.intellij.execution.filters.ConsoleInputFilterProvider;
import com.intellij.execution.filters.ConsoleFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.InputFilter;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyBatisLogConsoleFilterProvider implements ConsoleFilterProvider, ConsoleInputFilterProvider {
    
    @NotNull
    @Override
    public Filter[] getDefaultFilters(@NotNull Project project) {
        return new Filter[]{new MyBatisLogConsoleFilter(project)};
    }
    
    @NotNull
    @Override
    public List<InputFilter> getDefaultInputFilters(@NotNull Project project) {
        List<InputFilter> filters = new ArrayList<>();
        filters.add(new MyBatisLogConsoleFilter(project));
        return filters;
    }
}