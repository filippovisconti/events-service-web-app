package it.uniroma3.siw.siweventsservice.auth.controller;

import it.uniroma3.siw.siweventsservice.auth.models.Credentials;
import it.uniroma3.siw.siweventsservice.auth.models.User;
import it.uniroma3.siw.siweventsservice.auth.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AuthController {

	@Autowired
	private CredentialsService credentialsService;

	@GetMapping("/login")
	public String showLoginForm (Model model) {
		return "login/loginForm";
	}

	@GetMapping("/logout")
	public String logout (Model model) {
		return "redirect:/";
	}

	@GetMapping("/register")
	public String getRegisterPage (Model model) {
		model.addAttribute("credentials", new Credentials());
		return "login/signUpForm";
	}

	@PostMapping("/register")
	public String register (@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			//credentials.setRole("DEFAULT");
			this.credentialsService.saveCredentials(credentials);
			return "login/loginForm";
		} else {
			model.addAttribute("credentials", credentials);
			return "login/signUpForm";
		}
	}

	@GetMapping("/default")
	public String defaultAfterLogin (Model model) {

		UserDetails adminDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(adminDetails.getUsername());
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "redirect:/admin";
		} else if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
			return "redirect:/user";
		}
		return "redirect:/login";
	}

	@GetMapping("/admin")
	public String getAdminHomePage () {
		return "indexes/admin_index";
	}


	@GetMapping("/user")
	public String getUserHomePage (Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails)principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = credentialsService.getUserDetails(username);
		model.addAttribute("user", user);
		return "indexes/user_index";
	}


	@GetMapping("/")
	public String getPublicHomePage () {
		return "indexes/index";
	}
}
