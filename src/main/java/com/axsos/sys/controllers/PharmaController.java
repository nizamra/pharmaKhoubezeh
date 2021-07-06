package com.axsos.sys.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.axsos.sys.models.Category;
import com.axsos.sys.models.FileUploadUtil;
import com.axsos.sys.models.Location;
import com.axsos.sys.models.PharmaRequest;
import com.axsos.sys.models.Product;
import com.axsos.sys.models.Role;
import com.axsos.sys.models.User;
import com.axsos.sys.services.PharmaService;
import com.axsos.sys.validator.UserValidator;

@Controller
public class PharmaController {
	private final PharmaService pharmaServer;
	private UserValidator userValidator;
	public String randomNums = "";

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
			@RequestParam(value = "logout", required = false) String logout, Model model,
			@ModelAttribute("user") User user) {
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
		} else if (pharmaServer.findByName("ROLE_ADMIN").getUsers().size() < 1) {
			pharmaServer.saveUserWithAdminRole(user);
			return "redirect:/token";
		} else {
			pharmaServer.saveWithUserRole(user);
			return "redirect:/token";
		}
	}

	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model,
			@ModelAttribute("user") User user) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		}
		if (logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
		return "loginPage.jsp";
	}

	@RequestMapping(value = { "/", "/home" })
	public String home(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		if (currentUser.getVerified() == true) {
			model.addAttribute("currentUser", currentUser);
			model.addAttribute("pharmaAll", pharmaServer.getUser(currentUser.getLocation(), "ROLE_PHARMACY"));
			model.addAttribute("locationsAll", Location.Locations);
			List<PharmaRequest> carts = currentUser.getPharmaRequests();
			if (carts.isEmpty()) {
				PharmaRequest ss = new PharmaRequest(currentUser);
				pharmaServer.savePharmaRequest(ss);
			}
			int i = carts.size() - 1;
			if (carts.get(i).getDone().booleanValue()) {
				PharmaRequest ss = new PharmaRequest(currentUser);
				pharmaServer.savePharmaRequest(ss);
			}
			return "homePage.jsp";
		} else {
			return "redirect:/token";
		}
	}

	@RequestMapping("/{locat}")
	public String gotolocat(@PathVariable("locat") String chosenLocal, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("pharmaAll", pharmaServer.getUser(chosenLocal, "ROLE_PHARMACY"));
		model.addAttribute("locationsAll", Location.Locations);
		List<User> theseUsers = pharmaServer.getUser(chosenLocal, "ROLE_PHARMACY");
		for (User user : theseUsers) {
			System.out.println(user.getUsername());
		}

		return "homePage.jsp";

	}

	@RequestMapping("/search")
	public String search(@RequestParam(value = "search", required = false) String product, Model model) {
		model.addAttribute("search", pharmaServer.searchProduct(product));
		return "search.jsp";
	}

	@RequestMapping("/sendmail/{mailTo}")
	public String sendEmail(@PathVariable("mailTo") String reciever) {
		String message = "Hello From pharma khoubezeh team to you all \n Your verification token is: ";
		randomNums = "";
		Random r = new Random();
		for (byte i = 0; i < 4; i++) {
			randomNums = randomNums + (r.nextInt(9));
		}
		message = message + randomNums;
		System.out.println("sending new mail to... " + reciever + message);
//		pharmaServer.sendingMail(reciever, message, "Spreading Love");
		return "redirect:/token";
	}

	@RequestMapping("/token")
	public String show() {
		String message = "Hello From pharma khoubezeh team to you all \n Your verification token is: ";
		randomNums = "";
		Random r = new Random();
		for (byte i = 0; i < 4; i++) {
			randomNums = randomNums + (r.nextInt(9));
		}
		message = message + randomNums;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);

		String reciever = currentUser.getEmail();
		System.out.println("sending new mail to... " + reciever + message);
		pharmaServer.sendingMail(reciever, message, "Spreading Love");
		return "gettoken.jsp";
	}

	@PostMapping("/tokenpost")
	public String gettingshow(@RequestParam(name = "tokenFromUser") String tokenFromUser) {
		if (tokenFromUser.equals(randomNums)) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			User currentUser = pharmaServer.findByUsername(username);
			currentUser.setVerified(true);
			pharmaServer.updateUser(currentUser);
			return "redirect:/";
		} else {
			System.out.println("going out of tamam... ");
			return "redirect:/";
		}
	}

	@PostMapping("/products/save")
	public RedirectView saveProduct(Product product, @RequestParam("image") MultipartFile multipartFile)
			throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		product.setPhotos(fileName);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		product.setOwnerOfProduct(currentUser);
		Product savedProduct = pharmaServer.saveProduct(product);
		String uploadDir = "product-photos/" + savedProduct.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		return new RedirectView("/pharmacyproducts" + currentUser.getId(), true);
	}

//	@GetMapping("/products")
//	public String showProduct(Model model) {
//		model.addAttribute("product", pharmaServer.findAllProducts());
//		return "thymeleaf/product";
//	}

	@RequestMapping(value = { "/dashboard" })
	public String showHome(Principal principal, Model model) {
		String email = principal.getName();
		User user = pharmaServer.findByEmail(email);
		model.addAttribute("currentUser", pharmaServer.findByEmail(email));
		pharmaServer.updateUser(user);
		if (pharmaServer.checkIfAdmin(user)) {
			return "redirect:/admin";
		} else {
			return "redirect:/home";
		}
	}

	@RequestMapping("/admin")
	public String displayAdmin(Principal principal, Model model) {
		String email = principal.getName();
		model.addAttribute("currentUser", pharmaServer.findByEmail(email));
		model.addAttribute("all", pharmaServer.findAllUsers());
		return "adminPage.jsp";
	}

	@RequestMapping("user/admin/{id}")
	public String makeAdmin(@PathVariable("id") Long id) {
		User user = pharmaServer.getUserById(id);
		List<Role> roles = user.getUserRole();
		roles.add(pharmaServer.findByName("ROLE_ADMIN"));
		pharmaServer.updateUser(user);
		return "redirect:/admin";
	}

	@RequestMapping("/user/demote/{id}")
	public String demoteAdmin(@PathVariable("id") Long id) {
		User user = pharmaServer.getUserById(id);
		List<Role> roles = user.getUserRole();
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).getName().equals("ROLE_ADMIN")) {
				roles.remove(i);
			}
		}
		pharmaServer.updateUser(user);
		return "redirect:/admin";
	}

	@RequestMapping("user/pharmacy/admin/{id}")
	public String makePharmacy(@PathVariable("id") Long id) {
		User user = pharmaServer.getUserById(id);
		List<Role> roles = user.getUserRole();
		roles.add(pharmaServer.findByName("ROLE_PHARMACY"));
		pharmaServer.updateUser(user);
		return "redirect:/admin";
	}

	@RequestMapping("/user/pharmacy/demote/{id}")
	public String demotePharmacy(@PathVariable("id") Long id) {
		User user = pharmaServer.getUserById(id);
		List<Role> roles = user.getUserRole();
		for (int i = 0; i < roles.size(); i++) {
			if (roles.get(i).getName().equals("ROLE_PHARMACY")) {
				roles.remove(i);
			}

		}
		pharmaServer.updateUser(user);
		return "redirect:/admin";
	}

	@RequestMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Long id) {
		pharmaServer.deleteUserById(id);
		return "redirect:/admin";
	}

	@RequestMapping("/pharmacies")
	public String pharmacies() {
		return "pharmacies.jsp";
	}

	@RequestMapping("/pharmaCreate")
	public String pharmaCreate(@ModelAttribute("newUser") Product product) {
		return "pharmacyCreate.jsp";
	}

	@RequestMapping("/addToCart/{productId}")
	public String addingCart(@PathVariable("productId") Long productIdToAdd) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		pharmaServer.addToCart(currentUser.getId(), productIdToAdd);
		return "redirect:/cart";
	}

	@RequestMapping("/removeFromCart/{productId}")
	public String rmCart(@PathVariable("productId") Long productIdToremove) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		pharmaServer.removeItemCart(currentUser.getId(), productIdToremove);
		return "redirect:/cart";
	}

	@RequestMapping("/emptyCart/{cartId}")
	public void clear(@PathVariable("cartId") Long clearCart) {
		pharmaServer.emptyCart(clearCart);
	}

	@RequestMapping("/pharmacy")
	public String pharmaCreate(@ModelAttribute("newUser") Product product, Model model) {
		model.addAttribute("categories", Category.Categories);
		return "thymeleaf/indexx";
	}

	@RequestMapping("/pharmacyproducts/{id}")
	public String yourProducts(Model model, @PathVariable("id") Long pharmaId) {
		User pharmacist = pharmaServer.getUserById(pharmaId);
		model.addAttribute("products", pharmacist.getProductsOwned());
		model.addAttribute("locationsAll", Location.Locations);
		return "pharmacyOwner.jsp";
	}

	@RequestMapping("/specificproduct/{id}")
	public String thisProduct(Model model, @PathVariable("id") Long pharmaId) {
		model.addAttribute("productShow", pharmaServer.getProductById(pharmaId));
		return "viewProduct.jsp";
	}

	@RequestMapping("/sendforall")
	public String sendEmail() {
		pharmaServer.getUser("Ramallah", "ROLE_ADMIN");
		String message = "Hello From pharma khoubezeh team to you all \n Hope your enjoing our presentation";

		BufferedReader reader;
		try {
			reader = new BufferedReader(
					new FileReader("D:/codingdojo/potato/pharmaKhoubezeh/src/main/resources/static/imgs/mail.txt"));
			String line = reader.readLine();
			while (line != null) {
				String reciever = line;
				System.out.println("sending new mail to... " + reciever + message);
				pharmaServer.sendingMail(reciever, message, "Spreading Love");
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping("/allProducts")
	public String allProducts() {
		return "allProducts.jsp";
	}

	@RequestMapping("/thankyou")
	public String allProductss(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		model.addAttribute("currentUser", currentUser);
		return "thankyou.jsp";
	}

	@RequestMapping("/create")
	public String pro() {
		return "thymeleaf/indexx";
	}

	@RequestMapping("/showPharm")
	public String showPro() {
		return "PharmShowProd.jsp";
	}

	@RequestMapping("/cart")
	public String cart(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		PharmaRequest thisCart = pharmaServer.getUsersUndoneCart(currentUser);
		model.addAttribute("thisCart", thisCart.getProducts());
		model.addAttribute("locationsAll", Location.Locations);
		return "cart.jsp";
	}

	@RequestMapping("/edit")
	public String edit() {
		return "edit.jsp";
	}

	@RequestMapping("/aboutUs")
	public String aboutUs() {
		return "aboutUs.jsp";
	}

	@RequestMapping("/gettoken")
	public String gettoken() {
		return "gettoken.jsp";
	}

	@RequestMapping("/checkout")
	public String checkout(@ModelAttribute("user") User user, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		model.addAttribute("currentUser", currentUser);
		PharmaRequest thisCart = pharmaServer.getUsersUndoneCart(currentUser);
		model.addAttribute("thisCart", thisCart.getProducts());
		model.addAttribute("carting", thisCart);
		model.addAttribute("locationsAll", Location.Locations);
		return "CheckOut.jsp";
	}

	@PostMapping("/addingdata")
	public String addingdata(@ModelAttribute("user") User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		user = pharmaServer.findByUsername(username);

		pharmaServer.updateUser(user);
		pharmaServer.emptyCart(pharmaServer.getUsersUndoneCart(user).getId());
		return "redirect:/thankyou";
	}

	@RequestMapping("/viewProduct")
	public String viewProduct() {
		return "viewProduct.jsp";
	}

	@RequestMapping("/homePageeee")
	public String homePageeee() {
		return "homePage.jsp";
	}

}
