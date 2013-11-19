package edu.usc.tourdemuseum.model;

public class Painter {
	private String painterName;
	
	private String nationality;
	
	private Year birthYear; 
	
	private Year deathYear;
	
	private String nativePlace;
	
	public Painter(String painterName, String nationality, String nativePlace, Year birthYear, Year deathYear){
		this.painterName = painterName;
		this.nationality = nationality;
		this.nativePlace = nativePlace;
		this.birthYear = birthYear;
		this.deathYear = deathYear;
	}

	public Painter() {
		// TODO Auto-generated constructor stub
	}

	public String getPainterName() {
		return painterName;
	}

	public String getNationality() {
		return nationality;
	}

	public Year getBirthYear() {
		return birthYear;
	}

	public Year getDeathYear() {
		return deathYear;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setPainterName(String painterName) {
		this.painterName = painterName;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setBirthYear(Year birthYear) {
		this.birthYear = birthYear;
	}

	public void setDeathYear(Year deathYear) {
		this.deathYear = deathYear;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
}


