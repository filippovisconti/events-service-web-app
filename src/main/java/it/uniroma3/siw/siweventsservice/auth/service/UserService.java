package it.uniroma3.siw.siweventsservice.auth.service;

import it.uniroma3.siw.siweventsservice.auth.models.User;
import it.uniroma3.siw.siweventsservice.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUser (Long id) {
		var p = userRepository.findById(id);
		if (p.isPresent()) return p.get();
		return null;
	}

	public List<User> findAll () {
		List<User> users = new ArrayList<>();
		for (User u : userRepository.findAll()) users.add(u);
		return users;
	}

	public int userNumber(){
		return findAll().size();
	}
	@Transactional
	public User saveUser (User user) {
		return userRepository.save(user);
	}


	public boolean hasDuplicate(User user){
		return userRepository.existsByFirstNameAndLastName(user.getFirstName(), user.getLastName());
	}

	@Transactional
	public User save (User user){
		return userRepository.save(user);
	}
}
