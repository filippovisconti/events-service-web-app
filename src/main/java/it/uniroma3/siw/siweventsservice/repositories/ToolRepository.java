package it.uniroma3.siw.siweventsservice.repositories;

import it.uniroma3.siw.siweventsservice.models.Tool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ToolRepository extends CrudRepository<Tool, Long> {

	boolean existsByName(String name);
}
