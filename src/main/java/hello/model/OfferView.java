package hello.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQuery(name = "findAllOfferViews", query = "SELECT e from OfferView e")
@Table(name = "public.OfferView")
public class OfferView {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long offerViewID;

	@JoinColumn(name = "offerID")
	@JsonIgnore
	// @Column(name="offerID")
	private long offerID;

	@JoinColumn(name = "userID")
	@JsonIgnore
	// @Column(name="userID")
	private long userID;

	public OfferView() {
	} // For JPA

	public OfferView(long offerID, long userID) {
		this.offerID = offerID;
		this.userID = userID;
	}

	public long getOfferID() {
		return offerID;
	}

	public void setOfferID(long offerID) {
		this.offerID = offerID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

}
