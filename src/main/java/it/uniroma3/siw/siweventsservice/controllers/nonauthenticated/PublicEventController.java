package it.uniroma3.siw.siweventsservice.controllers.nonauthenticated;

import it.uniroma3.siw.siweventsservice.models.Event;
import it.uniroma3.siw.siweventsservice.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/public")
public class PublicEventController {
	@Autowired
	private EventService eventService;

	@GetMapping("/events")
	public String getAllEvents (Model model) {
		List<Event> events = eventService.findAll();
		model.addAttribute("events", events);
		return "events/events";
	}

	@GetMapping("/explore")
	public String exploreEvents (Model model) {
		model.addAttribute("events", this.eventService.findAll().stream().limit(4).collect(Collectors.toList()));
		return "indexes/explore";
	}


}
