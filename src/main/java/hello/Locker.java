package hello;
/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import hello.model.Offer;
import hello.repo.OfferRepository;

//@Service
public final class Locker implements Runnable {

	public static volatile Multimap<String,String> catalogus = ArrayListMultimap.create();
	public static volatile Map<String,Integer> seats = new HashMap<String,Integer>();
	public static volatile Map<String,ReentrantLock > eventtqbel = new HashMap<String, ReentrantLock>();
	public static volatile int teller=0;
	public String event;
	public long userid;
	public boolean firstuse=false;
	public List<String> seatToBook = new ArrayList<String>();
	@Autowired
	OfferRepository offerRepository;

	
	public Locker(){
		
	}
	
	public Locker(String event,List<String> seats, long userid) {
		this.event=event;
		this.seatToBook = seats;
		this.userid=userid;
			
	}
	public void fillLocker(){

			Offer f = this.offerRepository.findByOfferID(8);
		
			System.out.println("kldfklqdjsklfjlkdsjlkfjdslkqjflkdsqj");
			
			eventtqbel.put("1", new ReentrantLock());
			seats.put("1", 1);
			seats.put("stoel2", 1);
			seats.put("stoel3", 1);
			seats.put("stoel4", 1);
			
			eventtqbel.put("event2", new ReentrantLock());
			seats.put("stoel1", 1);
			seats.put("stoel2", 1);
			seats.put("stoel3", 1);
			seats.put("stoel4", 1);
		
	}

		
	@Override
	public void run() {
		
		eventtqbel.get(event).lock();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //slapen om te testen
		try{
			Iterator it= seatToBook.iterator();
			if(it.hasNext()) it.next();
			while(it.hasNext()){
				String s = (String) it.next();
				int aantalStoelen= seats.get(s);
				System.out.println("stoel is " + s + " aantal is " + aantalStoelen);
				if(aantalStoelen==1){
				seats.put(s, 0);}
				else{
					 System.out.println("bestelling mislukt");//stoelen al bezet, eerdere nog vrijgeven
					break;
				}
			}
			
		} 
		finally{
			eventtqbel.get(event).unlock();

		}
			
		}

}