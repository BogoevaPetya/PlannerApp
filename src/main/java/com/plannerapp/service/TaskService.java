package com.plannerapp.service;

import com.plannerapp.model.dtos.AddTaskDTO;
import com.plannerapp.model.dtos.TaskDTO;
import com.plannerapp.model.dtos.TaskHomeViewDTO;
import com.plannerapp.model.entity.Priority;
import com.plannerapp.model.entity.Task;
import com.plannerapp.model.entity.User;
import com.plannerapp.repo.PriorityRepository;
import com.plannerapp.repo.TaskRepository;
import com.plannerapp.repo.UserRepository;
import com.plannerapp.user.LoggedUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final PriorityRepository priorityRepository;
    private final UserRepository userRepository;
    private final LoggedUser loggedUser;

    public TaskService(TaskRepository taskRepository, PriorityRepository priorityRepository, UserRepository userRepository, LoggedUser loggedUser) {
        this.taskRepository = taskRepository;
        this.priorityRepository = priorityRepository;
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
    }

    public void add(AddTaskDTO addTaskDTO) {
        Priority priority = priorityRepository.findByName(addTaskDTO.getPriority());
        User user = userRepository.findByUsername(loggedUser.getUsername());

        if (priority != null){
            Task task = new Task();
            task.setDescription(addTaskDTO.getDescription());
            task.setDueDate(LocalDate.parse(addTaskDTO.getDueDate()));
            task.setPriority(priority);
            task.setAssignee(user);
            taskRepository.save(task);
        }
    }

    public void remove(Long id){
        taskRepository.deleteById(id);
    }

    public void assign(Long id, String username){
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()){
            Task task = optionalTask.get();
            if (username == null){
                task.setAssignee(null);
            } else {
                User user = userRepository.findByUsername(username);
                task.setAssignee(user);
            }

            taskRepository.save(task);
        }
    }

    public TaskHomeViewDTO getHomeViewData(String username) {
        User user = userRepository.findByUsername(username);
        List<TaskDTO> assignedTasks = taskRepository.findAllByAssignee(user)
                .stream()
                .map(this::createFromTask).toList();
        List<TaskDTO> availableTasks = taskRepository.getAllAvailable()
                .stream()
                .map(this::createFromTask).collect(Collectors.toList());

       return new TaskHomeViewDTO(assignedTasks, availableTasks);
    }

    public TaskDTO createFromTask(Task task){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setPriority(task.getPriority().getName());
        taskDTO.setDueDate(String.valueOf(task.getDueDate()));
        return taskDTO;
    }
}
