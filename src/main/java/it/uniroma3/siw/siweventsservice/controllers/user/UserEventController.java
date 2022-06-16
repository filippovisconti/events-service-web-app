package it.uniroma3.siw.siweventsservice.controllers.user;

import it.uniroma3.siw.siweventsservice.auth.models.User;
import it.uniroma3.siw.siweventsservice.auth.service.CredentialsService;
import it.uniroma3.siw.siweventsservice.models.Activity;
import it.uniroma3.siw.siweventsservice.models.Event;
import it.uniroma3.siw.siweventsservice.services.ActivityService;
import it.uniroma3.siw.siweventsservice.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/protected")
public class UserEventController {
	@Autowired
	private EventService eventService;

	@Autowired
	private ActivityService activityService;
	@Autowired
	private CredentialsService credentialsService;


	@GetMapping("/event/{id}")
	public String getEvent (@PathVariable("id") Long id, Model model) {
		Event e = this.eventService.findById(id);
		model.addAttribute("event", e);
		float duration = 0;
		for (Activity a : e.getActivityList()) {
			duration += a.getDuration();
		}
		model.addAttribute("duration", duration);
		return "events/event";
	}

	@GetMapping("/explore")
	public String getAllEvents (Model model) {
		model.addAttribute("events",
				this.eventService.findAll().stream()
						.limit(4)
						.collect(Collectors.toList()));
		return "indexes/explore";
	}

	@PostMapping("/registerToEvent/{id}")
	public String registerToEvent (@PathVariable("id") Long id, Model model) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = credentialsService.getUserDetails(username);

		Event e = this.eventService.findById(id);
		e.getParticipants().add(user);
		this.eventService.save(e);

		model.addAttribute("event", e);
		float duration = 0;
		for (Activity a : e.getActivityList()) {
			duration += a.getDuration();
		}
		model.addAttribute("duration", duration);
		return "events/event.html";
	}

}
