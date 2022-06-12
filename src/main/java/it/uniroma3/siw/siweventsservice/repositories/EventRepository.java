package it.uniroma3.siw.siweventsservice.repositories;

import it.uniroma3.siw.siweventsservice.models.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface EventRepository extends CrudRepository<Event, Long> {

	boolean existsByName(String name);

	boolean existsByNameAndDate(String name, Date date);
}
