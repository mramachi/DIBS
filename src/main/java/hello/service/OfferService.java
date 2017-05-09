package hello.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.model.Event;
import hello.model.Offer;
import hello.repo.OfferRepository;

@Service
public class OfferService {

    @Autowired(required=true)
    private OfferRepository offerRepository;

    public OfferRepository getOfferRepository(){
    	return this.offerRepository;
    }
    
    public boolean lock(long event, long[] seats){
    	Locker l = new Locker(event,seats);
        Thread t = new Thread(l,"mijn thread");
      
        t.start();
        synchronized(t){
            try{
            	System.out.println("bbbb: 1 " + l.b);
                System.out.println("Waiting for t1 to complete...");
                t.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        	if(l.b){
        		for(long seat : seats){
        			Offer s = offerRepository.findByOfferID(seat);
        			s.setOfferTicksAvl(0);
        			offerRepository.saveAndFlush(s);
        		}
        		return true;
        	}
        	
        	
        	else return false;
            
        }
    	
    }


}