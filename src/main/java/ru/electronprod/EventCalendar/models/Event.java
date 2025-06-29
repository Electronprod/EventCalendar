package ru.electronprod.EventCalendar.models;

import jakarta.persistence.*;

@Entity
@Table(name = "eventcalendar_data")
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

	public Event(int id, String date, String title, String content, boolean verified, String author) {
		this.id = id;
		this.date = date;
		this.title = title;
		this.content = content;
		this.verified = verified;
		this.author = author;
	}

	public Event() {
	}

	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public boolean isVerified() {
		return verified;
	}

	public String getAuthor() {
		return author;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
