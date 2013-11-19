package edu.usc.tourdemuseum.model;

import java.net.URL;
import java.util.List;

public class ArtWork {

	private Museum museum;
	
	private String artTitle;
	
	private String description;
	
	private String department;
	
	private Year paintedYear;
	
	private Painter painter;
	
	private String dimensions;
	
	private URL imageUrl;
	
	private String credit;
	
	private String provenance;
	
	private String classification;
	
	private String medium;
	
	private String accessionID;
	
	private List<String> keywords;
	
	private Synonyms synonyms;
	
	private Entities entities;
	
	public ArtWork(Museum museum, String artTitle, String description, String department, Year paintedYear, Painter painter,
			String dimensions, URL imageUrl, String credit, String provenance, String classification,
			String accessionID, List<String> keywords, Entities entities, Synonyms synonyms){
		this.museum = museum;
		this.artTitle = artTitle;
		this.description = description;
		this.department = department;
		this.paintedYear = paintedYear;
		this.painter = painter;
		this.dimensions = dimensions;
		this.imageUrl = imageUrl;
		this.credit = credit;
		this.provenance = provenance;
		this.classification = classification;
		this.accessionID = accessionID;
		this.keywords = keywords;
		this.synonyms = synonyms;
		this.entities = entities;
	}

	public ArtWork() {
		
	}

	public String getArtTitle() {
		return artTitle;
	}

	public String getDepartment() {
		return department;
	}

	public Year getPaintedYear() {
		return paintedYear;
	}

	public Painter getPainter() {
		return painter;
	}

	public String getDimensions() {
		return dimensions;
	}

	public URL getImageUrl() {
		return imageUrl;
	}

	public String getCredit() {
		return credit;
	}

	public String getProvenance() {
		return provenance;
	}

	public String getClassification() {
		return classification;
	}

	public String getAccessionID() {
		return accessionID;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public Synonyms getSynonyms() {
		return synonyms;
	}

	public String getDescription() {
		return description;
	}

	public String getMedium() {
		return medium;
	}

	public Entities getEntities() {
		return entities;
	}
	
	//Museum Type
	public enum Museum{
		LACMA, DETROIT, ARTIC;
	}

	public Museum getMuseum() {
		return museum;
	}

	public void setMuseum(Museum museum) {
		this.museum = museum;
	}

	public void setArtTitle(String artTitle) {
		this.artTitle = artTitle;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setPaintedYear(Year paintedYear) {
		this.paintedYear = paintedYear;
	}

	public void setPainter(Painter painter) {
		this.painter = painter;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	public void setImageUrl(URL imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public void setAccessionID(String accessionID) {
		this.accessionID = accessionID;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public void setSynonyms(Synonyms synonyms) {
		this.synonyms = synonyms;
	}

	public void setEntities(Entities entities) {
		this.entities = entities;
	}
}
