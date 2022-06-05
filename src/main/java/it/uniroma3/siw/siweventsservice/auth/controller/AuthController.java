package it.uniroma3.siw.siweventsservice.auth.controller;

import it.uniroma3.siw.siweventsservice.auth.models.Credentials;
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
		return "loginForm";
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		return "redirect:/public";
	}

	@GetMapping("/register")
	public String getRegisterPage(Model model) {
		model.addAttribute("credentials", new Credentials());
		return "signUpForm";
	}

	@PostMapping("/register")
	public String register (@Valid @ModelAttribute("credentials") Credentials credentials, BindingResult bindingResult, Model model) {
		String nextPage;
		if (!bindingResult.hasErrors()) {
			//credentials.setRole("DEFAULT");
			this.credentialsService.saveCredentials(credentials);
			nextPage = "loginForm.html";
		} else {
			model.addAttribute("credentials", credentials);
			nextPage = "signUpForm.html";
		}
		return nextPage;

	}

	@GetMapping("/admin")
	//@IsStdUser
	public String getHomePage () {
		String nextPage = "admin_index.html";
		return nextPage;
	}

	@GetMapping("/default")
	public String defaultAfterLogin(Model model) {

		UserDetails adminDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(adminDetails.getUsername());
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "redirect:/admin";
		}
		return "redirect:/login";
	}
}
