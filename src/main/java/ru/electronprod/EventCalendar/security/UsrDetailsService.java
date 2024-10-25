package ru.electronprod.EventCalendar.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.electronprod.EventCalendar.models.User;
import ru.electronprod.EventCalendar.repositories.UserRepository;

@Service
public class UsrDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository urp;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = urp.findByLogin(username);

		if (user.isEmpty())
			throw new UsernameNotFoundException("User not found");

		return new UsrDetails(user.get());
	}

}
