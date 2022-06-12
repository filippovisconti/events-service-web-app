package it.uniroma3.siw.siweventsservice.controllers.nonauthenticated;

import it.uniroma3.siw.siweventsservice.models.Event;
import it.uniroma3.siw.siweventsservice.models.Organizer;
import it.uniroma3.siw.siweventsservice.services.EventService;
import it.uniroma3.siw.siweventsservice.services.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/public")
public class PublicOrganizerController {
	@Autowired
	private OrganizerService organizerService;

	@GetMapping("/organizers")
	public String getAllOrganizers(Model model) {
		List<Organizer> organizers = organizerService.findAll();
		model.addAttribute("organizers", organizers);
		return "organizers/organizers";
	}

}
