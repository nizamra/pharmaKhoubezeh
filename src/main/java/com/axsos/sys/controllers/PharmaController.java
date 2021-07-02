package com.axsos.sys.controllers;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.axsos.sys.models.User;
import com.axsos.sys.services.PharmaService;
import com.axsos.sys.validator.UserValidator;

@Controller
public class PharmaController {
	private final PharmaService pharmaServer;
	private UserValidator userValidator;

	public PharmaController(PharmaService xXx, UserValidator userValidator) {
		pharmaServer = xXx;
		this.userValidator = userValidator;
	}

    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            return "registrationPage.jsp";
        }
        
        pharmaServer.saveWithUserRole(user);
        return "redirect:/login";
    }
    
    
	@RequestMapping("/registration")
	public String registerForm(@Valid @ModelAttribute("user") User user) {
		return "registrationPage.jsp";
	}

	@RequestMapping(value = { "/", "/home" })
	public String home(Principal principal, Model model) {
		// 1
		String username = principal.getName();
		model.addAttribute("currentUser", pharmaServer.findByUsername(username));
		return "homePage.jsp";
	}

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
	
	@RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", pharmaServer.findByUsername(username));
        return "adminPage.jsp";
    }

//	@GetMapping("/")
//	public String index(@ModelAttribute("newUser") User ussr, Model model, HttpSession session) {
//		if (session.getAttribute("user_id") != null) {
//			model.addAttribute("everyUserInTheDatabase", pharmaServer.findAllUsers());
//			model.addAttribute("everyProductInTheDatabase", pharmaServer.findAllProducts());
//			model.addAttribute("everyRequestInTheDatabase", pharmaServer.findAllRequests());
//			return "redirect:/pharmahome";
//		}
//		return "adminpage.old.jsp";
//	}
}
