package ru.electronprod.EventCalendar.models;

import jakarta.persistence.*;

@Entity
@Table(name = "datetable")
public class DateEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "date")
	private String date;
	@Column(name = "content")
	private String content;
	@Column(name = "name")
	private String name;

	public DateEvent(String date, String content, String name) {
		this.content = content;
		this.date = date;
		this.name = name;
	}

	public DateEvent() {
	}

	public String getContent() {
		return content;
	}

	public String getName() {
		return name;
	}
	public String getDate() {
		return date;
	}
}
