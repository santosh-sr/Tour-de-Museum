package edu.usc.tourdemuseum.model;

import java.util.List;

public class Entities {
	private List<String> personList;
	
	private List<String> orgList;
	
	private List<String> socialTagsList;
	
	public Entities(List<String> personList, List<String> orgList, List<String> socialTagsList){
		this.personList = personList;
		this.orgList = orgList;
		this.socialTagsList = socialTagsList;
	}

	public List<String> getPersonList() {
		return personList;
	}

	public List<String> getOrgList() {
		return orgList;
	}

	public List<String> getSocialTagsList() {
		return socialTagsList;
	}
}
