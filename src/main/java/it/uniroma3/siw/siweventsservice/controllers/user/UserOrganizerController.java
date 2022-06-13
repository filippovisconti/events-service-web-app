package it.uniroma3.siw.siweventsservice.controllers.user;

import it.uniroma3.siw.siweventsservice.services.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/protected")
public class UserOrganizerController {
	@Autowired
	private OrganizerService organizerService;

	@GetMapping("/organizer/{id}")
	public String getOrganizer(@PathVariable("id") Long id, Model model) {
		model.addAttribute("organizer", this.organizerService.findById(id));
		return "organizers/organizer";
	}
}
