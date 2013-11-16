package edu.usc.tourdemuseum.json.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.POS;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import edu.usc.tourdemuseum.wordnet.Synonyms;

public class AddSynonyms {
	
	private static Map<String, Float> tfidfMap;

	public static Map<String, Float> loadTFIDF() throws FileNotFoundException, IOException{
		Map<String, Float> tfidfMap = new HashMap<>();
		String line;
		try(BufferedReader reader = new BufferedReader(new FileReader(new File("term-tfidf.txt")))){
			while((line = reader.readLine()) != null){
				int lastIndex = line.lastIndexOf(" ");

				tfidfMap.put(line.substring(0, lastIndex), Float.parseFloat(line.substring(lastIndex + 1, line.length())));
			}
		}

		return tfidfMap;
	}

	public static void addSynonyms(String propsFile, Path inputPath) throws IOException, JWNLException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(Files.newInputStream(inputPath));

		//"C:\\Semester\\Fall 2013\\CS 548\\Project\\tools\\extjwnl-1.6.10\\extjwnl-1.6.10\\src\\extjwnl\\src\\main\\resources\\net\\sf\\extjwnl\\file_properties.xml"
		Synonyms.initDictionary(propsFile);

		ArrayNode jsonArray = (ArrayNode) rootNode.get("paintings");
		Iterator<JsonNode> jsonNodeIter = jsonArray.iterator();
		JsonNode paintingNode;
		JsonNode titleNode, descNode, keywordsNode, entityNode, personNode, orgNode;
		ArrayNode personArrayNode, orgArrayNode;
		String title, description, keywords;

		String fileName = inputPath.getFileName().toFile().getName();

		List<String> personList, orgList = null;
		StringBuffer buffer = new StringBuffer();
		personList = new ArrayList<>();
		ArrayNode synonymsArrayNode;
		ArrayNode termSynonymsNode = null;
		while(jsonNodeIter.hasNext()){
			paintingNode = jsonNodeIter.next();
			titleNode = paintingNode.get("title");
			descNode = paintingNode.get("description");
			keywordsNode = paintingNode.get("keywords");
			entityNode = paintingNode.get("entities");
			title = ""; description = ""; keywords = "";

			synonymsArrayNode = objectMapper.createArrayNode();
			//synonymsArrayNode.with("synonyms");

			if(entityNode != null){
				personList = new ArrayList<>();
				orgList = new ArrayList<>();

				personNode = entityNode.get("person");
				if(personNode != null){
					personArrayNode = (ArrayNode) personNode;
					Iterator<JsonNode> personNodeIter = personArrayNode.iterator();

					while(personNodeIter.hasNext()){
						String person = personNodeIter.next().getTextValue(); 
						personList.add(person);
					}
				}

				orgNode = entityNode.get("organization");
				if(orgNode != null){
					orgArrayNode = (ArrayNode) orgNode;
					Iterator<JsonNode> orgNodeIter = orgArrayNode.iterator();

					while(orgNodeIter.hasNext()){
						String org = orgNodeIter.next().getTextValue(); 
						orgList.add(org);
					}
				}
			}

			if(titleNode != null){
				title = titleNode.getTextValue();

				if(personList != null){
					for(String person : personList){
						if(title.contains(person)){
							int substr = title.indexOf(person);
							buffer.append(title.substring(0, substr));
							buffer.append(title.substring(substr + 1, title.length()));

							title = buffer.toString();
							buffer.setLength(0);
						}
					}
				}

				if(orgList != null){
					for(String org : orgList){
						if(title.contains(org)){
							int substr = title.indexOf(org);
							buffer.append(title.substring(0, substr));
							buffer.append(title.substring(substr + 1, title.length()));

							title = buffer.toString();
							buffer.setLength(0);
						}
					}
				}

				String[] titleArray = title.split(" ");
				for(String val : titleArray){
					val = val.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
					if(tfidfMap.containsKey(val + "@" + fileName)){
						Set<String> synonyms = Synonyms.getSynonyms(val, POS.VERB, POS.NOUN, POS.ADJECTIVE);
						if(!synonyms.isEmpty()){
							termSynonymsNode = objectMapper.createArrayNode();
							for(String syn : synonyms){
								termSynonymsNode.add(syn);
							}

							ObjectNode titleSynNode = objectMapper.createObjectNode();
							titleSynNode.put(val, termSynonymsNode);

							synonymsArrayNode.add(titleSynNode);
						}
					}
				}
			}

			if(descNode != null){
				description = descNode.getTextValue();

				if(personList != null){
					for(String person : personList){
						if(description.contains(person)){
							int substr = description.indexOf(person);
							buffer.append(description.substring(0, substr));
							buffer.append(description.substring(substr + 1, title.length()));

							description = buffer.toString();
							buffer.setLength(0);
						}
					}
				}

				if(orgList != null){
					for(String org : orgList){
						if(description.contains(org)){
							int substr = description.indexOf(org);
							buffer.append(description.substring(0, substr));
							buffer.append(description.substring(substr + 1, title.length()));

							description = buffer.toString();
							buffer.setLength(0);
						}
					}
				}

				String[] descArray = description.split(" ");
				for(String val : descArray){
					val = val.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
					if(tfidfMap.containsKey(val + "@" + fileName)){
						Set<String> synonyms = Synonyms.getSynonyms(val, POS.VERB, POS.NOUN, POS.ADJECTIVE);
						if(!synonyms.isEmpty()){
							termSynonymsNode = objectMapper.createArrayNode();
							for(String syn : synonyms){
								termSynonymsNode.add(syn);
							}

							ObjectNode descSynNode = objectMapper.createObjectNode();
							descSynNode.put(val, termSynonymsNode);

							synonymsArrayNode.add(descSynNode);
						}
					}
				}
			}

			if(keywordsNode != null){
				ArrayNode keyArraysNode = (ArrayNode) keywordsNode;
				Iterator<JsonNode> keywordsJsonIter = keyArraysNode.iterator();

				while(keywordsJsonIter.hasNext()){
					keywords = keywordsJsonIter.next().getTextValue();

					if(personList != null && personList.contains(keywords)){
						continue;
					}

					if(orgList != null && orgList.contains(keywords)){
						continue;
					}

					keywords = keywords.toLowerCase();
					if(tfidfMap.containsKey(keywords + "@" + fileName)){
						Set<String> synonyms = Synonyms.getSynonyms(keywords, POS.VERB, POS.NOUN, POS.ADJECTIVE);
						if(!synonyms.isEmpty()){
							termSynonymsNode = objectMapper.createArrayNode();
							for(String syn : synonyms){
								termSynonymsNode.add(syn);
							}

							ObjectNode keywordsSynNode = objectMapper.createObjectNode();
							keywordsSynNode.put(keywords, termSynonymsNode);

							synonymsArrayNode.add(keywordsSynNode);
						}
					}
				}
			}

			((ObjectNode) paintingNode).put("synonyms", synonymsArrayNode);
		}

		ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
		writer.writeValue(Files.newOutputStream(Paths.get(fileName + "-with-synonyms.json")), rootNode);
		//writer.writeValue(out, value)
	}

	public static void main(String[] args) throws IOException, JWNLException {
		if(args.length < 2){
			System.err.println("Specify exjwnl properties file and museum dataset path..");
			System.exit(0);
		}

		tfidfMap = loadTFIDF();
		for(int index = 1; index < args.length; index++){
			Path inputPath = Paths.get(args[index]);
			AddSynonyms.addSynonyms(args[0], inputPath);
		}
	}
}
