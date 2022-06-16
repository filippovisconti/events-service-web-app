package it.uniroma3.siw.siweventsservice.controllers.admin;

import it.uniroma3.siw.siweventsservice.models.Organizer;
import it.uniroma3.siw.siweventsservice.services.EventService;
import it.uniroma3.siw.siweventsservice.services.OrganizerService;
import it.uniroma3.siw.siweventsservice.validators.OrganizerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminOrganizerController {
	@Autowired
	private OrganizerService organizerService;

	@Autowired
	private EventService eventService;

	@Autowired
	private OrganizerValidator organizerValidator;

	@GetMapping("/organizerForm")
	public String getOrganizerForm (Model model) {
		model.addAttribute("events", eventService.findAll());
		model.addAttribute("organizer", new Organizer());
		return "organizers/organizerForm.html";
	}

	@PostMapping("/organizer")
	public String newOrganizer (@Valid @ModelAttribute("organizer") Organizer organizer, BindingResult bindingResult, Model model) {
		this.organizerValidator.validate(organizer, bindingResult);
		if (!bindingResult.hasErrors()) { // se i dati sono corretti
			this.organizerService.save(organizer); // salvo un oggetto Organizer
			model.addAttribute("organizer", this.organizerService.findById(organizer.getId()));
			return "organizers/organizer.html";      // presenta un pagina con la organizer appena salvata
		} else
			return "organizers/organizerForm.html"; // ci sono errori, torna alla form iniziale

	}

	@GetMapping("/edit/organizer/{id}")
	public String getOrganizerForm (@PathVariable Long id, Model model) {
		model.addAttribute("organizer", organizerService.findById(id));
		return "organizers/editOrganizerForm.html";
	}

	@Transactional
	@PostMapping("/edit/organizer/{id}")
	public String editOrganizer (@PathVariable Long id, @Valid @ModelAttribute("organizer") Organizer organizer, BindingResult bindingResults, Model model) {
		Organizer oldOrganizer = organizerService.findById(id);
		if (!oldOrganizer.equals(organizer))
			this.organizerValidator.validate(organizer, bindingResults);
		if (!bindingResults.hasErrors()) {
			oldOrganizer.setId(organizer.getId());
			oldOrganizer.setFirstName(organizer.getFirstName());
			oldOrganizer.setLastName(organizer.getLastName());
			oldOrganizer.setCountry(organizer.getCountry());
			this.organizerService.save(oldOrganizer);
			model.addAttribute("organizer", organizer);
			return "organizers/organizer.html";
		} else {
			return "organizers/editOrganizerForm.html";
		}
	}

	@GetMapping("/delete/organizer/{id}")
	public String toDeleteOrganizer (@PathVariable("id") Long id, Model model) {
		model.addAttribute("organizer", this.organizerService.findById(id));
		return "organizers/organizerConfirmDelete.html";
	}

	@PostMapping("/delete/organizer/{id}")
	public String deleteOrganizer (@PathVariable("id") Long id, Model model) {
		try {
			this.organizerService.deleteOrganizerById(id);
			model.addAttribute("organizers", this.organizerService.findAll());
			return "organizers/organizers.html";
		} catch (Exception e) {
			return "error.html";
		}
	}

}
