package ru.electronprod.EventCalendar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.electronprod.EventCalendar.models.User;
import ru.electronprod.EventCalendar.repositories.UserRepository;
@Service
public class AuthHelper {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 ***REMOVED***Returns User object from SpringContextHolder
	 ***REMOVED***
	 ***REMOVED***@return user
	 */
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UsrDetails details = (UsrDetails) authentication.getPrincipal();
		return details.getUser();
	}

	/**
	 ***REMOVED***It hashes password and saves user to database
	 ***REMOVED***
	 ***REMOVED***@param person
	 */
	@Transactional
	public void register(User person) {
		person.setPassword(passwordEncoder.encode(person.getPassword()));
		userRepository.save(person);
	}

	/**
	 ***REMOVED***Checks login for availability
	 ***REMOVED***
	 ***REMOVED***@param login
	 ***REMOVED***@return true - found user/ false - not found
	 */
	@Transactional(readOnly = true)
	public boolean exists(String login) {
		return userRepository.findByLogin(login).isPresent();
	}
}
