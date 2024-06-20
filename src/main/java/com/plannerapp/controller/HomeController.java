package com.plannerapp.controller;

import com.plannerapp.model.dtos.TaskHomeViewDTO;
import com.plannerapp.service.TaskService;
import com.plannerapp.user.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        if (loggedUser.isLogged()){
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model){
        if (!loggedUser.isLogged()){
            return "redirect:/";
        }
        TaskHomeViewDTO viewDTO = taskService.getHomeViewData(loggedUser.getUsername());
        model.addAttribute("viewDTO", viewDTO);
        return "home";
    }

}
