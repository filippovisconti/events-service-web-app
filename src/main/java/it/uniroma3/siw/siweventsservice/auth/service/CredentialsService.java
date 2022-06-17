package it.uniroma3.siw.siweventsservice.auth.service;

import it.uniroma3.siw.siweventsservice.auth.models.Credentials;
import it.uniroma3.siw.siweventsservice.auth.models.User;
import it.uniroma3.siw.siweventsservice.auth.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CredentialsService {

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	private CredentialsRepository credentialsRepository;

	public Credentials getCredentials (Long id) {
		var p = credentialsRepository.findById(id);
		if (p.isPresent()) return p.get();
		return null;
	}

	public Credentials getCredentials (String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}

	public User getUserDetails (String username) {
		try {
			return getCredentials(username).getUser();
		} catch (NullPointerException e) {
			System.out.println("No user found. " + e.getMessage());
			return null;
		} catch (Exception e) {
			System.out.println("Generic error: " + e.getMessage());
			return null;
		}
	}

	@Transactional
	public Credentials saveCredentials (Credentials credentials) {
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return credentialsRepository.save(credentials);
	}

}

