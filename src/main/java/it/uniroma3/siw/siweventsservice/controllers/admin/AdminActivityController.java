package it.uniroma3.siw.siweventsservice.controllers.admin;

import it.uniroma3.siw.siweventsservice.models.Activity;
import it.uniroma3.siw.siweventsservice.services.ActivityService;
import it.uniroma3.siw.siweventsservice.services.ToolService;
import it.uniroma3.siw.siweventsservice.validators.ActivityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminActivityController {
	@Autowired
	private ActivityService activityService;

	@Autowired
	private ToolService toolService;

	@Autowired
	private ActivityValidator activityValidator;

	@GetMapping("/activityForm")
	public String getActivityForm (Model model) {
		model.addAttribute("tools", toolService.findAll());
		model.addAttribute("activity", new Activity());
		return "activities/activityForm.html";
	}

	@PostMapping("/activity")
	public String newActivity (@Valid @ModelAttribute("activity") Activity activity, BindingResult bindingResult, Model model) {
		this.activityValidator.validate(activity, bindingResult);

		if (!bindingResult.hasErrors()) { // se i dati sono corretti
			this.activityService.save(activity); // salvo un oggetto Activity
			model.addAttribute("activity", this.activityService.findById(activity.getId()));
			return "activities/activity.html";      // presenta un pagina con la activity appena salvata
		} else {
			model.addAttribute("tools", toolService.findAll());
			return "activities/activityForm.html"; // ci sono errori, torna alla form iniziale
		}
	}

	@GetMapping("/edit/activity/{id}")
	public String getActivityForm (@PathVariable Long id, Model model) {
		model.addAttribute("tools", toolService.findAll());
		model.addAttribute("activity", activityService.findById(id));
		return "activities/editActivityForm.html";
	}

	@Transactional
	@PostMapping("/edit/activity/{id}")
	public String editActivity (@PathVariable Long id, @Valid @ModelAttribute("activity") Activity activity, BindingResult bindingResults, Model model) {
		Activity oldActivity = activityService.findById(id);
		if (!oldActivity.equals(activity))
			this.activityValidator.validate(activity, bindingResults);
		if (!bindingResults.hasErrors()) {
			oldActivity.setId(activity.getId());
			oldActivity.setName(activity.getName());
			oldActivity.setDescription(activity.getDescription());
			oldActivity.setDuration(activity.getDuration());
			oldActivity.setToolList(activity.getToolList());
			this.activityService.save(oldActivity);
			model.addAttribute("activity", activity);
			return "activities/activity.html";
		} else {
			return "activities/editActivityForm.html";
		}

	}

	@GetMapping("/delete/activity/{id}")
	public String toDeleteActivity (@PathVariable("id") Long id, Model model) {
		model.addAttribute("activity", this.activityService.findById(id));
		return "activities/activityConfirmDelete.html";
	}

	@PostMapping("/delete/activity/{id}")
	public String deleteActivity (@PathVariable("id") Long id, Model model) {
		try {
			this.activityService.deleteActivityById(id);
			model.addAttribute("activities", this.activityService.findAll());
			return "activities/activities.html";
		} catch (Exception e) {
			return "error.html";
		}
	}

}
