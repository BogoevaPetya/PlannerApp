package com.plannerapp.model.dtos;

import java.util.ArrayList;
import java.util.List;

public class TaskHomeViewDTO {
    private List<TaskDTO> assignedTasksToMe;
    private List<TaskDTO> availableTasks;

    public TaskHomeViewDTO(List<TaskDTO> assignedTasksToMe, List<TaskDTO> availableTasks){
        this.assignedTasksToMe = assignedTasksToMe;
        this.availableTasks = availableTasks;
    }

    public TaskHomeViewDTO() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public List<TaskDTO> getAssignedTasksToMe() {
        return assignedTasksToMe;
    }

    public void setAssignedTasksToMe(List<TaskDTO> assignedTasksToMe) {
        this.assignedTasksToMe = assignedTasksToMe;
    }

    public List<TaskDTO> getAvailableTasks() {
        return availableTasks;
    }

    public void setAvailableTasks(List<TaskDTO> availableTasks) {
        this.availableTasks = availableTasks;
    }
}
