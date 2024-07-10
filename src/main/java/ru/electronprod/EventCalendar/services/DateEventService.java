package ru.electronprod.EventCalendar.services;

import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.electronprod.EventCalendar.models.DateEvent;
import ru.electronprod.EventCalendar.repositories.DateEventRepository;

@Service
public class DateEventService {
	@Autowired
	private DateEventRepository rep;

	public void addDateEvent(DateEvent e) throws Exception {
		if (e == null) {
			throw new Exception("Error adding: DateEvent=null");
		}
		rep.save(e);
	}

	public JSONArray getDayEvents(String date) {
		JSONArray arr = new JSONArray();
		List<DateEvent> events = rep.findAllByDate(date);
		for (int i = 0; i < events.size(); i++) {
			DateEvent event = events.get(i);
			JSONObject obj = new JSONObject();
			obj.put("content", event.getContent());
			obj.put("by", event.getName());
			arr.add(obj);
		}
		if (arr.isEmpty()) {
			JSONObject obj = new JSONObject();
			obj.put("by", "Event Calendar");
			obj.put("content", "Пока тут ничего нет :(");
			arr.add(obj);
		}
		return arr;
	}
}
