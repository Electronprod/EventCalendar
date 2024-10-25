package ru.electronprod.EventCalendar.repositories;

import org.springframework.stereotype.Repository;

import ru.electronprod.EventCalendar.models.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByLogin(String login);
}
