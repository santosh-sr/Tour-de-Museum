package edu.usc.tourdemuseum.similarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.usc.tourdemuseum.model.ArtWork;

public class EntitySimilarity {

	private Map<String, List<EntityWithPainting>> entitiesMap;
	
	public EntitySimilarity(){
		entitiesMap = new HashMap<String, List<EntityWithPainting>>();
	}
	
	public void addEntityWithPainting(String entity, EntityWithPainting paintingWork){
		List<EntityWithPainting> artworksClusterList;
		if(entitiesMap.containsKey(entity)){
			artworksClusterList = entitiesMap.get(entity);
		}else{
			artworksClusterList = new ArrayList<>();
		}
		
		artworksClusterList.add(paintingWork);
		entitiesMap.put(entity, artworksClusterList);
	}
	
	public List<EntityWithPainting> getArtworksList(String entity){
		return entitiesMap.get(entity);
	}
	
	
	public static class EntityWithPainting{
		private String entityName;
		
		private List<ArtWork> artworks;
		
		
		public EntityWithPainting(String entityName){
			this.entityName = entityName;
			artworks = new ArrayList<>();
		}
		
		public String getEntityName(){
			return entityName;
		}
		
		public void addArtwork(ArtWork art){
			artworks.add(art);
		}
		
		public List<ArtWork> getArtworkList(){
			return artworks;
		}
	}
}
