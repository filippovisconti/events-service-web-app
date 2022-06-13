package it.uniroma3.siw.siweventsservice.controllers.user;

import it.uniroma3.siw.siweventsservice.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/protected")
public class UserEventController {
	@Autowired
	private EventService eventService;

	@GetMapping("/event/{id}")
	public String getEvent(@PathVariable("id") Long id, Model model) {
		model.addAttribute("event", this.eventService.findById(id));
		return "events/event";
	}
}
