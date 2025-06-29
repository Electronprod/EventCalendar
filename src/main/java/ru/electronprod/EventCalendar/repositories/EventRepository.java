package ru.electronprod.EventCalendar.repositories;

import ru.electronprod.EventCalendar.models.*;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
	List<Event> findAllByDate(String date);

	List<Event> findAllByVerified(boolean verified, Sort sort);
}
