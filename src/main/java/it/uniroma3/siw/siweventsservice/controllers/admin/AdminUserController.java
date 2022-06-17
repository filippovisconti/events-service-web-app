package it.uniroma3.siw.siweventsservice.controllers.admin;

import it.uniroma3.siw.siweventsservice.auth.models.User;
import it.uniroma3.siw.siweventsservice.auth.service.UserService;
import it.uniroma3.siw.siweventsservice.auth.service.UserValidator;
import it.uniroma3.siw.siweventsservice.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private EventService eventService;

	@GetMapping("/user/{id}")
	public String getUser (@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", this.userService.getUser(id));
		return "users/user";
	}

	@GetMapping("/users")
	public String getAllUsers (Model model) {
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "users/users";
	}

	@GetMapping("/edit/user/{id}")
	public String getUserForm (@PathVariable Long id, Model model) {
		model.addAttribute("events", eventService.findAll());
		model.addAttribute("user", userService.getUser(id));
		return "users/editUserForm.html";
	}

	@Transactional
	@PostMapping("/edit/user/{id}")
	public String editUser (@PathVariable Long id, @Valid @ModelAttribute("user") User user, BindingResult bindingResults, Model model) {
		User oldUser = userService.getUser(id);
		if (!oldUser.equals(user))
			this.userValidator.validate(user, bindingResults);
		if (!bindingResults.hasErrors()) {
			oldUser.setId(user.getId());
			oldUser.setFirstName(user.getFirstName());
			oldUser.setLastName(user.getLastName());
			oldUser.setEventList(user.getEventList());

			this.userService.save(oldUser);
			model.addAttribute("user", user);
			return "users/user.html";
		} else {
			return "users/editUserForm.html";
		}

	}
}
