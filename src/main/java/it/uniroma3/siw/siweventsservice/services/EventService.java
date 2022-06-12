package it.uniroma3.siw.siweventsservice.services;

import it.uniroma3.siw.siweventsservice.models.Activity;
import it.uniroma3.siw.siweventsservice.models.Event;
import it.uniroma3.siw.siweventsservice.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Transactional
	public Event save (Event event){
		return eventRepository.save(event);
	}

	@Transactional
	public void deleteEventById(Long id){
		eventRepository.deleteById(id);
	}

	public boolean hasDuplicate(Event event){
		return eventRepository.existsByNameAndDate(event.getName(), event.getDate());
	}
	public List<Event> findAll () {
		List<Event> l = new ArrayList<>();
		for (Event i : eventRepository.findAll()) l.add(i);
		return l;
	}

	public Event findById (Long id) {
		var p = eventRepository.findById(id);
		if (p.isPresent()) return p.get();
		return null;
	}
}
