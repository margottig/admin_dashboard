package com.example.autorizacion.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.autorizacion.models.User;
import com.example.autorizacion.services.UserService;
import com.example.autorizacion.validator.UserValidator;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	private UserValidator userValidator;

	@RequestMapping("/register")
	public String registerForm(@Valid @ModelAttribute("user") User user) {
		return "registrationPage.jsp";
	}

	@PostMapping("/register")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			HttpSession session) {
		
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return "registrationPage.jsp";
		}
		userService.saveUserWithAdminRole(user);
		return "redirect:/login";
	}

//	@RequestMapping("/login")
//	public String login() {
//		return "test.jsp";
//	}

	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
		return "loginPage.jsp";
	}

	@RequestMapping(value = { "/", "/home" })
	public String home(Principal principal, Model model) {
		// 1
		String username = principal.getName();
		model.addAttribute("currentUser", userService.findByUsername(username));
		return "homePage.jsp";
	}
	
	  @RequestMapping("/admin")
	    public String adminPage(Principal principal, Model model) {
	        String username = principal.getName();
	        model.addAttribute("currentUser", userService.findByUsername(username));
	        return "adminPage.jsp";
	    }

}
