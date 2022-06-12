package it.uniroma3.siw.siweventsservice.services;

import it.uniroma3.siw.siweventsservice.models.Tool;
import it.uniroma3.siw.siweventsservice.repositories.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToolService {

	@Autowired
	private ToolRepository toolRepository;

	@Transactional
	public Tool save (Tool tool){
		return toolRepository.save(tool);
	}

	@Transactional
	public void deleteToolById(Long id){
		toolRepository.deleteById(id);
	}

	public boolean hasDuplicate(Tool tool){
		return toolRepository.existsByName(tool.getName());
	}
	public List<Tool> findAll () {
		List<Tool> l = new ArrayList<>();
		for (Tool i : toolRepository.findAll()) l.add(i);
		return l;
	}

	public Tool findById (Long id) {
		var p = toolRepository.findById(id);
		if (p.isPresent()) return p.get();
		return null;
	}
}
