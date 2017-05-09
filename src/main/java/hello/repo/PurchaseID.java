package hello.repo;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import hello.model.Offer;
import hello.model.User;

public class PurchaseID implements Serializable {
	
	private Offer offer;
	private User user;
	
	public PurchaseID(){
	}
	
	public PurchaseID(User u, Offer o){
		this.user = u;
		this.offer = o;
	}
	
	public User getUserID(){
		return this.user;
	}
	
	public Offer getOfferID(){
		return this.offer;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((user == null) ? 0 : user.hashCode());
		result = prime * result + (int)offer.getOfferID();
		return result;
	}
 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseID other = (PurchaseID) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (offer != other.offer)
			return false;
		return true;
	}

}
