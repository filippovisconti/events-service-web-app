package it.uniroma3.siw.siweventsservice.controllers.admin;

import it.uniroma3.siw.siweventsservice.auth.models.User;
import it.uniroma3.siw.siweventsservice.auth.service.UserService;
import it.uniroma3.siw.siweventsservice.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", this.userService.getUser(id));
        return "users/user";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users/users";
    }

    @GetMapping("/edit/user/{id}")
    public String getToolForm (@PathVariable Long id, Model model) {
        model.addAttribute("events", eventService.findAll());
        model.addAttribute("user", userService.getUser(id));
        return "users/editUserForm.html";
    }
}
