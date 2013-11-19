package edu.usc.tourdemuseum.model;

public class Year {
	private String year;
	
	private boolean isCirca;
	
	public Year(String year, boolean isCirca){
		this.year = year;
		this.isCirca = isCirca;
	}
	
	public Year(String year){
		this(year, false);
	}

	public String getYear() {
		return year;
	}

	public boolean isCirca() {
		return isCirca;
	}
	
	
}