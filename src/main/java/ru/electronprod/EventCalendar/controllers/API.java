package ru.electronprod.EventCalendar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.electronprod.EventCalendar.services.DateEventService;

@RestController
public class API {
	@Autowired
	private DateEventService des;

	@GetMapping("/daydata")
	public String getDayData(@RequestParam String day) {
		return des.getDayEvents(day).toJSONString();
	}
}
