package com.plannerapp.model.dtos;

import com.plannerapp.model.annotations.StringDateInFuture;
import com.plannerapp.model.enums.PriorityName;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TaskDTO {
    private Long id;
    @Size(min = 2, max = 50)
    private String description;
    @StringDateInFuture
    private String dueDate;
    @NotNull
    private PriorityName priority;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public PriorityName getPriority() {
        return priority;
    }

    public void setPriority(PriorityName priority) {
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
