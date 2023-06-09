package com.example.autorizacion.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.autorizacion.models.User;
import com.example.autorizacion.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	  private UserService userService;
	    
	    public UserController(UserService userService) {
	        this.userService = userService;
	    }

	@RequestMapping("/register")
	public String registerForm(@Valid @ModelAttribute("user") User user) {
		return "registrationPage.jsp";
	}
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "registrationPage.jsp";
        }
        userService.saveWithUserRole(user);
        return "redirect:/login";
    }

	@RequestMapping("/login")
	public String login() {
		return "test.jsp";
	}
}
