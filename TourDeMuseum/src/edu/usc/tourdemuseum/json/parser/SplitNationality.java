package edu.usc.tourdemuseum.json.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

public class SplitNationality {
	public static void parseJsonAndTokenize(Path inputPath) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(Files.newInputStream(inputPath));

		ArrayNode jsonArray = (ArrayNode) rootNode.get("paintings");
		Iterator<JsonNode> jsonNodeIter = jsonArray.iterator();
		JsonNode paintingNode, nationalityNode;

		Path fileName = inputPath.getFileName();
		if(!Files.exists(fileName)){
			Files.createFile(fileName);
		}

		StringBuffer buffer = new StringBuffer();
		String yearDate = null;
		while(jsonNodeIter.hasNext()){
			String birth = "", death = "";
			yearDate = null;
			paintingNode = (JsonNode)jsonNodeIter.next();
			nationalityNode = paintingNode.get("nationality");

			if(nationalityNode != null){
				String nationality = nationalityNode.getTextValue();
				if(!nationality.isEmpty()){
					String[] splitValues = nationality.split(",");
					for(String value : splitValues){
						if(value.trim().startsWith("circa") || value.trim().matches("\\d{4}-\\d{4}")
								|| value.trim().contains("cenutry") || value.trim().contains("circa") || value.trim().contains("born")){
							yearDate = value;
							continue;
						}

						buffer.append(value).append(",");
					}

					if(yearDate != null){
						if(yearDate.contains("born")){
							birth = yearDate.replaceAll("[^0-9]", "");
						}else if(yearDate.contains("or")){
							splitValues = yearDate.split("or");

							splitValues = splitValues[0].split("-");
							birth = splitValues[0].replaceAll("[^0-9]", "");
							death = splitValues[1].replaceAll("[^0-9]", "");
						}else if(yearDate.contains("circa")){
							splitValues = yearDate.split("-");
							birth = splitValues[0].replaceAll("[^0-9]", "");
							death = splitValues[1].replaceAll("[^0-9]", "");
						}else if(yearDate.contains("century")){
							birth = death = yearDate;
						}else{
							splitValues = yearDate.split("-");
							birth = splitValues[0].replaceAll("[^0-9]", "");
							death = splitValues[1].replaceAll("[^0-9]", "");
						}
					}

					nationality = buffer.toString();
					if(nationality.endsWith(",")){
						nationality = nationality.substring(0, nationality.length() - 1);
					}
					((ObjectNode) paintingNode).put("nationality", nationality);
					((ObjectNode) paintingNode).put("birth", birth);
					((ObjectNode) paintingNode).put("death", death);
					buffer.setLength(0);
				}
			}
		}

		ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
		writer.writeValue(Files.newOutputStream(Paths.get(fileName + "-with-birth-death.json")), rootNode);
	}

	public static void main(String[] args) throws IOException {
		for(String valueArg : args){
			parseJsonAndTokenize(Paths.get(valueArg));
		}
	}
}
