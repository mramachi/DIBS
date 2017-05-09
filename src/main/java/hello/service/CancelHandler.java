package hello.service;

import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hello.repo.OfferRepository;
import hello.repo.PurchaseRepository;
import hello.repo.UserRepository;

@Service
public class CancelHandler implements Runnable {
	//DB Repos
	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	PurchaseRepository purchaseRepository;
	
	//DB Thread
	@Autowired
	CancelHandlerDBThread dbThread;
	
	
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
		Thread t;
		// lock eventtable
		if (!Locker.eventTable.containsKey(eventID))
			Locker.eventTable.put(eventID, new ReentrantLock());
		// set DB access attributes
		synchronized (dbThread) {
			dbThread.setEventID(eventID);
			dbThread.setOfferID(offerIDs);
			dbThread.setUserID(userID);
			t = new Thread(dbThread, "Cancel_DB_Thread");
			t.start();
		}
	}
	
}
