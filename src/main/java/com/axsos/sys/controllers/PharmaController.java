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
import org.springframework.web.bind.annotation.PutMapping;
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
		return "redirect:/token";
	}

	@RequestMapping("/token")
	public String show(Model model) {
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
		model.addAttribute("currentUser", currentUser);
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
		return new RedirectView("/pharmacistproducts/" + currentUser.getId(), true);
	}

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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		model.addAttribute("currentUser", currentUser);
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

	@RequestMapping("/sendforallenjoing")
	public String sendEmailEnjoing() {
		String first = "\r\n"
				+ "         _ \\\\  //     .-.     wWw  wWw  ___    wWw    _oo  wWw  \\\\  //  \r\n"
				+ "  (OO) .' )(o)(o)   c(O_O)c   (O)  (O) (___)__ (O)_>-(_  \\ (O)_ (o)(o)  \r\n"
				+ "   ||_/ .' ||  ||  ,'.---.`,  / )  ( \\ (O)(O)  / __)  / _/ / __)||  ||  \r\n"
				+ "   |   /   |(__)| / /|_|_|\\ \\/ /    \\ \\/  _\\  / (    / /  / (   |(__)|  \r\n"
				+ "   ||\\ \\   /.--.\\ | \\_____/ || \\____/ || |_))(  _)  / (  (  _)  /.--.\\  \r\n"
				+ "  (/\\)\\ `.-'    `-'. `---' .`'. `--' .`| |_)) \\ \\_ (   `-.\\ \\_ -'    `- \r\n"
				+ "       `._)         `-...-'    `-..-'  (.'-'   \\__) `--.._)\\__)         \r\n"
				+ "";
		String love = "\r\n"
				+ "?????????????????????????????????        \r\n"
				+ "?????????????????????????????????           \r\n"
				+ "?????????????????????????????????           \r\n"
				+ "????????????????????????              \r\n"
				+ "?????????????????????               \r\n"
				+ "???????????????                  \r\n"
				+ "????????????                   \r\n"
				+ "??????????????????????????????????????????         \r\n"
				+ "???????????????????????????????????????????????????      \r\n"
				+ "?????????????????????????????????????????????????????????    \r\n"
				+ "???????????????????????????(???)??????????????????     \r\n"
				+ "????????????????????????????????????????????????????????????   \r\n"
				+ "???????????????????????????????????????????????????????????????  \r\n"
				+ "?????????????????????????????????????????????????????????????????? \r\n"
				+ "?????????????????????????????????????????????????????????????????? \r\n"
				+ "??????????????????????????????????????????????????????????????????  \r\n"
				+ "??????????????????????????????????????????????????????????????????  \r\n"
				+ "???????????????????????????????????????????????????????????????      \r\n"
				+ "???????????????????????????????????????????????????????????????      \r\n"
				+ "????????????????????????????????????????????????????????????????????????   \r\n"
				+ "????????????????????????????????????????????????????????????????????????   \r\n"
				+ "????????????????????????????????????????????????????????????????????????   \r\n"
				+ "???????????????????????????????????????????????????????????????????????? \r\n";
		String last = "\r\n"
				+ "                                                                                                 \r\n"
				+ " ,dPYb,     ,dPYb,                             ,dPYb,                                  ,dPYb,    \r\n"
				+ " IP'`Yb     IP'`Yb                             IP'`Yb                                  IP'`Yb    \r\n"
				+ " I8  8I     I8  8I                             I8  8I                                  I8  8I    \r\n"
				+ " I8  8bgg,  I8  8'                             I8  8'                                  I8  8'    \r\n"
				+ " I8 dP\" \"8  I8 dPgg,     ,ggggg,   gg      gg  I8 dP       ,ggg,      ,gggg,   ,ggg,   I8 dPgg,  \r\n"
				+ " I8d8bggP\"  I8dP\" \"8I   dP\"  \"Y8gggI8      8I  I8dP   88ggi8\" \"8i    d8\"  Yb  i8\" \"8i  I8dP\" \"8I \r\n"
				+ " I8P' \"Yb,  I8P    I8  i8'    ,8I  I8,    ,8I  I8P    8I  I8, ,8I   dP    dP  I8, ,8I  I8P    I8 \r\n"
				+ ",d8    `Yb,,d8     I8,,d8,   ,d8' ,d8b,  ,d8b,,d8b,  ,8I  `YbadP' ,dP  ,adP'  `YbadP' ,d8     I8,\r\n"
				+ "88P      Y888P     `Y8P\"Y8888P\"   8P'\"Y88P\"`Y88P'\"Y88P\"' 888P\"Y8888\"   \"\"Y8d8888P\"Y88888P     `Y8\r\n"
				+ "                                                                        ,d8I'                    \r\n"
				+ "                                                                      ,dP'8I                     \r\n"
				+ "                                                                     ,8\"  8I                     \r\n"
				+ "                                                                     I8   8I                     \r\n"
				+ "                                                                     `8, ,8I                     \r\n"
				+ "                                                                      `Y8P\"                      \r\n"
				+ "";
		pharmaServer.getUser("Ramallah", "ROLE_ADMIN");
		String message = "\t Hello From pharma khoubezeh team to you all \n \t Hope you enjoy our presentation \n Here and now in front of your eyes will be the start of our pharma khoubezeh";
		message= first+"\n \n"+message+"\n \n"+last+"\n \n"+love;
		BufferedReader reader;
		try {
			reader = new BufferedReader(
					new FileReader("D:/codingdojo/potato/pharmaKhoubezeh/src/main/resources/static/imgs/mail.txt"));
			String line = reader.readLine();
			while (line != null) {
				String reciever = line;
				System.out.println("send email to..." +reciever);
				pharmaServer.sendingMail(reciever, message, "Spreading Love");
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@RequestMapping("/sendfor/{mail}")
	public String sennhfjkg(@PathVariable("mail") String mail) {
		String first = "\r\n"
				+ "         _ \\\\  //     .-.     wWw  wWw  ___    wWw    _oo  wWw  \\\\  //  \r\n"
				+ "  (OO) .' )(o)(o)   c(O_O)c   (O)  (O) (___)__ (O)_>-(_  \\ (O)_ (o)(o)  \r\n"
				+ "   ||_/ .' ||  ||  ,'.---.`,  / )  ( \\ (O)(O)  / __)  / _/ / __)||  ||  \r\n"
				+ "   |   /   |(__)| / /|_|_|\\ \\/ /    \\ \\/  _\\  / (    / /  / (   |(__)|  \r\n"
				+ "   ||\\ \\   /.--.\\ | \\_____/ || \\____/ || |_))(  _)  / (  (  _)  /.--.\\  \r\n"
				+ "  (/\\)\\ `.-'    `-'. `---' .`'. `--' .`| |_)) \\ \\_ (   `-.\\ \\_ -'    `- \r\n"
				+ "       `._)         `-...-'    `-..-'  (.'-'   \\__) `--.._)\\__)         \r\n"
				+ "";
		String love = "\r\n"
				+ "?????????????????????????????????        \r\n"
				+ "?????????????????????????????????           \r\n"
				+ "?????????????????????????????????           \r\n"
				+ "????????????????????????              \r\n"
				+ "?????????????????????               \r\n"
				+ "???????????????                  \r\n"
				+ "????????????                   \r\n"
				+ "??????????????????????????????????????????         \r\n"
				+ "???????????????????????????????????????????????????      \r\n"
				+ "?????????????????????????????????????????????????????????    \r\n"
				+ "???????????????????????????(???)??????????????????     \r\n"
				+ "????????????????????????????????????????????????????????????   \r\n"
				+ "???????????????????????????????????????????????????????????????  \r\n"
				+ "?????????????????????????????????????????????????????????????????? \r\n"
				+ "?????????????????????????????????????????????????????????????????? \r\n"
				+ "??????????????????????????????????????????????????????????????????  \r\n"
				+ "??????????????????????????????????????????????????????????????????  \r\n"
				+ "???????????????????????????????????????????????????????????????      \r\n"
				+ "???????????????????????????????????????????????????????????????      \r\n"
				+ "????????????????????????????????????????????????????????????????????????   \r\n"
				+ "????????????????????????????????????????????????????????????????????????   \r\n"
				+ "????????????????????????????????????????????????????????????????????????   \r\n"
				+ "???????????????????????????????????????????????????????????????????????? \r\n";
		String last = "\r\n"
				+ "                                                                                                 \r\n"
				+ " ,dPYb,     ,dPYb,                             ,dPYb,                                  ,dPYb,    \r\n"
				+ " IP'`Yb     IP'`Yb                             IP'`Yb                                  IP'`Yb    \r\n"
				+ " I8  8I     I8  8I                             I8  8I                                  I8  8I    \r\n"
				+ " I8  8bgg,  I8  8'                             I8  8'                                  I8  8'    \r\n"
				+ " I8 dP\" \"8  I8 dPgg,     ,ggggg,   gg      gg  I8 dP       ,ggg,      ,gggg,   ,ggg,   I8 dPgg,  \r\n"
				+ " I8d8bggP\"  I8dP\" \"8I   dP\"  \"Y8gggI8      8I  I8dP   88ggi8\" \"8i    d8\"  Yb  i8\" \"8i  I8dP\" \"8I \r\n"
				+ " I8P' \"Yb,  I8P    I8  i8'    ,8I  I8,    ,8I  I8P    8I  I8, ,8I   dP    dP  I8, ,8I  I8P    I8 \r\n"
				+ ",d8    `Yb,,d8     I8,,d8,   ,d8' ,d8b,  ,d8b,,d8b,  ,8I  `YbadP' ,dP  ,adP'  `YbadP' ,d8     I8,\r\n"
				+ "88P      Y888P     `Y8P\"Y8888P\"   8P'\"Y88P\"`Y88P'\"Y88P\"' 888P\"Y8888\"   \"\"Y8d8888P\"Y88888P     `Y8\r\n"
				+ "                                                                        ,d8I'                    \r\n"
				+ "                                                                      ,dP'8I                     \r\n"
				+ "                                                                     ,8\"  8I                     \r\n"
				+ "                                                                     I8   8I                     \r\n"
				+ "                                                                     `8, ,8I                     \r\n"
				+ "                                                                      `Y8P\"                      \r\n"
				+ "";
		
		String message = "Hello From pharma khoubezeh team to you all \n Hope you enjoy our presentation \n Here and now in front of your eyes will be the start of our pharma khoubezeh";
		
		message= first+"\n \n"+message+"\n \n"+last+"\n \n"+love;
	System.out.println(message);
	pharmaServer.sendingMail(mail, message, "Spreading Love");
	return "redirect:/";
	}
	
	
	@RequestMapping("/sendforallthx")
	public String sendEmail() {
		pharmaServer.getUser("Ramallah", "ROLE_ADMIN");
		String message = "Hello From the other side \n you need to keep cuite during the presentation \n so we all can get the intended value";
		BufferedReader reader;
		try {
			reader = new BufferedReader(
					new FileReader("D:/codingdojo/potato/pharmaKhoubezeh/src/main/resources/static/imgs/mail.txt"));
			String line = reader.readLine();
			while (line != null) {
				String reciever = line;
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
	public String addingdata(@Valid @ModelAttribute("user") User user,BindingResult result) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		if (result.hasErrors()) {
			return "CheckOut.jsp";
		} else {
			pharmaServer.updateUser(currentUser);
			pharmaServer.emptyCart(pharmaServer.getUsersUndoneCart(currentUser).getId());			
			return "redirect:/thankyou";
		}
	}
	
	@RequestMapping("/pharmacistproducts/{id}")
	public String PharmacistProducts(Model model, @PathVariable("id") Long pharmaId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		User pharmacist = pharmaServer.getUserById(pharmaId);
		model.addAttribute("products", pharmacist.getProductsOwned());
		model.addAttribute("locationsAll", Location.Locations);
		model.addAttribute("currentUser", currentUser);
		return "pharmacistProducts.jsp";
	}
	
	@RequestMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User currentUser = pharmaServer.findByUsername(username);
		pharmaServer.deleteProductById(id);
		return "redirect:/pharmacistproducts/"+currentUser.getId();
	}
}
