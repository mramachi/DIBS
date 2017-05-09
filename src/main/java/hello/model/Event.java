package hello.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.sql.Date;
import java.sql.Time;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

@Entity
@NamedQuery(name="findAllEvent", query="SELECT e from Event e")
@Table(name = "public.Event")
public class Event {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //geen GenerationTYPE.AUTO, want dat geeft fouten
	@Column(name="EventID", nullable=false)
	private long eventID;
	
	@Column(name="partnerID")
	private long partnerID;
	
	@Column(name="eventName")
	private String eventName;
	
	@Column(name="eventDescription")
	private String eventDescription;
	
	@Column(name="eventLink")
	private String eventLink;
	
	@Column(name="eventIsVisible")
	private boolean eventIsVisible;
	
	@Column(name="eventPaymentText")
	private String eventPaymentText;
	
	@Column(name="eventDate")
	private Date eventDate;
	
	@Column(name="eventTimeStart")
	private Time eventTimeStart;
	
	@Column(name="eventTimeStop")
	private Time eventTimeStop;
	
	@ManyToOne
	@JoinColumn(name="locationID")
	private Location location;
	
	@Column(name="eventLikes")
	private int eventLikes;
	
	@Column(name="eventShares")
	private int eventShares;
	
	@Column(name="eventTags")
	@Type(type="hello.model.support.StringArrayType")
	private String[] eventTags;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private Set<Offer> offers;
	
	public Event(){} //For JPA

	//likes and shares init at 0
	public Event(long partnerID, String eventDescription, String eventLink,
			boolean eventIsVisible, String eventPaymentText, Date eventDate, Time eventTimeStart, Time eventTimeStop,
			Location location, String eventName) {
		this.partnerID = partnerID;
		this.eventDescription = eventDescription;
		this.eventLink = eventLink;
		this.eventIsVisible = eventIsVisible;
		this.eventPaymentText = eventPaymentText;
		this.eventTimeStart = eventTimeStart;
		this.eventTimeStop = eventTimeStop;
		this.location = location;
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventLikes = 0;
		this.eventShares = 0;
	}
	
	protected Set<Offer> getOffersInternal(){
		if (this.offers == null) this.offers = new HashSet<>();
		return this.offers;
	}
	
	protected void setOffersInternal(Set<Offer> offers){
		this.offers = offers;
	}
	
	public List<Offer> getOffers(){
		List<Offer> sortedOffers = new ArrayList<Offer>(getOffersInternal());
		PropertyComparator.sort(sortedOffers, new MutableSortDefinition("offerID", true, true));
		return Collections.unmodifiableList(sortedOffers);
	}
	
	public void addOffer(Offer offer){
		getOffersInternal().add(offer);
		offer.setEvent(this);
	}
	
	public long getEventID() {
		return eventID;
	}

	public void setEventID(long eventID) {
		this.eventID = eventID;
	}

	public long getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(long partnerID) {
		this.partnerID = partnerID;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public String getEventLink() {
		return eventLink;
	}

	public void setEventLink(String eventLink) {
		this.eventLink = eventLink;
	}

	public boolean isEventIsVisible() {
		return eventIsVisible;
	}

	public void setEventIsVisible(boolean eventIsVisible) {
		this.eventIsVisible = eventIsVisible;
	}

	public String getEventPaymentText() {
		return eventPaymentText;
	}

	public void setEventPaymentText(String eventPaymentText) {
		this.eventPaymentText = eventPaymentText;
	}

	public Time getEventTimeStart() {
		return eventTimeStart;
	}

	public void setEventTimeStart(Time eventTimeStart) {
		this.eventTimeStart = eventTimeStart;
	}

	public Time getEventTimeStop() {
		return eventTimeStop;
	}

	public void setEventTimeStop(Time eventTimeStop) {
		this.eventTimeStop = eventTimeStop;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String[] getTags() {
		return eventTags;
	}

	public void setTags(String[] tags) {
		this.eventTags = tags;
	}

	public int getEventLikes() {
		return eventLikes;
	}

	public void setEventLikes(int eventLikes) {
		this.eventLikes = eventLikes;
	}

	public int getEventShares() {
		return eventShares;
	}

	public void setEventShares(int eventShares) {
		this.eventShares = eventShares;
	}

	@Override
	public String toString() {
		return "Event [eventID=" + eventID + ", partnerID=" + partnerID + ", eventName=" + eventName
				+ ", eventDescription=" + eventDescription + ", eventLink=" + eventLink + ", eventIsVisible="
				+ eventIsVisible + ", eventPaymentText=" + eventPaymentText + ", eventDate=" + eventDate
				+ ", eventTimeStart=" + eventTimeStart + ", eventTimeStop=" + eventTimeStop + ", location=" + location
				+ ", eventLikes=" + eventLikes + ", eventShares=" + eventShares + ", eventTags="
				+ Arrays.toString(eventTags) + ", offers=" + offers + "]";
	}
	
	
}
