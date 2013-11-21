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

public class SplitPaintedDate {

	public static void parseJsonAndTokenize(Path inputPath) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(Files.newInputStream(inputPath));

		ArrayNode jsonArray = (ArrayNode) rootNode.get("paintings");
		Iterator<JsonNode> jsonNodeIter = jsonArray.iterator();
		JsonNode paintingNode, dateNode;

		Path fileName = inputPath.getFileName();
		if(!Files.exists(fileName)){
			Files.createFile(fileName);
		}

		while(jsonNodeIter.hasNext()){
			paintingNode = (JsonNode)jsonNodeIter.next();
			dateNode = paintingNode.get("date");

			if(dateNode != null){
				String paintedDate = dateNode.getTextValue().trim();
				if(!paintedDate.isEmpty()){
					if(paintedDate.contains("century")){
						((ObjectNode) paintingNode).put("date", paintedDate.substring(paintedDate.length() - 12,
								paintedDate.length()));
					}else{
						paintedDate = paintedDate.replaceAll("[^0-9]", "");
						if(!paintedDate.isEmpty()){
							if(paintedDate.length() >= 4){
								((ObjectNode) paintingNode).put("date", paintedDate.substring(0, 4));
							}else{
								System.out.println("");
							}
						}
					}
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
