package hello.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Stoel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //geen GenerationTYPE.AUTO, want dat geeft fouten
	@Column(name="SeatID", nullable=false)
	private long seatid;
	
	@Column(name="seatrow")
	private int seatRow;
	
	@Column(name="seatColumn")
	private int seatColumn;
	
	@ManyToOne
	@JoinColumn(name = "offerID")
	//@JsonIgnore
	private Offer offer;
	
	public Stoel(){
		
	}
	
	public Stoel(long seatid,int seatrow,int seatColumn,Offer offer){
		this.seatid=seatid;
		this.seatRow=seatrow;
		this.seatColumn=seatColumn;
		this.offer=offer;
	}

	public long getSeatid() {
		return seatid;
	}

	public void setSeatid(long seatid) {
		this.seatid = seatid;
	}

	public int getSeatRow() {
		return seatRow;
	}

	public void setSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}

	public int getSeatColumn() {
		return seatColumn;
	}

	public void setSeatColumn(int seatColumn) {
		this.seatColumn = seatColumn;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	@Override
	public String toString() {
		return "Stoel [seatid=" + seatid + ", seatRow=" + seatRow + ", seatColumn=" + seatColumn + ", offer=" + offer
				+ ", getSeatid()=" + getSeatid() + ", getSeatRow()=" + getSeatRow() + ", getSeatColumn()="
				+ getSeatColumn() + ", getOffer()=" + getOffer() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
