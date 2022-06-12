package it.uniroma3.siw.siweventsservice.repositories;

import it.uniroma3.siw.siweventsservice.models.Organizer;
import it.uniroma3.siw.siweventsservice.models.Tool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganizerRepository extends CrudRepository<Organizer, Long> {

	boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
