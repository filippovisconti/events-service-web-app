package it.uniroma3.siw.siweventsservice.controllers.user;

import it.uniroma3.siw.siweventsservice.models.Tool;
import it.uniroma3.siw.siweventsservice.services.ToolService;
import it.uniroma3.siw.siweventsservice.validators.ToolValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/protected")
public class UserToolController {
	@Autowired
	private ToolService toolService;

	@GetMapping("/tool/{id}")
	public String getTool(@PathVariable("id") Long id, Model model) {
		model.addAttribute("tool", this.toolService.findById(id));
		String nextPage = "tool.html";
		return nextPage;
	}
}
