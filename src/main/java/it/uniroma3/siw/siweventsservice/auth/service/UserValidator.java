package it.uniroma3.siw.siweventsservice.auth.service;

import it.uniroma3.siw.siweventsservice.auth.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports (Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate (Object target, Errors errors) {
		if (this.userService.hasDuplicate((User) target)) {
			errors.reject("user.duplicate", "duplicate user");
		}
	}
}
