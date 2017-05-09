package hello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQuery(name = "findAllOffers", query = "SELECT e from Offer e")
@Table(name = "public.Offer")
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "offerID")
	private long offerID;

	@ManyToOne
	@JoinColumn(name = "eventID")
	@JsonIgnore // prevents infinite JSON recursion
	private Event event;

	@Column(name = "seatColumn")
	private String seatColumn;
	
	@Column(name="seatRow")
	private String seatRow;
	
	@Column(name= "offerPartnerID")
	private long offerPartnerID;

	@Column(name = "offerTicksAvl")
	private int offerTicksAvl;

	@Column(name = "offerIsVisible")
	private boolean offerIsVisible;

	@Column(name = "offerPrice")
	private double offerPrice;

	@Column(name = "offerDiscount")
	private double offerDiscount;

	@Column(name = "offerViewCount")
	private int offerViewCount;

	@Column(name = "offerPicturePath")
	private String offerPicturePath;

	@Column(name = "offerDeadline")
	private Timestamp offerDeadline;

	@Column(name = "offerProvider")
	private String offerProvider;

	public Offer() {
	} // For JPA

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public long getOfferID() {
		return offerID;
	}

	public void setOfferID(long offerID) {
		this.offerID = offerID;
	}

	public int getOfferTicksAvl() {
		return offerTicksAvl;
	}

	public void setOfferTicksAvl(int offerTicksAvl) {
		this.offerTicksAvl = offerTicksAvl;
	}

	public boolean isOfferIsVisible() {
		return offerIsVisible;
	}

	public void setOfferIsVisible(boolean offerIsVisible) {
		this.offerIsVisible = offerIsVisible;
	}

	public double getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(double offerPrice) {
		this.offerPrice = offerPrice;
	}

	public double getOfferDiscount() {
		return offerDiscount;
	}

	public void setOfferDiscount(double offerDiscount) {
		this.offerDiscount = offerDiscount;
	}

	public int getOfferViewCount() {
		return offerViewCount;
	}

	public void setOfferViewCount(int offerViewCount) {
		this.offerViewCount = offerViewCount;
	}

	public String getOfferPicturePath() {
		return offerPicturePath;
	}

	public void setOfferPicturePath(String offerPicturePath) {
		this.offerPicturePath = offerPicturePath;
	}

	public Timestamp getOfferDeadline() {
		return offerDeadline;
	}

	public void setOfferDeadline(Timestamp offerDeadline) {
		this.offerDeadline = offerDeadline;
	}

	public String getOfferProvider() {
		return offerProvider;
	}

	public void setOfferProvider(String offerProvider) {
		this.offerProvider = offerProvider;
	}

	public Offer(Event event, String seatRow, String seatColumn, int offerTicksAvl, boolean offerIsVisible, double offerPrice,
			double offerDiscount, int offerViewCount, String offerPicturePath, Timestamp offerDeadline,
			String offerProvider) {
		this.event = event;
		this.seatRow = seatRow;
		this.seatColumn = seatColumn;
		this.offerTicksAvl = offerTicksAvl;
		this.offerIsVisible = offerIsVisible;
		this.offerPrice = offerPrice;
		this.offerDiscount = offerDiscount;
		this.offerViewCount = offerViewCount;
		this.offerPicturePath = offerPicturePath;
		this.offerDeadline = offerDeadline;
		this.offerProvider = offerProvider;
	}

	@Override
	public String toString() {
		return "Offer [event=" + event + ", offerID=" + offerID + ", seatColumn=" + seatColumn +", seatRow=" + seatRow + ", offerTicksAvl="
				+ offerTicksAvl + ", offerIsVisible=" + offerIsVisible + ", offerPrice=" + offerPrice
				+ ", offerDiscount=" + offerDiscount + ", offerViewCount=" + offerViewCount + ", offerPicturePath="
				+ offerPicturePath + ", offerDeadline=" + offerDeadline + ", offerProvider=" + offerProvider + "]";
	}

	public long getOfferPartnerID() {
		return offerPartnerID;
	}

	public void setOfferPartnerID(long offerPartnerID) {
		this.offerPartnerID = offerPartnerID;
	}

	public String getSeatColumn() {
		return seatColumn;
	}

	public void setSeatColumn(String seatColumn) {
		this.seatColumn = seatColumn;
	}

	public String getSeatRow() {
		return seatRow;
	}

	public void setSeatRow(String seatRow) {
		this.seatRow = seatRow;
	}

}
