package com.axsos.sys.controllers;

import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.axsos.sys.models.FileUploadUtil;
import com.axsos.sys.models.Location;
import com.axsos.sys.models.Product;
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

	@GetMapping("/thymeleaf")
    String thymeleafPage() {
        return "thymeleaf/indexx";
    }

	@RequestMapping("/registration")
	public String registerForm(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
		model.addAttribute("locations", Location.Locations);
		return "registrationPage.jsp";
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
	
	@RequestMapping(value = { "/", "/home" })
	public String home(Principal principal, Model model) {
		String username = principal.getName();
		model.addAttribute("currentUser", pharmaServer.findByUsername(username));
		return "homePage.jsp";
	}
	
	@RequestMapping("/search")
	public String search(@RequestParam(value="search",required=false) String product,Model model) {
		model.addAttribute("search", pharmaServer.searchProduct(product));
		return "search.jsp";
	}
	
	@PostMapping("/products/save")
    public RedirectView saveProduct(Product product,
            @RequestParam("image") MultipartFile multipartFile) throws IOException {
         
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        product.setPhotos(fileName);
         
        Product savedProduct = pharmaServer.saveProduct(product);
 
        String uploadDir = "product-photos/" + savedProduct.getId();
 
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        System.out.println("HELLO");
        return new RedirectView("/products", true);
    }
	
	@GetMapping("/products")
	public String showProduct(Model model) {
		model.addAttribute("product", pharmaServer.findAllProducts());
		return "thymeleaf/product";
	}
}
