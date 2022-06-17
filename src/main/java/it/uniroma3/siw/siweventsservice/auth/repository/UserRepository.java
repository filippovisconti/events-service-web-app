package it.uniroma3.siw.siweventsservice.auth.repository;

import it.uniroma3.siw.siweventsservice.auth.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);

}
