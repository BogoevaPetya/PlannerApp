package com.plannerapp.controller;

import com.plannerapp.model.dtos.AddTaskDTO;
import com.plannerapp.service.TaskService;
import com.plannerapp.user.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class TaskController {
    private final TaskService taskService;
    private final LoggedUser loggedUser;

    public TaskController(TaskService taskService, LoggedUser loggedUser) {
        this.taskService = taskService;
        this.loggedUser = loggedUser;
    }

    @ModelAttribute
    public AddTaskDTO addTaskDTO(){
        return new AddTaskDTO();
    }

    @GetMapping("/tasks/add")
    public String addTask(){
        return "task-add";
    }

    @PostMapping("/tasks/add")
    public String addTask(@Valid AddTaskDTO addTaskDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addTaskDTO", addTaskDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addTaskDTO", bindingResult);
            return "redirect:/tasks/add";
        }

        taskService.add(addTaskDTO);
        return "redirect:/home";
    }

    @DeleteMapping("/tasks/remove/{id}")
    public String removeTask(@PathVariable("id") Long id){
        taskService.remove(id);
        return "redirect:/home";
    }

    @PostMapping("/tasks/return/{id}")
    public String returnTask(@PathVariable("id") Long id){
        taskService.assign(id, null);
        return "redirect:/home";
    }

    @PostMapping("/tasks/assign/{id}")
    public String assign(@PathVariable("id") Long id){
        taskService.assign(id, loggedUser.getUsername());
        return "redirect:/home";
    }
}
