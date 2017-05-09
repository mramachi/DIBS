package hello.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.model.Offer;
import hello.model.Purchase;
import hello.model.User;
import hello.repo.OfferRepository;
import hello.repo.PurchaseRepository;
import hello.repo.UserRepository;

@Service
public class CancelHandlerDBThread implements Runnable {
	//DB Repos
	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
	
	
	//this object has to be locked when setting attributes to start a thread
	public static Object lock;
	private long userID;
	private long[] offerIDs;
	private long eventID;
	
	
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public long[] getOfferID() {
		return offerIDs;
	}
	public void setOfferID(long[] offerID) {
		this.offerIDs = offerID;
	}
	public long getEventID() {
		return eventID;
	}
	public void setEventID(long eventID) {
		this.eventID = eventID;
	}
	@Override
	public void run() {
		boolean userOfferExists = true;
		
		//check if given data exists exists
		User u = userRepository.findByUserID(userID);
		if (u==null) userOfferExists = true;
		List<Offer> offerList = new ArrayList<Offer>();
		for(int i=0; i < offerIDs.length; i++){
			Offer o = offerRepository.findByOfferID(offerIDs[i]);
			if(o == null) userOfferExists = false;
			offerList.add(o);
		}
		List<Purchase> pList = new ArrayList<Purchase>();
		if(userOfferExists){
			//vind al de purchases die hier aan voldoen.
			System.out.println(offerIDs.length);
			for(int i=0; i < offerIDs.length; i++){
				Purchase p = purchaseRepository.findByOfferAndUser(offerList.get(i), u);
				System.out.println(p);
				pList.add(p);
			}
			//check if all purchases have: payed = 0; canceled = 0;
			for(int i = 0; i < pList.size(); i++){
				Purchase p = pList.get(i);
				if ((p.getCanceled() == 0) && (p.getPayed() == 0)){
					//get lock in memory
					ReentrantLock lock = Locker.eventTable.get(eventID);
					lock.lock();
						//should be true, but check anyway
					System.out.println(Locker.seats);
						if(Locker.seats.get(p.getOffer().getOfferID())==false) {
							Locker.seats.get(p.getOffer().getOfferID());
							Locker.seats.remove(p.getOffer().getOfferID());
							Locker.seats.put(p.getOffer().getOfferID(), true);
						}
						else {
							System.err.println("..");
						}
					lock.unlock();
					//adjust in DB
					p.setCanceled(1);
					p.setPayed(0);
					purchaseRepository.saveAndFlush(p);
				}
				else {
					System.err.println("Purcahse with id("+pList.get(i).getPurchaseID()+") is either already canceled or is already payed.");
				}
			}
		}
		else {
			//handle the exception
			System.err.println("User or Offer does not exist in /cancelbooking");
		}
		
	}
	
}
