package edu.usc.tourdemuseum.json.parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import uk.ac.shef.wit.simmetrics.similaritymetrics.JaroWinkler;

import edu.usc.tourdemuseum.model.ArtWork;
import edu.usc.tourdemuseum.model.ArtWork.Museum;
import edu.usc.tourdemuseum.model.Entities;
import edu.usc.tourdemuseum.model.Painter;
import edu.usc.tourdemuseum.model.Synonyms;
import edu.usc.tourdemuseum.model.Year;
import edu.usc.tourdemuseum.similarity.EntitySimilarity;
import edu.usc.tourdemuseum.similarity.EntitySimilarity.EntityWithPainting;

public class JSONConvertor {
	private static List<ArtWork> artWorkList = new ArrayList<>();

	public static List<ArtWork> getArtWorkList(){
		return artWorkList;
	}

	public static void parseJSONToObject(Museum museum, Path inputJsonPath) throws FileNotFoundException, IOException{
		JsonNode jsonNode = parseJson(inputJsonPath);

		ArrayNode jsonArray = (ArrayNode) jsonNode.get("paintings");
		Iterator<JsonNode> jsonNodeIter = jsonArray.iterator();

		JsonNode titleNode, descNode, keywordsNode, entityNode, //personNode, orgNode,
		imageNode, classificationNode, nationalityNode, nameNode, paintedOnNode,
		synonymsNode, mediumNode, departmentNode, provenenceNode, dimensionsNode,
		creditNode, accessionIdNode, birthNode, deathNode;
		ArrayNode personArrayNode, orgArrayNode, stagsArrayNode; 

		JsonNode paintingNode;
		ArtWork artWork;
		while(jsonNodeIter.hasNext()){
			artWork = new ArtWork();
			paintingNode = jsonNodeIter.next();

			//museum
			artWork.setMuseum(museum);

			//image url
			imageNode = paintingNode.get("imageURL");
			if(imageNode != null){
				artWork.setImageUrl(new URL(imageNode.getTextValue()));
			}

			//title
			titleNode = paintingNode.get("title");
			if(titleNode != null){
				artWork.setArtTitle(titleNode.getTextValue());
			}

			//description
			descNode = paintingNode.get("description");
			if(descNode != null){
				artWork.setDescription(descNode.getTextValue());
			}

			//Artist Details
			{
				Painter painter = new Painter();

				//Nationality
				nationalityNode = paintingNode.get("nationality");
				if(nationalityNode != null){
					painter.setNationality(nationalityNode.getTextValue());
				}

				//Name
				nameNode = paintingNode.get("name");
				if(nameNode != null){
					painter.setPainterName(nameNode.getTextValue());
				}

				//Birth
				birthNode = paintingNode.get("birth");
				if(birthNode != null){
					String birth = birthNode.getTextValue();
					Year birthYear = null;
					if(birth.contains("circa")){
						birth = birth.replaceAll("[^0-9]", "");
						birthYear = new Year(birth, true);
					}else{
						birthYear = new Year(birth);
					}

					painter.setBirthYear(birthYear);
				}

				//Death
				deathNode = paintingNode.get("death");
				if(deathNode != null){
					String death = deathNode.getTextValue();
					Year deathYear = null;
					if(death.contains("circa")){
						death = death.replaceAll("[^0-9]", "");
						deathYear = new Year(death, true);
					}else{
						if(death.equals("unknown")){
							death = String.valueOf(Integer.MIN_VALUE);
						}
						deathYear = new Year(death);
					}

					painter.setDeathYear(deathYear);
				}

				artWork.setPainter(painter);
			}

			//painted date
			paintedOnNode = paintingNode.get("date");
			String paintedYear = null;
			if(paintedOnNode != null){
				paintedYear = paintedOnNode.getTextValue();
				artWork.setPaintedYear(new Year(paintedYear));
			}

			//classification
			classificationNode = paintingNode.get("classification");
			if(classificationNode != null){
				artWork.setClassification(classificationNode.getTextValue());
			}

			//department
			departmentNode = paintingNode.get("department");
			if(departmentNode != null){
				artWork.setDepartment(departmentNode.getTextValue());
			}

			//medium
			mediumNode = paintingNode.get("medium");
			if(mediumNode != null){
				artWork.setMedium(mediumNode.getTextValue());
			}

			//provenance
			provenenceNode = paintingNode.get("provenance");
			if(provenenceNode != null){
				artWork.setProvenance(provenenceNode.getTextValue());
			}

			//dimensions
			dimensionsNode = paintingNode.get("dimensions");
			if(dimensionsNode != null){
				artWork.setDimensions(dimensionsNode.getTextValue());
			}

			//credit
			creditNode = paintingNode.get("credit");
			if(creditNode != null){
				artWork.setCredit(creditNode.getTextValue());
			}

			//accession id
			accessionIdNode = paintingNode.get("accession");
			if(accessionIdNode != null){
				artWork.setAccessionID(accessionIdNode.getTextValue());
			}

			//synonyms
			synonymsNode = paintingNode.get("synonyms");
			if(synonymsNode != null){
				ArrayNode synArrayNode = (ArrayNode) synonymsNode;
				Iterator<JsonNode> synIter = synArrayNode.iterator();

				Synonyms synonyms = new Synonyms();
				while(synIter.hasNext()){
					JsonNode synJsonNode = synIter.next();

					Iterator<String> fieldsIter = synJsonNode.getFieldNames();
					while(fieldsIter.hasNext()){
						String term = fieldsIter.next();
						Iterator<JsonNode> valsIter = ((ArrayNode) synJsonNode.get(term)).iterator();

						while(valsIter.hasNext()){
							synonyms.addSynonymForTerm(term, valsIter.next().getTextValue());
						}
					}
				}

				artWork.setSynonyms(synonyms);
			}

			//keywords
			keywordsNode = paintingNode.get("keywords");
			if(keywordsNode != null){
				List<String> keywords = new ArrayList<>();
				ArrayNode arrayNode = (ArrayNode)keywordsNode;
				Iterator<JsonNode> keywordsIter = arrayNode.iterator();

				while(keywordsIter.hasNext()){
					JsonNode keywordNode = keywordsIter.next();
					keywords.add(keywordNode.getTextValue());
				}

				artWork.setKeywords(keywords);
			}

			//entities
			entityNode = paintingNode.get("entities");
			if(entityNode != null){
				List<String> personList = new ArrayList<>();
				List<String> orgList = new ArrayList<>();
				List<String> socialTagsList = new ArrayList<>();
				//ArrayNode arrayNode = (ArrayNode)entityNode;
				//Iterator<JsonNode> entitiesIter = arrayNode.iterator();

				//while(entitiesIter.hasNext()){
				//	JsonNode entityJsonNode = entitiesIter.next();
				JsonNode personNode = entityNode.get("Person");
				if(personNode != null){
					personArrayNode = (ArrayNode) personNode;
					Iterator<JsonNode> personNodeIter = personArrayNode.iterator();

					while(personNodeIter.hasNext()){
						String person = personNodeIter.next().getTextValue(); 
						personList.add(person);
					}
				}

				JsonNode orgNode = entityNode.get("Organization");
				if(orgNode != null){
					orgArrayNode = (ArrayNode) orgNode;
					Iterator<JsonNode> orgNodeIter = orgArrayNode.iterator();

					while(orgNodeIter.hasNext()){
						String org = orgNodeIter.next().getTextValue(); 
						orgList.add(org);
					}
				}

				JsonNode stagsNode = entityNode.get("socialTag");
				if(stagsNode != null){
					stagsArrayNode = (ArrayNode) stagsNode;
					Iterator<JsonNode> stagsIter = stagsArrayNode.iterator();

					while(stagsIter.hasNext()){
						String socialTags = stagsIter.next().getTextValue(); 
						socialTagsList.add(socialTags);
					}
				}

				//}

				artWork.setEntities(new Entities(personList, orgList, socialTagsList));
			}

			artWorkList.add(artWork);
		}
	}

	private static JsonNode parseJson(Path filePath) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readTree(Files.newInputStream(filePath));
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		if(args.length < 2){
			System.out.println("JSONConvertor <museum-type> <json path>");
			System.exit(0);
		}

		Museum museum;

		for(int i=0; i<args.length/2; i++){
			String museumType = args[2 * i];
			museum = getMuseum(museumType);

			JSONConvertor.parseJSONToObject(museum, Paths.get(args[(2 * i) + 1]));
		}

		//Entities Similarity
		EntitySimilarity similarity = new EntitySimilarity();
		List<EntityWithPainting> entityPaintingList;
		EntityWithPainting entityPainting;
		boolean addedArtwork;
		JaroWinkler jarowinkler = new JaroWinkler();
		for(int i = 0; i < artWorkList.size(); i++){
			//Art work
			ArtWork artWork = artWorkList.get(i);

			//person
			entityPaintingList = similarity.getArtworksList("person");
			String painterName = artWork.getPainter().getPainterName();
			if(entityPaintingList == null || entityPaintingList.size() <= 0){
				entityPainting = new EntityWithPainting(painterName);
				entityPainting.addArtwork(artWork);
				similarity.addEntityWithPainting("person", entityPainting);
			}else{
				addedArtwork = false;
				for(EntityWithPainting entityPaintingObj : entityPaintingList){
					String entityName = entityPaintingObj.getEntityName();
					if(entityName != null && painterName != null && jarowinkler.getSimilarity(entityName, painterName) > 0.90){
						entityPaintingObj.addArtwork(artWork);
						addedArtwork = true;
						break;
					}
				}

				if(!addedArtwork){
					entityPainting = new EntityWithPainting(painterName);
					entityPainting.addArtwork(artWork);
					entityPaintingList.add(entityPainting);
				}
			}

			//painted year
			entityPaintingList = similarity.getArtworksList("painted_year");
			String paintedOn = artWork.getPaintedYear().getYear();
			//System.out.println(paintedOn);
			if(entityPaintingList == null || entityPaintingList.size() <= 0){
				entityPainting = new EntityWithPainting(paintedOn);
				entityPainting.addArtwork(artWork);
				similarity.addEntityWithPainting("painted_year", entityPainting);
			}else{
				addedArtwork = false;
				for(EntityWithPainting entityPaintingObj : entityPaintingList){
					String entityName = entityPaintingObj.getEntityName();
					if(entityName != null && painterName != null && (paintedOn.equals(entityName) || 
							paintedOn.contains(entityName) || entityName.contains(paintedOn))){
						entityPaintingObj.addArtwork(artWork);
						addedArtwork = true;
						break;
					}
				}

				if(!addedArtwork){
					entityPainting = new EntityWithPainting(paintedOn);
					entityPainting.addArtwork(artWork);
					entityPaintingList.add(entityPainting);
				}
			}
		}

		//write person cluster
		entityPaintingList = similarity.getArtworksList("person");
		Path personClusterPath = Paths.get("person-cluster.csv");
		if(!Files.exists(personClusterPath)){
			Files.createFile(personClusterPath);
		}

		try(PrintWriter writer = new PrintWriter(personClusterPath.toFile())){
			writer.write("key_painter| matching_painter| image_url| museum\n");

			StringBuffer buffer = new StringBuffer();
			for(EntityWithPainting entityPaintingObj : entityPaintingList){
				List<ArtWork> matchingArtworkList = entityPaintingObj.getArtworkList();
				for(ArtWork artwork : matchingArtworkList){
					buffer.append(entityPaintingObj.getEntityName()).append("|");
					buffer.append(artwork.getPainter().getPainterName()).append("|")
					.append(artwork.getImageUrl()).append("|").append(artwork.getMuseum().name().toLowerCase()).append("\n");
					
					writer.write(buffer.toString());
					buffer.setLength(0);
				}
			}
		}

		//write painter year cluster
		entityPaintingList = similarity.getArtworksList("painted_year");
		Path paintedYearClusterPath = Paths.get("painted-year-cluster.csv");
		if(!Files.exists(paintedYearClusterPath)){
			Files.createFile(paintedYearClusterPath);
		}

		try(PrintWriter writer = new PrintWriter(paintedYearClusterPath.toFile())){
			writer.write("keyword_painted_year | matching_year| image_url| museum\n");

			StringBuffer buffer = new StringBuffer();
			for(EntityWithPainting entityPaintingObj : entityPaintingList){
				List<ArtWork> matchingArtworkList = entityPaintingObj.getArtworkList();
				for(ArtWork artwork : matchingArtworkList){
					buffer.append(entityPaintingObj.getEntityName()).append("|");
					buffer.append(artwork.getPaintedYear().getYear()).append("|")
					.append(artwork.getImageUrl()).append("|").append(artwork.getMuseum().name().toLowerCase()).append("\n");
					
					writer.write(buffer.toString());
					buffer.setLength(0);
				}
			}
		}
	}

	private static Museum getMuseum(String museumType) {
		Museum museum = null;
		if(museumType.equalsIgnoreCase(Museum.LACMA.name())){
			museum = Museum.LACMA;
		}else if(museumType.equalsIgnoreCase(Museum.DETROIT.name())){
			museum = Museum.DETROIT;
		}else if(museumType.equalsIgnoreCase(Museum.ARTIC.name())){
			museum = Museum.ARTIC;
		}
		return museum;
	}
}
