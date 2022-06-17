package it.uniroma3.siw.siweventsservice.controllers.nonauthenticated;

import it.uniroma3.siw.siweventsservice.models.Tool;
import it.uniroma3.siw.siweventsservice.services.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/public")
public class PublicToolController {
	@Autowired
	private ToolService toolService;

	@GetMapping("/tools")
	public String getAllTools (Model model) {
		List<Tool> tools = toolService.findAll();
		model.addAttribute("tools", tools);
		return "tools/tools";
	}

}
