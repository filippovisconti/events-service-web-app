package it.uniroma3.siw.siweventsservice.controllers.user;

import it.uniroma3.siw.siweventsservice.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/protected")
public class UserActivityController {
	@Autowired
	private ActivityService activityService;

	@GetMapping("/activity/{id}")
	public String getActivity(@PathVariable("id") Long id, Model model) {
		model.addAttribute("activity", this.activityService.findById(id));
		return "activities/activity";
	}
}
