package model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class TableBuchung {

	int bID;
	int pID;
	String pVN;
	String pNN;
	String zeitpunkt;

	
	
	public TableBuchung() {
		super();
		
		this.bID = -1;
		this.pID = -1;
		this.pVN = " - ";
		this.pNN = " - ";
		this.zeitpunkt = " - ";

	}

	public TableBuchung(int bID, int pID, String pVN, String pNN, String zeitpunkt) {
		super();
		
		this.bID = bID;
		this.pID = pID;
		this.pVN = pVN;
		this.pNN = pNN;
		this.zeitpunkt = zeitpunkt;

	}
	
	public int getbID() {
		return bID;
	}

	public void setbID(int bID) {
		this.bID = bID;
	}

	public int getpID() {
		return pID;
	}

	public void setpID(int pID) {
		this.pID = pID;
	}

	public String getpVN() {
		return pVN;
	}

	public void setpVN(String pVN) {
		this.pVN = pVN;
	}

	public String getpNN() {
		return pNN;
	}

	public void setpNN(String pNN) {
		this.pNN = pNN;
	}

	public String getZeitpunkt() {
		return zeitpunkt;
	}

	public String setZeitpunkt(String zeitpunkt) {
		GregorianCalendar now = new GregorianCalendar(); 

		SimpleDateFormat d = new SimpleDateFormat("dd.MM.yyyy");
		String pD = d.format(now.getTime());

		return pD;
	}

	@Override
	public String toString() {
		return "TableBuchung [bID=" + bID + ", pID=" + pID + ", pVN=" + pVN + ", pNN=" + pNN + ", zeitpunkt="
				+ zeitpunkt + "]";
	}
	
	
	
	
}