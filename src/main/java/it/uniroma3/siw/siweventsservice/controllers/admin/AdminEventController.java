package it.uniroma3.siw.siweventsservice.controllers.admin;

import it.uniroma3.siw.siweventsservice.models.Event;
import it.uniroma3.siw.siweventsservice.services.ActivityService;
import it.uniroma3.siw.siweventsservice.services.EventService;
import it.uniroma3.siw.siweventsservice.services.OrganizerService;
import it.uniroma3.siw.siweventsservice.validators.EventValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminEventController {
	@Autowired
	private EventService eventService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private OrganizerService organizerService;

	@Autowired
	private EventValidator eventValidator;

	@GetMapping("/eventForm")
	public String getEventForm (Model model) {
		model.addAttribute("organizersList", organizerService.findAll());
		model.addAttribute("activities", activityService.findAll());
		model.addAttribute("event", new Event());
		return "events/eventForm.html";
	}

	@PostMapping("/event")
	public String newEvent (@Valid @ModelAttribute("event") Event event, BindingResult bindingResult, Model model) {
		this.eventValidator.validate(event, bindingResult);
		if (!bindingResult.hasErrors()) { // se i dati sono corretti
			this.eventService.save(event); // salvo un oggetto Event
			model.addAttribute("event", this.eventService.findById(event.getId()));
			return "events/event.html";      // presenta un pagina con la event appena salvata
		} else {
			model.addAttribute("organizersList", organizerService.findAll());
			model.addAttribute("activities", activityService.findAll());
			return "events/eventForm.html"; // ci sono errori, torna alla form iniziale
		}

	}

	@GetMapping("/edit/event/{id}")
	public String getEventForm (@PathVariable Long id, Model model) {
		model.addAttribute("activities", activityService.findAll());
		model.addAttribute("event", eventService.findById(id));
		return "events/editEventForm.html";
	}

	@Transactional
	@PostMapping("/edit/event/{id}")
	public String editEvent (@PathVariable Long id, @Valid @ModelAttribute("event") Event event, BindingResult bindingResults, Model model) {
		Event oldEvent = eventService.findById(id);
		if (!oldEvent.equals(event)) this.eventValidator.validate(event, bindingResults);
		if (!bindingResults.hasErrors()) {
			oldEvent.setId(event.getId());
			oldEvent.setName(event.getName());
			oldEvent.setDescription(event.getDescription());
			oldEvent.setDate(event.getDate());
			oldEvent.setOrganizer(event.getOrganizer());
			oldEvent.setActivityList(event.getActivityList());
			this.eventService.save(oldEvent);
			model.addAttribute("event", event);
			return "events/event.html";
		} else {
			return "events/editEventForm.html";
		}

	}

	@GetMapping("/delete/event/{id}")
	public String toDeleteEvent (@PathVariable("id") Long id, Model model) {
		model.addAttribute("event", this.eventService.findById(id));
		return "events/eventConfirmDelete.html";
	}

	@PostMapping("/delete/event/{id}")
	public String deleteEvent (@PathVariable("id") Long id, Model model) {
		try {
			this.eventService.deleteEventById(id);
			return "events/events.html";
		} catch (Exception e) {
			return "error.html";
		}
	}

}
