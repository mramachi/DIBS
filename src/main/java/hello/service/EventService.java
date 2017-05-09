package hello.service;

import java.sql.Date;
import java.sql.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.model.Event;
import hello.model.Location;
import hello.repo.EventRepository;

@Service
public class EventService {

    @Autowired(required=true)
    private EventRepository eventRepository;

    public Event makeAnEvent(long partnerID, String eventDescription, String eventLink, boolean eventIsVisible, String eventPaymentText, Date eventDate, Time eventTimeStart, Time eventTimeStop, Location location, String eventName) {
        Event e = new Event(partnerID, eventDescription, eventLink, eventIsVisible, eventPaymentText, eventDate, eventTimeStart, eventTimeStop, location, eventName);

        eventRepository.saveAndFlush(e);
        return e;
    }

    
    public EventRepository getEventRepository(){
    	return this.eventRepository;
    }

}