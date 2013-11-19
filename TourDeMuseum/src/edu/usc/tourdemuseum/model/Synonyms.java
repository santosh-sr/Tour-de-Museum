package edu.usc.tourdemuseum.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Synonyms {
	private Map<String, List<String>> synonymsMap;
	
	public Synonyms(){
		this.synonymsMap = new HashMap<>();
	}
	
	public void addSynonymForTerm(String key, String synonym){
		List<String> synonymsList;
		if(synonymsMap.containsKey(key)){
			synonymsList = synonymsMap.get(key);
		}else{
			synonymsList = new ArrayList<>();
		}
		
		synonymsList.add(synonym);
		synonymsMap.put(key, synonymsList);
	}
	
	public List<String> getSynonymsList(String key){
		return synonymsMap.get(key);
	}
}
