package edu.usc.tourdemuseum.json.parser;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;



public class JSONParser {
	public JsonNode parseJson(Path filePath) throws IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readTree(Files.newInputStream(filePath));
	}

	public static void main(String[] args) throws IOException {
		for(String absPath : args){
			//"C:\\Semester\\Fall 2013\\CS 548\\Project\\tour-de-museum\\dataset\\detroit\\european\\data-paintings.txt"
			Path filePath = Paths.get(absPath);
			JsonNode jsonNode = new JSONParser().parseJson(filePath);

			ArrayNode jsonArray = (ArrayNode) jsonNode.get("paintings");
			Iterator<JsonNode> jsonNodeIter = jsonArray.iterator();
			JsonNode paintingNode;
			JsonNode titleNode, descNode, keywordsNode, entityNode, personNode, orgNode;
			ArrayNode personArrayNode, orgArrayNode;
			String title, description, keywords;

			Path fileName = filePath.getFileName();
			if(!Files.exists(fileName)){
				Files.createFile(fileName);
			}

			List<String> personList, orgList = null;
			StringBuffer buffer = new StringBuffer();
			try(PrintWriter prinWriter = new PrintWriter(Files.newOutputStream(fileName))){
				personList = new ArrayList<>();
				while(jsonNodeIter.hasNext()){
					paintingNode = jsonNodeIter.next();
					titleNode = paintingNode.get("title");
					descNode = paintingNode.get("description");
					keywordsNode = paintingNode.get("keywords");
					entityNode = paintingNode.get("entities");
					title = ""; description = ""; keywords = "";

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
							prinWriter.write(val.replaceAll("[^a-zA-Z0-9]", ""));
							prinWriter.write("\n");
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
							prinWriter.write(val.replaceAll("[^a-zA-Z0-9]", ""));
							prinWriter.write("\n");
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

							prinWriter.write(keywords.replaceAll("[^a-zA-Z0-9 ]", ""));
							prinWriter.write("\n");
						}
					}

				}
			}
		}
	}
}
