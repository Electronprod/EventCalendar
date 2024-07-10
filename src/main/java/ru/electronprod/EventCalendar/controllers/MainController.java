package ru.electronprod.EventCalendar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.electronprod.EventCalendar.models.DateEvent;
import ru.electronprod.EventCalendar.services.DateEventService;

@Controller
public class MainController {
	@Autowired
	private DateEventService des;

	@GetMapping("/send")
	public String recordData(@RequestParam("name") String name, @RequestParam("content") String content,
			@RequestParam("date") String date) {
		try {
			des.addDateEvent(new DateEvent(date, content, name));
			return "redirect:/?success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/?fail";
	}
}
