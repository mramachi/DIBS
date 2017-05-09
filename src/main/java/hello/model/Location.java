package hello.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQuery(name = "findAllLocations", query = "SELECT e from Location e")
@Table(name = "public.Location")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "locationID")
	private long locationID;

	@Column(name = "locationZIP", length = 4)
	private char[] locationZIP;

	@Column(name = "locationStreet")
	private String locationStreet;

	@Column(name = "locationCity")
	private String locationCity;

	@Column(name = "locationHouseNumber")
	private int locationHouseNumber;

	public Location() {
	} // For JPA

	public Location(long locationID, char[] locationZIP, String locationStreet, String locationCity,
			int locationHouseNumber) {
		this.locationZIP = locationZIP;
		this.locationStreet = locationStreet;
		this.locationCity = locationCity;
		this.locationHouseNumber = locationHouseNumber;
	}

	public long getLocationID() {
		return locationID;
	}

	public void setLocationID(long locationID) {
		this.locationID = locationID;
	}

	public char[] getLocationZIP() {
		return locationZIP;
	}

	public void setLocationZIP(char[] locationZIP) {
		this.locationZIP = locationZIP;
	}

	public String getLocationStreet() {
		return locationStreet;
	}

	public void setLocationStreet(String locationStreet) {
		this.locationStreet = locationStreet;
	}

	public String getLocationCity() {
		return locationCity;
	}

	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}

	public int getLocationHouseNumber() {
		return locationHouseNumber;
	}

	public void setLocationHouseNumber(int locationHouseNumber) {
		this.locationHouseNumber = locationHouseNumber;
	}

	@Override
	public String toString() {
		return "Location [locationID=" + locationID + ", locationZIP=" + Arrays.toString(locationZIP)
				+ ", locationStreet=" + locationStreet + ", locationCity=" + locationCity + ", locationHouseNumber="
				+ locationHouseNumber + "]";
	}

}
