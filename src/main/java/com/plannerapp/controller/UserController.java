package com.plannerapp.controller;

import com.plannerapp.model.dtos.UserLoginDTO;
import com.plannerapp.model.dtos.UserRegisterDTO;
import com.plannerapp.service.UserService;
import com.plannerapp.user.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final LoggedUser loggedUser;

    public UserController(UserService userService, LoggedUser loggedUser) {
        this.userService = userService;
        this.loggedUser = loggedUser;
    }

    @ModelAttribute
    public UserLoginDTO userLoginDTO(){
        return new UserLoginDTO();
    }

    @ModelAttribute
    public UserRegisterDTO userRegisterDTO(){
        return new UserRegisterDTO();
    }

    @GetMapping("/login")
    public String login(){
        if (loggedUser.isLogged()){
            return "redirect:/home";
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        if (loggedUser.isLogged()){
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/login")
    public String loggUser(@Valid UserLoginDTO userLoginDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (loggedUser.isLogged()){
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO", bindingResult);
            return "redirect:/login";
        }

        if (!userService.login(userLoginDTO)){
            redirectAttributes.addFlashAttribute("userLoginDTO", userLoginDTO);
            redirectAttributes.addFlashAttribute("passwordOrEmailMismatch", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO", bindingResult);
            return "redirect:/login";
        }
        return "redirect:/home";
    }


    @PostMapping("/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (loggedUser.isLogged()){
            return "redirect:/home";
        }

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.UserRegisterDTO", bindingResult);
            return "/register";
        }

        boolean hasSuccessfulRegistration = userService.register(userRegisterDTO);

        if (!hasSuccessfulRegistration){
            redirectAttributes.addFlashAttribute("userRegisterDTO", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.UserRegisterDTO", bindingResult);
            return "/register";
        }

        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(){
        if (!loggedUser.isLogged()){
            return "redirect:/";
        }

        this.userService.logout();
        return "redirect:/";
    }
}
