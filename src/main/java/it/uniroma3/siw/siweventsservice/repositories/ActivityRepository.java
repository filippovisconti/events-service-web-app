package it.uniroma3.siw.siweventsservice.repositories;

import it.uniroma3.siw.siweventsservice.models.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Long> {
	boolean existsByName(String name);
}
