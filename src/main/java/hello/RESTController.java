package hello;

import org.springframework.web.bind.annotation.RestController;

import hello.model.Event;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RESTController {
	
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
 
    @RequestMapping(value = "/filterEvents")
    public List<Event> GetEventResult(@RequestParam(value="kind", required=true, defaultValue="All") String kind, @RequestParam(value="Date", required=true, defaultValue="All") String date, @RequestParam(value="Place", required=true, defaultValue="All") String place, @RequestParam(value="page", required=true, defaultValue="1") int page, Model model) {
        //Hier heb ik dus een functie nodig als volgt:
    	//int page geeft welke 9 evenementen ik nodig heb: dus 1 => 1e 9 evenementen, 2=> 2e 9 evenementen,....
    	//name, place, kind moet op gefiltered worden
    	System.err.println("Komt in filterevents");
    	
    	//Initialize events
    	List<Event> fetchedEvents = new ArrayList<Event>();
    	List<Event> filteredEvents = new ArrayList<Event>();
    	
    	//fetch events from DB
    	
    	int k = 9;
    	
    	/*
    	for(int i =(page-1)*k; i < Math.min(page*k, fetchedEvents.size()); i++) {
    		//filteredEvents.add(fetchedEvents.get(i));
    		Event e = new Event(""+i);
    		filteredEvents.add(e);
    	}
    	*/
    	
    	
    	for(int i =0; i < k; i++) {
    		//filteredEvents.add(fetchedEvents.get(i));
    		//Event e = new Event("Event_"+i, "locatie"+i);
    		//filteredEvents.add(e);
    	}
    	
    	//return new Event(""+1);
    	return filteredEvents; 	
    } 
  
}
