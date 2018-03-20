package model;

public class Buchung {

	int bID;
	String d;
	int pID;
	int rbID;

	
	
	public int getbID() {
		return bID;
	}



	public void setbID(int bID) {
		this.bID = bID;
	}



	public String getD() {
		return d;
	}



	public void setD(String d) {
		this.d = d;
	}



	public int getpID() {
		return pID;
	}



	public void setpID(int pID) {
		this.pID = pID;
	}



	public int getRbID() {
		return rbID;
	}



	public void setRbID(int rbID) {
		this.rbID = rbID;
	}
	
	public Buchung(int bID, String d, int pID, int rbID) {
		super();
		this.bID = bID;
		this.d = d;
		this.pID = pID;
		this.rbID = rbID;
	}

	public Buchung() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Buchung [bID=" + bID + ", d=" + d + ", pID=" + pID + ", rbID=" + rbID + "]";
	}
}
