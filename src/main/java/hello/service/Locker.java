package hello.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import hello.Application;
import hello.model.Event;
import hello.model.Offer;
import hello.model.User;
import hello.repo.EventRepository;
import hello.repo.OfferRepository;
import hello.repo.UserRepository;

@Service
public final class Locker implements Runnable {

	public static volatile Multimap<String,String> catalogus = ArrayListMultimap.create();
	public static volatile Map<Long,Boolean> seats = new HashMap<Long,Boolean>();
	public static volatile Map<Long,ReentrantLock > eventTable = new HashMap<Long, ReentrantLock>();
	public static volatile int teller=0;
	public long event;
	public User user;
	long[] seatToBook;
	public boolean b = false;
	public static boolean firstUse=true;
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Autowired
	OfferRepository offerRepo;
	/*
	@Autowired
	EventRepository eventRepo;
	*/
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	EventService eventService;
	
	
	@Autowired
	OfferHandler offerHan;
	
	public Locker(){ 
	}
	
	public Locker(long e, long[] s){
		this.event = e;
		this.seatToBook = s;
	}
	
	public void setSeatToBook(long[] seats){
		this.seatToBook = seats;
	}
	
	public void setEvent(long event){
		this.event = event;
	}
	public void setUser(long user){
		User u=userRepo.findByFacebookID(user);
		this.user=u;
	}
	
	public void fillLocker(){
		
		
		if(firstUse==true){
			List<Event> events = eventService.getEventRepository().findAll();
			System.out.println("BB");
			for(Event e : events){
				
				eventTable.put(e.getEventID(), new ReentrantLock());
				
				List<Offer> f= offerRepo.findByEvent(e);
				
				for(Offer of : f){
					
					seats.put(of.getOfferID(), true);
				}
				
				
			}
			System.out.println("table filled");
			}
		
		/*
		*/
	}
	
	public void addEvent(long eventid){
		eventTable.put(eventid, new ReentrantLock());
	}

	public Locker getLocker(){
		return this;
	}
	
	@Override
	public void run() {
		eventTable.get(event).lock();
		
		try{
			for(long seat : seatToBook){
				if(seats.get(seat)==true){
					System.out.println(seats.toString());
					System.out.println("stoel vrij + stoel is " + seat);
					seats.put(seat, false);
					System.out.println(seats.toString());
					b=true;	
				}
				else {
					System.out.println("stoel bezet stoel is + " + seat);
					b=false;	
				}
			}
		} 
		finally{
			eventTable.get(event).unlock();
			
		}
		//het zetten van de attributen moet synchronized zijn op de klasse offerHan, niet?
		//Indien niet zouden twee threads de attributen van die klasse kunnen aanpassen en krijg je een mix van argumenten die je aan je thread wil meegeven
		if(b==true){
		offerHan.setEvent(event);
		offerHan.setSeatToBook(seatToBook);
		System.out.println("user is: " + user);
		offerHan.setUser(user);
		Thread t = new Thread(offerHan,"offer thread");
		t.start();
		}

	}

}