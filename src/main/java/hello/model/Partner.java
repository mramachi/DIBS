package hello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQuery(name = "findAllPartners", query = "SELECT e from Partner e")
@Table(name = "public.Partner")
public class Partner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partnerID", nullable = false)
	private long partnerID;

	@Column(name = "partnerName")
	private String partnerName;

	@Column(name = "partnerEmail")
	private String partnerEmail;

	@Column(name = "partnerLink")
	private String partnerLink;

	@Column(name = "locationID")
	private long locationID;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "partnerContactFirstName")
	private String partnerContactFirstName;
	
	@Column(name = "partnerContactLastName")
	private String partnerContactLastName;
	
	@Column(name = "partnerImagePath")
	private String partnerImagePath;
	
	protected Partner() {
	}; // for JPA

	public Partner(String partnerName, String partnerEmail, String partnerLink) {
		this.partnerName = partnerName;
		this.partnerEmail = partnerEmail;
		this.partnerLink = partnerLink;
	}

	public long getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(long partnerID) {
		this.partnerID = partnerID;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerEmail() {
		return partnerEmail;
	}

	public void setPartnerEmail(String partnerEmail) {
		this.partnerEmail = partnerEmail;
	}

	public String getPartnerLink() {
		return partnerLink;
	}

	public void setPartnerLink(String partnerLink) {

		this.partnerLink = partnerLink;
	}
	
	public String getPartnerContactFirstName(){
		return this.partnerContactFirstName;
	}

	public long getLocationID() {
		return locationID;
	}

	public void setLocationID(long locationID) {
		this.locationID = locationID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPartnerContactLastName() {
		return partnerContactLastName;
	}

	public void setPartnerContactLastName(String partnerContactLastName) {
		this.partnerContactLastName = partnerContactLastName;
	}

	public void setPartnerContactFirstName(String partnerContactFirstName) {
		this.partnerContactFirstName = partnerContactFirstName;
	}

	public String getPartnerImagePath() {
		return partnerImagePath;
	}

	public void setPartnerImagePath(String partnerImagePath) {
		this.partnerImagePath = partnerImagePath;
	}

}
