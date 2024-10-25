//package ru.electronprod.EventCalendar.services;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import ru.electronprod.EventCalendar.models.Event;
//import ru.electronprod.EventCalendar.repositories.DateEventRepository;
//
//@Service
//public class DateEventService {
//	@Autowired
//	private DateEventRepository rep;
//
//	public void addDateEvent(Event e) throws NullPointerException {
//		if (e == null) {
//			throw new NullPointerException("Error adding: DateEvent=null");
//		}
//		rep.save(e);
//	}
//
//	public JSONArray getDayEvents(String date) {
//		JSONArray arr = new JSONArray();
//		List<Event> events = rep.findAllByDate(date);
//		for (Event event : events) {
//			JSONObject obj = new JSONObject();
//			obj.put("content", event.getContent());
//			obj.put("by", event.getName());
//			arr.add(obj);
//		}
//		if (arr.isEmpty()) {
//			JSONObject obj = new JSONObject();
//			obj.put("by", "Event Calendar");
//			obj.put("content", "Пока тут ничего нет :(");
//			arr.add(obj);
//		}
//		return arr;
//	}
//
//	// Bad
//	public String generateReport() {
//		List<Event> events = rep.findAll();
//		Collections.sort(events, new DateEventComparator());
//		String result = "<head><title>Data Report</title><meta charset=\"UTF-8\"><style type=\"text/css\">\r\n"
//				+ "      TABLE {\r\n" + "       width: 500px; /* Ширина таблицы */\r\n"
//				+ "       border-collapse: collapse; /* Убираем двойные линии между ячейками */\r\n" + "      }\r\n"
//				+ "      TD, TH {\r\n" + "       padding: 3px; /* Поля вокруг содержимого таблицы */\r\n"
//				+ "       border: 1px solid black; /* Параметры рамки */\r\n" + "      }\r\n" + "      TH {\r\n"
//				+ "       background: #b0e0e6; /* Цвет фона */\r\n" + "      }\r\n" + "     </style></head><table>\r\n"
//				+ "    <tr>\r\n" + "        <th>Дата</th>\r\n" + "        <th>Автор</th>\r\n"
//				+ "        <th>Контент</th>\r\n" + "    </tr>";
//		for (Event e : events) {
//			result = result + "<tr><td>" + e.getDate() + "</td><td>" + e.getName() + "</td><td>" + e.getContent()
//					+ "</td></tr>";
//		}
//		return result + "</table>";
//	}
//}
