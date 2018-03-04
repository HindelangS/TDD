package model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class TableBallon {

	int flugID;
	String anzPers;
	String ort;
	String pilotID;
	String zeitpunkt;
	int preis;
	int platze;
	
	public TableBallon() {
		super();
		
		this.flugID = -1;
		this.anzPers = " - ";
		this.ort = " - ";
		this.pilotID = " - ";
		this.zeitpunkt = " - ";
		this.preis = -1;
		this.platze = -1;
	}
	
	public TableBallon(int flugID, String anzPers, String ort, String pilotID, int preis, int platz, String zeitpunkt ) {
		super();
		
		this.flugID = flugID;
		this.anzPers = anzPers;
		this.ort = ort;
		this.pilotID = pilotID;
		this.preis = preis;
		this.platze = platz;
		this.zeitpunkt = zeitpunkt;

	}
	
	public String getFlug(int fid) {
		
		switch(fid) {
		case 1: 
			return (String) "Über den Wolken 1"; 
		case 2:
			return (String) "Wolke 7"; 
		case 3:
			return (String) "Fly High"; 
		case 4:
			return (String) "Morgenrot"; 
		case 5:
			return (String) "Abenddämmerung"; 
		default:
			return (String) "Rundflug"; 	
		}
	}
	
	public void setFlug(int flugID) {
		this.flugID = flugID;
	}
	
	
	public String getAnzPers() {
		return anzPers;
	}
	
	public void setAnzPers(String anzPers) {
		this.anzPers = anzPers;
	}
	
	public String getOrt() {
		return ort;
	}
	
	public void setOrt(String ort) {
		this.ort = ort;
	}
	
	public String getPilot() {
		return pilotID;
	}
	
	public void setPilot(String pID) {
		this.pilotID = pID;
	}
	
	public int getPreis() {
		return preis;
	}
	
	public void setPreis(int preis) {
		this.preis = preis;
	}
	
	public int getPlatze() {
		return platze--;
	}
	
	public void setPlatze(int preis) {
		this.preis -= preis;
	}
	
	public String getZeitpunkt() {
		GregorianCalendar now = new GregorianCalendar(); 

		SimpleDateFormat d = new SimpleDateFormat("dd.MM.yyyy");
		String pD = d.format(now.getTime());
//		System.out.println(pD);

		return pD;
	}
	
	public String setZeitpunkt() {
		GregorianCalendar now = new GregorianCalendar(); 

		SimpleDateFormat d = new SimpleDateFormat("dd.MM.yyyy");
		String pD = d.format(now.getTime());
//		System.out.println(pD);

		return pD;
	}

	@Override
	public String toString() {
		return "TableBallon [flugID=" + flugID + ", anzPers=" + anzPers + ", ort=" + ort + ", pilotID=" + pilotID
				+", preis=" + preis + ", platze=" + platze + ", zeitpunkt=" + zeitpunkt + "]";
	}
}