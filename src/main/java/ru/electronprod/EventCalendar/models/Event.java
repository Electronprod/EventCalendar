package ru.electronprod.EventCalendar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "eventcalendar_data")
@Getter
@Setter
@AllArgsConstructor
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "date", nullable = false)
	private String date;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "content", nullable = false)
	private String content;
	@Column(name = "verified", nullable = false)
	private boolean verified;
	@Column(name = "author")
	private String author;
}
