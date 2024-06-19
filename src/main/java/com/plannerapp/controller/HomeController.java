package com.plannerapp.controller;

import com.plannerapp.model.dtos.TaskHomeViewDTO;
import com.plannerapp.service.TaskService;
import com.plannerapp.user.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final TaskService taskService;
    private final LoggedUser loggedUser;

    public HomeController(TaskService taskService, LoggedUser loggedUser) {
        this.taskService = taskService;
        this.loggedUser = loggedUser;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model){
        TaskHomeViewDTO viewDTO = taskService.getHomeViewData(loggedUser.getUsername());
        model.addAttribute("viewDTO", viewDTO);
        return "home";
    }
}
