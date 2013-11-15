package edu.usc.tourdemuseum.tfidf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

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
		Path americanDetroitPath = Paths.get("C:\\Semester\\Fall 2013\\CS 548\\Project\\tour-de-museum\\TourDeMuseum\\detroit-american.txt");
		Path europeanDetroitPaintingsPath = Paths.get("C:\\Semester\\Fall 2013\\CS 548\\Project\\tour-de-museum\\TourDeMuseum\\data-paintings.txt");
		Path europeanDetroitSculpturesPath = Paths.get("C:\\Semester\\Fall 2013\\CS 548\\Project\\tour-de-museum\\TourDeMuseum\\data-sculptures.txt");
		Path europeanDetroitModernArtPath = Paths.get("C:\\Semester\\Fall 2013\\CS 548\\Project\\tour-de-museum\\TourDeMuseum\\data-modern art.txt");

		//calculate the term frequencies
		CalculateTermFrequency.countTermsinDocument(americanDetroitPath);
		CalculateTermFrequency.countTermsinDocument(europeanDetroitPaintingsPath);
		CalculateTermFrequency.countTermsinDocument(europeanDetroitSculpturesPath);
		CalculateTermFrequency.countTermsinDocument(europeanDetroitModernArtPath);

		//calculate the inverse document frequencies
		CalculateInverseDocumentFrequency.calculateInverseDocFrequency(new Path[]{Paths.get(getOutputTermFreqFile(americanDetroitPath.toFile().getName())), 
				Paths.get(getOutputTermFreqFile(europeanDetroitPaintingsPath.toFile().getName())),
				Paths.get(getOutputTermFreqFile(europeanDetroitSculpturesPath.toFile().getName())),
				Paths.get(getOutputTermFreqFile(europeanDetroitModernArtPath.toFile().getName()))});

		//calculate tf-idf
		calculateTFIDF(Paths.get("term-stats-frequency-all-documents.txt"), 4);
		
	/*	Path testPath = Paths.get("C:\\Semester\\Fall 2013\\CS 548\\Project\\tour-de-museum\\TourDeMuseum\\test-detroit.txt");
		CalculateTermFrequency.countTermsinDocument(testPath);
		CalculateInverseDocumentFrequency.calculateInverseDocFrequency(new Path[]{Paths.get(getOutputTermFreqFile(testPath.toFile().getName()))});
		calculateTFIDF(Paths.get("term-stats-frequency-all-documents.txt"), 1);*/
	}

	private static String getOutputTermFreqFile(String inputFileName){
		return inputFileName + "-term-frequency.txt";
	}
}
