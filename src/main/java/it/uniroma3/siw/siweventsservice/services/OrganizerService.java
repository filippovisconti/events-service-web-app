package it.uniroma3.siw.siweventsservice.services;

import it.uniroma3.siw.siweventsservice.models.Organizer;
import it.uniroma3.siw.siweventsservice.repositories.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizerService {

	@Autowired
	private OrganizerRepository organizerRepository;

	@Transactional
	public Organizer save (Organizer organizer){
		return organizerRepository.save(organizer);
	}

	@Transactional
	public void deleteOrganizerById(Long id){
		organizerRepository.deleteById(id);
	}

	public boolean hasDuplicate(Organizer organizer){
		return organizerRepository.existsByFirstNameAndLastName(organizer.getFirstName(), organizer.getLastName());
	}
	
	public List<Organizer> findAll () {
		List<Organizer> l = new ArrayList<>();
		for (Organizer i : organizerRepository.findAll()) l.add(i);
		return l;
	}
}
