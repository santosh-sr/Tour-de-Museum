package edu.usc.tourdemuseum.opencalais;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonTest {
	public static void main(String[] args) {

		JSONParser parser = new JSONParser();
		int i=0;
		while(args[i] != null || !args[i].isEmpty())
		{
			try {

				Object obj = parser.parse(new FileReader(args[i]));
				JSONObject jsonObject = (JSONObject) obj;

				// loop array
				JSONArray paintings = (JSONArray) jsonObject.get("paintings");
				Iterator<JSONObject> iterator = paintings.iterator();
				while (iterator.hasNext()) {
					JSONObject jsonObject1 = iterator.next();
					HttpClientPost httpClientPost = new HttpClientPost();
					if(jsonObject1.containsKey("description"))
					{
						httpClientPost.openCalais((String) jsonObject1.get("title")+" "+(String) jsonObject1.get("description"));
					}
					else 
					{
						httpClientPost.openCalais((String) jsonObject1.get("title"));					
					}
					Map<String, ArrayList<String>> entityMap = httpClientPost.getMap();
					if (!entityMap.isEmpty())
					{
						jsonObject1.put("entities", entityMap);					
					}
				}

				String content = jsonObject.toJSONString();

				File file = new File(args[i]+"_opencalais.json");

				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();
				System.out.println("Done");
				i++;
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
