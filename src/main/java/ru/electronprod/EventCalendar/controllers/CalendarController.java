package ru.electronprod.EventCalendar.controllers;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import ru.electronprod.EventCalendar.models.Event;
import ru.electronprod.EventCalendar.repositories.EventRepository;

@Controller
public class CalendarController {
	@Autowired
	private EventRepository database;

	@GetMapping("/")
	public String calendar() {
		return "index";
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/api/addevent")
	public ResponseEntity<String> createEvent(@RequestBody Event event) {
		event.setVerified(false);
		event.setDate(event.getDate().replaceAll("-", "."));
		boolean result = database.save(event) != null;
		JSONObject answer = new JSONObject();
		answer.put("result", result);
		if (result) {
			return ResponseEntity.ok(answer.toJSONString());
		}
		return ResponseEntity.internalServerError().body(answer.toJSONString());
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/api/getevents")
	public ResponseEntity<String> getEvents(@RequestParam boolean verified) {
		JSONArray events = new JSONArray();
		List<Event> evs = database.findAllByVerified(verified, Sort.by(Order.asc("date")));
		evs.forEach(event -> {
			JSONObject JSEvent = new JSONObject();
			JSEvent.put("id", event.getId());
			JSEvent.put("date", event.getDate());
			JSEvent.put("title", event.getTitle());
			JSEvent.put("content", event.getContent());
			JSEvent.put("author", event.getAuthor());
			events.add(JSEvent);
		});
		return ResponseEntity.ok(events.toJSONString());
	}
}
