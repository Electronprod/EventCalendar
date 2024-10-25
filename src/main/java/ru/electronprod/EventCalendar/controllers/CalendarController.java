package ru.electronprod.EventCalendar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarController {
	@GetMapping("/")
	public String calendar() {
		return "index";
	}
}
