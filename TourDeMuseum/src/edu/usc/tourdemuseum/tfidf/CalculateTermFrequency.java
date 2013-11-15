package edu.usc.tourdemuseum.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CalculateTermFrequency {
	
	private static Set<String> googleStopwords;

	static {
		googleStopwords = new HashSet<String>();
		googleStopwords.add("I"); googleStopwords.add("a"); googleStopwords.add("about");
		googleStopwords.add("an"); googleStopwords.add("are"); googleStopwords.add("as");
		googleStopwords.add("at"); googleStopwords.add("be"); googleStopwords.add("by");
		googleStopwords.add("com"); googleStopwords.add("de"); googleStopwords.add("en");
		googleStopwords.add("for"); googleStopwords.add("from"); googleStopwords.add("how");
		googleStopwords.add("in"); googleStopwords.add("is"); googleStopwords.add("it");
		googleStopwords.add("la"); googleStopwords.add("of"); googleStopwords.add("on");
		googleStopwords.add("or"); googleStopwords.add("that"); googleStopwords.add("the");
		googleStopwords.add("this"); googleStopwords.add("to"); googleStopwords.add("was");
		googleStopwords.add("what"); googleStopwords.add("when"); googleStopwords.add("where"); 
		googleStopwords.add("who"); googleStopwords.add("will"); googleStopwords.add("with");
		googleStopwords.add("and"); googleStopwords.add("the"); googleStopwords.add("www");
		googleStopwords.add("his"); googleStopwords.add("her");
	}
	
	public static void countTermsinDocument(Path inputPath) throws IOException{
		Map<String, Integer> termFrequencyMap = new HashMap<>();
		File inputFile = inputPath.toFile();
		try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
			String line;

			while((line = reader.readLine()) != null){
				String[] splitValues = line.split(" ");
				for(String val : splitValues){
					val = val.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

					if(!val.isEmpty()){
						
						// remove names starting with non letters, digits, considered stopwords or containing other chars
						if (!Character.isLetter(val.charAt(0)) || Character.isDigit(val.charAt(0))
								|| googleStopwords.contains(val) || val.contains("_") || 
								val.length() < 3) {
							continue;
						}
						
						if(termFrequencyMap.containsKey(val)){
							int count = termFrequencyMap.get(val);
							termFrequencyMap.put(val, ++count);
						}else{
							termFrequencyMap.put(val, 1);
						}
					}
				}
			}
		}

		//Write the term frequencies
		String fileName = inputFile.getName();//.split(".")[0];
		try(PrintWriter writer = new PrintWriter(Paths.get(fileName + "-term-frequency.txt").toFile())){
			Set<String> keySet = termFrequencyMap.keySet();
			int totalFrequency = 0;
			for(String key : keySet){
				Integer termFrequency = termFrequencyMap.get(key);
				totalFrequency += termFrequency;
			}

			writer.write("#" + fileName + "->" + totalFrequency + "\n");
			for(String key : keySet){
				writer.write(key + " " + termFrequencyMap.get(key));
				writer.write("\n");
			}
		}
	}
}
