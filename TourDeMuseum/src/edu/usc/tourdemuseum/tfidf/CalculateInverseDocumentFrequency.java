package edu.usc.tourdemuseum.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalculateInverseDocumentFrequency {
	public static void calculateInverseDocFrequency(Path... inputFilePaths) throws IOException{
		Map<String, List<TermStats>> termStatsFrequency = new HashMap<>();
		int totalTermsInDoc = 0;
		List<TermStats> termStatsList;
		String origFileName = null;
		for(Path inputPath : inputFilePaths){
			File inputFile = inputPath.toFile();
			try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))){
				String line;

				line = reader.readLine();
				if(line.startsWith("#")){
					String[] header = line.substring(1).split("->");
					origFileName = header[0];
					totalTermsInDoc = Integer.parseInt(header[1]);
				}
				
				while((line = reader.readLine()) != null){
					String[] splitValues = line.split("->");
					String term = splitValues[0];
					int val = Integer.parseInt(splitValues[1]);
					if(termStatsFrequency.containsKey(term)){
						termStatsList = termStatsFrequency.get(term);
					}else{
						termStatsList = new ArrayList<>();
					}
					
					termStatsList.add(new TermStats(origFileName, val, totalTermsInDoc));
					termStatsFrequency.put(term, termStatsList);
				}
			}
		}
		
		//Write the term stats in output file
		Path outputFilePath = Paths.get("term-stats-frequency-all-documents.txt");
		if(!Files.exists(outputFilePath)){
			Files.createFile(outputFilePath);
		}
		try(PrintWriter writer = new PrintWriter(outputFilePath.toFile())){
			Set<String> keySet = termStatsFrequency.keySet();
			
			StringBuffer buffer = new StringBuffer();
			for(String termKey : keySet){
				termStatsList = termStatsFrequency.get(termKey);
				for(TermStats stats : termStatsList){
					buffer.append(stats.toString()).append("|");
				}
				
				writer.write(termKey + "->" + buffer.toString());
				writer.write("\n");
				buffer.setLength(0);
			}
		}
	}

	private static class TermStats {
		private int frqInDocument;

		private int totalTermsInDocument;

		private String inputFileName;

		public TermStats(String inputFileName, int freqInDocument, int totalTermsInDocument){
			this.inputFileName = inputFileName;
			this.frqInDocument = freqInDocument;
			this.totalTermsInDocument = totalTermsInDocument;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "<" + inputFileName + "#" + frqInDocument + "#" + totalTermsInDocument + ">";
		}

	}
}
