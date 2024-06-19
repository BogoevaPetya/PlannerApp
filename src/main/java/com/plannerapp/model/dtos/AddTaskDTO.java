package com.plannerapp.model.dtos;

import com.plannerapp.model.annotations.StringDateInFuture;
import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.enums.PriorityName;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class AddTaskDTO {
    @NotBlank
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
}
