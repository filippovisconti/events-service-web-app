package it.uniroma3.siw.siweventsservice.services;


import it.uniroma3.siw.siweventsservice.models.Activity;
import it.uniroma3.siw.siweventsservice.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityService {
	@Autowired
	private ActivityRepository activityRepository;

	@Transactional
	public Activity save (Activity activity) {
		return activityRepository.save(activity);
	}

	@Transactional
	public void deleteActivityById (Long id) {
		activityRepository.deleteById(id);
	}

	public boolean hasDuplicate (Activity activity) {
		return activityRepository.existsByName(activity.getName());
	}

	public List<Activity> findAll () {
		List<Activity> l = new ArrayList<>();
		for (Activity i : activityRepository.findAll()) l.add(i);
		return l;
	}

	public int activityNumber () {
		return findAll().size();
	}

	public Activity findById (Long id) {
		var p = activityRepository.findById(id);
		if (p.isPresent()) return p.get();
		return null;
	}
}
