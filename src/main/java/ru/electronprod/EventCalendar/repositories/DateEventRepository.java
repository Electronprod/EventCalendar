package ru.electronprod.EventCalendar.repositories;

import ru.electronprod.EventCalendar.models.*;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DateEventRepository extends JpaRepository<DateEvent, Integer> {
	List<DateEvent> findAllByDate(String date);
}
