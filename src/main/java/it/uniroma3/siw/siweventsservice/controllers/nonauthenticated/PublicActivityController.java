package it.uniroma3.siw.siweventsservice.controllers.nonauthenticated;

import it.uniroma3.siw.siweventsservice.models.Activity;
import it.uniroma3.siw.siweventsservice.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/public")
public class PublicActivityController {
	@Autowired
	private ActivityService activityService;

	@GetMapping("/activities")
	public String getAllActivities (Model model) {
		List<Activity> activities = activityService.findAll();
		model.addAttribute("activities", activities);
		return "activities/activities";
	}

}
