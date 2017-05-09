package hello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQuery(name="findAllUsers", query="SELECT e from User e")
@Table(name = "public.User")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userID")
	private long userID;
	
	@Column(name="facebookID")
	private long facebookID;
	
	@Column(name="userFirstName")
	private String userFirstName;
	
	@Column(name="userLastName")
	private String userLastName;
	
	@Column(name="userEmail")
	private String userEmail;
	
	@Column(name="userPhone")
	private String userPhone;
	
	@Column(name="userSettings")
	private String userSettings;
	
	@Column(name="userPhotoPath")
	private String userPhotoPath;
	
	@ManyToOne
	@JoinColumn(name="locationID")
	private Location locationID;
	
	//Laten staan. - Simon
	/*
	@Column(name="userTagSubscr")
	private String[] userTagSubscr;
	*/
	
	public User(){}
	
	public User( String userFirstName,long facebookID, String userLastName, String userEmail, String userPhone, String userSettings, String userPhotoPath, Location location /*, ArrayList<String> userTagSubscr */){
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userSettings = userSettings;
		this.userPhotoPath = userPhotoPath;
		this.locationID = location;
		this.facebookID=facebookID;
	//	this.userTagSubscr = userTagSubscr; // moet dit niet een array van Strings zijn?
	}
	
	
	@Override
	public String toString() {
		return String.format("User[id=%d, userFirstName='%s', userLastName='%s', userEmail='%s', userPhone='%s']", userID, userFirstName, userLastName, userEmail,
				userPhone);
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUserFirstName() {
		return userFirstName;
	}
	
	public String getUserLastName() {
		return userLastName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserSettings() {
		return userSettings;
	}

	public void setUserSettings(String userSettings) {
		this.userSettings = userSettings;
	}

	public String getUserPhotoPath() {
		return userPhotoPath;
	}

	public void setUserPhotoPath(String userPhotoPath) {
		this.userPhotoPath = userPhotoPath;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Location getLocationID() {
		return locationID;
	}

	public void setLocationID(Location locationID) {
		this.locationID = locationID;
	}
/*
	public ArrayList<String> getUserTagSubscr() {
		return userTagSubscr;
	}

	public void setUserTagSubscr(ArrayList<String> userTagSubscr) {
		this.userTagSubscr = userTagSubscr;
	}
*/
	
}
