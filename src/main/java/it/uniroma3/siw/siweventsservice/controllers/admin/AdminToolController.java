package it.uniroma3.siw.siweventsservice.controllers.admin;

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

@Controller
@RequestMapping("/admin")
public class AdminToolController {
	@Autowired
	private ToolService toolService;

	@Autowired
	private ToolValidator toolValidator;

	@GetMapping("/toolForm")
	public String getToolForm (Model model) {
		model.addAttribute("tool", new Tool());
		return "tools/toolForm.html";
	}

	@PostMapping("/tool")
	public String newTool (@Valid @ModelAttribute("tool") Tool tool, BindingResult bindingResult, Model model) {
		this.toolValidator.validate(tool, bindingResult);
		if (!bindingResult.hasErrors()) { // se i dati sono corretti
			this.toolService.save(tool); // salvo un oggetto Tool
			model.addAttribute("tool", this.toolService.findById(tool.getId()));
			return "tools/tool.html";      // presenta un pagina con la tool appena salvata
		} else
			return "tools/toolForm.html"; // ci sono errori, torna alla form iniziale

	}

	@GetMapping("/edit/tool/{id}")
	public String getBuffetForm (@PathVariable Long id, Model model) {
		model.addAttribute("tool", toolService.findById(id));
		return "tools/editToolForm.html";
	}

	@Transactional
	@PostMapping("/edit/tool/{id}")
	public String editBuffet (@PathVariable Long id, @Valid @ModelAttribute("tool") Tool tool, BindingResult bindingResults, Model model) {
		Tool oldTool = toolService.findById(id);
		if (!oldTool.equals(tool))
			this.toolValidator.validate(tool, bindingResults);
		if (!bindingResults.hasErrors()) {
			oldTool.setId(tool.getId());
			oldTool.setName(tool.getName());
			oldTool.setDescription(tool.getDescription());
			oldTool.setCost(tool.getCost());
			this.toolService.save(oldTool);
			model.addAttribute("tool", tool);
			return "tools/tool.html";
		} else {
			return "tools/editToolForm.html";
		}
	}

	@GetMapping("/delete/tool/{id}")
	public String toDeleteTool (@PathVariable("id") Long id, Model model) {
		model.addAttribute("tool", this.toolService.findById(id));
		return "tools/toolConfirmDelete.html";
	}

	@PostMapping("/delete/tool/{id}")
	public String deleteTool (@PathVariable("id") Long id, Model model) {
		try {
			this.toolService.deleteToolById(id);
			return "tools/tools.html";
		} catch (Exception e) {
			return "error.html";
		}
	}

}
