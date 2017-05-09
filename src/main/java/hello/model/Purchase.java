package hello.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.io.Serializable;
import java.sql.Timestamp;


import hello.repo.PurchaseID;

@SuppressWarnings("serial")
@Entity
@Table(name = "public.Purchase")
public class Purchase implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long purchaseID;
	
	@OneToOne
	@JoinColumn(name="offerID")
	private Offer offer;
	
	@OneToOne
	@JoinColumn(name="userID")
	private User user;
	
	@Column(name="purchaseTime")
	private Timestamp purchaseTime;
	
	@Column(name="canceled")
	private int canceled;
	
	@Column(name="payed")
	private int payed;
	
	public Purchase(){} //For JPA

	public Purchase(Offer offer, User user, Timestamp purchaseTime) {
		this.offer = offer;
		this.user = user;
		this.purchaseTime = purchaseTime;
	}
	
	public Offer getOfferID() {
		return this.offer;
	}

	public void setOfferID(Offer offer) {
		this.offer = offer;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(Timestamp purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public int getCanceled() {
		return canceled;
	}

	public void setCanceled(int canceled) {
		this.canceled = canceled;
	}

	public int getPayed() {
		return payed;
	}

	public void setPayed(int payed) {
		this.payed = payed;
	}
	
	public long getPurchaseID(){
		return this.purchaseID;
	}

	
	 
}
