package com.axsos.sys.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.axsos.sys.models.User;
import com.axsos.sys.services.PharmaService;

@Controller
public class PharmaController {
	private final PharmaService pharmaServer;

	public PharmaController(PharmaService xXx) {
		pharmaServer = xXx;
	}

	@GetMapping("/")
	public String index(@ModelAttribute("newUser") User ussr, Model model, HttpSession session) {
		if (session.getAttribute("user_id") != null) {
			model.addAttribute("everyUserInTheDatabase", pharmaServer.findAllUsers());
			model.addAttribute("everyProductInTheDatabase", pharmaServer.findAllProducts());
			model.addAttribute("everyRequestInTheDatabase", pharmaServer.findAllRequests());
			return "redirect:/pharmahome";
		}
		return "loginpage.jsp";
	}
}
