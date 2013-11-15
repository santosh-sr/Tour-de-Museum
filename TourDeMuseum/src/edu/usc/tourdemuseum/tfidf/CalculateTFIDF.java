package edu.usc.tourdemuseum.tfidf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import edu.usc.tourdemuseum.json.parser.JSONParser;

public class CalculateTFIDF {

	private static final DecimalFormat DF = new DecimalFormat("###.########");

	public static void calculateTFIDF(Path inputFilePath, int totalDocs) throws IOException{
		String termKey;
		String values;
		try(BufferedReader reader = new BufferedReader(new FileReader(inputFilePath.toFile()))){
			String line;

			Path outputFilePath = Paths.get("term-tfidf.txt");
			if(!Files.exists(outputFilePath)){
				Files.createFile(outputFilePath);
			}
			try(PrintWriter writer = new PrintWriter(outputFilePath.toFile())){
				while((line = reader.readLine()) != null){
					String[] splitValues = line.split("->");
					termKey = splitValues[0];
					values = splitValues[1];

					String[] termStatsArray = values.split("\\|");
					int termInDocument = termStatsArray.length; 
					for(String termStats : termStatsArray){
						if(!termStats.isEmpty()){
							termStats = termStats.substring(1, termStats.length() - 1);
							String[] freqStatValues = termStats.split("#");
							String fileName = freqStatValues[0];
							int termFreq = Integer.parseInt(freqStatValues[1]);
							int totalFreq = Integer.parseInt(freqStatValues[2]);
							float tfidf = Float.parseFloat(DF.format(new TFIDF(termFreq, totalFreq, totalDocs, termInDocument).getValue()));

							writer.write(termKey + "@" + fileName + " " + tfidf + "\n");
						}
					}

				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		if(args.length < 1){
			System.err.println("Specify atleast one dataset..");
			System.exit(0);
		}
		
		List<Path> outputPathsList = new ArrayList<>();
		for(String arg : args){
			Path inputPath = Paths.get(arg);
			
			//parse and tokenize the document
			JSONParser.parseJsonAndTokenize(inputPath);
			
			//calculate the term frequencies
			String fileName = inputPath.toFile().getName();
			CalculateTermFrequency.countTermsinDocument(Paths.get(fileName));
			outputPathsList.add(Paths.get(getOutputTermFreqFile(fileName)));
		}
		
		//calculate the inverse document frequencies
		Path[] idfList = new Path[args.length];
		CalculateInverseDocumentFrequency.calculateInverseDocFrequency(outputPathsList.toArray(idfList));

		//calculate tf-idf
		calculateTFIDF(Paths.get("term-stats-frequency-all-documents.txt"), args.length);
	}

	private static String getOutputTermFreqFile(String inputFileName){
		return inputFileName + "-term-frequency.txt";
	}
}
