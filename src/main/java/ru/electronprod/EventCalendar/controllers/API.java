package ru.electronprod.EventCalendar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ru.electronprod.EventCalendar.services.DateEventService;

@RestController
public class API {
	@Autowired
	private DateEventService des;
	@Value("${report.secretkey}")
	private String secret;

	@GetMapping("/daydata")
	public String getDayData(@RequestParam String day) {
		return des.getDayEvents(day).toJSONString();
	}

	@GetMapping("/report")
	public String report(@RequestParam("key") String key) {
		// Checking key
		if (!key.equals(secret)) {
			// throw 401 error
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect key.");
		}
		return des.generateReport();
	}
}
