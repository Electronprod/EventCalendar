package ru.electronprod.EventCalendar.controllers;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.electronprod.EventCalendar.models.Event;
import ru.electronprod.EventCalendar.models.User;
import ru.electronprod.EventCalendar.repositories.EventRepository;
import ru.electronprod.EventCalendar.repositories.UserRepository;
import ru.electronprod.EventCalendar.security.AuthHelper;

@Controller
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
	@Autowired
	private EventRepository database;
	@Autowired
	private UserRepository users;
	@Autowired
	private AuthHelper auth;

	@GetMapping("/admin")
	public String overview() {
		return "admin/overview";
	}

	@GetMapping("/admin/usermgr")
	public String usermgr(Model model) {
		model.addAttribute("users", users.findAll());
		return "admin/usermgr";
	}

	@GetMapping("/admin/report")
	public String report(@RequestParam(required = false) String verified, Model model) {
		boolean v = true;
		if (verified != null) {
			v = Boolean.parseBoolean(verified);
		}
		model.addAttribute("events", database.findAllByVerified(v, Sort.by(Order.asc("date"))));
		return "admin/report";
	}

	@PostMapping("/admin/verify")
	public ResponseEntity<String> verify(@RequestParam int id) {
		Optional<Event> event = database.findById(id);
		if (event.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Event ev = event.get();
		ev.setVerified(true);
		database.save(ev);
		return ResponseEntity.ok("[]");
	}

	@PostMapping("/admin/deny")
	public ResponseEntity<String> deny(@RequestParam int id) {
		Optional<Event> event = database.findById(id);
		if (event.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Event ev = event.get();
		database.delete(ev);
		return ResponseEntity.ok("[]");
	}

	@PostMapping("/admin/delete_user")
	public ResponseEntity<String> deleteUser(@RequestParam int id) {
		Optional<User> user = users.findById(id);
		if (user.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		if (users.findAll().size() == 1) {
			return ResponseEntity.status(406).build();
		}
		users.delete(user.get());
		return ResponseEntity.ok("[]");
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/admin/create_user")
	public ResponseEntity<String> createUser(@RequestParam String login, @RequestParam String password) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setRole("ROLE_ADMIN");
		boolean result = auth.register(user);
		JSONObject answer = new JSONObject();
		answer.put("result", result);
		if (result) {
			return ResponseEntity.ok(answer.toJSONString());
		}
		return ResponseEntity.internalServerError().body(answer.toJSONString());
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/admin/getevent")
	public ResponseEntity<String> getEvents(@RequestParam int id) {
		Optional<Event> event1 = database.findById(id);
		if (event1.isEmpty())
			return ResponseEntity.badRequest().body("[]");
		Event event = event1.get();
		JSONObject JSEvent = new JSONObject();
		JSEvent.put("id", event.getId());
		JSEvent.put("date", event.getDate());
		JSEvent.put("title", event.getTitle());
		JSEvent.put("content", event.getContent());
		JSEvent.put("author", event.getAuthor());
		return ResponseEntity.ok(JSEvent.toJSONString());
	}
}
