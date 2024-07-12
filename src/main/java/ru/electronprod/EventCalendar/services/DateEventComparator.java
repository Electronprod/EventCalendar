package ru.electronprod.EventCalendar.services;
import java.util.Comparator;
import ru.electronprod.EventCalendar.models.DateEvent;

public class DateEventComparator implements Comparator<DateEvent> {
    @Override
    public int compare(DateEvent event1, DateEvent event2) {
        return event1.getDate().compareTo(event2.getDate());
    }
}