package it.uniroma3.siw.siweventsservice.controllers.user;

import it.uniroma3.siw.siweventsservice.services.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/protected")
public class UserToolController {
	@Autowired
	private ToolService toolService;

	@GetMapping("/tool/{id}")
	public String getTool (@PathVariable("id") Long id, Model model) {
		model.addAttribute("tool", this.toolService.findById(id));
		return "tools/tool";
	}
}
