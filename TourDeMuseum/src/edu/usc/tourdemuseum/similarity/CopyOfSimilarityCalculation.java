package edu.usc.tourdemuseum.similarity;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

public class CopyOfSimilarityCalculation {

	protected static int simVal = 0;
	protected static String[] keywords = {"battle", "still life", "seascape"};

	private static ILexicalDatabase db = new NictWordNet();
	private static RelatednessCalculator[] rcs = {
		new HirstStOnge(db)
		//		, new LeacockChodorow(db), new Lesk(db),  new WuPalmer(db), 
		//		new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db)
	};

	private static boolean isSimilarWord( String word1, String word2 ) {
		WS4JConfiguration.getInstance().setMFS(true);
		double similarityScore;
		for ( RelatednessCalculator rc : rcs ) {
			similarityScore = rc.calcRelatednessOfWords(word1, word2);
			if (similarityScore >= 4 || word1.toLowerCase().contains(word2.toLowerCase())){
				return true;
			}
		}

		return false;
	}
	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		for(int i = 0; i <  args.length; i++){
			getSynonyms(args[i]);
		}
		
		long t1 = System.currentTimeMillis();
		System.out.println( "Done in "+(t1-t0)+" msec." );
	}
	private static void getSynonyms(String file) {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONObject jsonObject = (JSONObject) obj;
			// loop paintings JSON array
			JSONArray paintings = (JSONArray) jsonObject.get("paintings");
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = paintings.iterator();
			while (iterator.hasNext()) {
				JSONObject paintingJsonObject = iterator.next();
				//				System.out.println(paintingJsonObject.toJSONString());
				ArrayList<String> words = new ArrayList<>();
				if(paintingJsonObject.containsKey("synonyms"))
				{
					JSONArray synonyms = (JSONArray) paintingJsonObject.get("synonyms");
					@SuppressWarnings("unchecked")
					Iterator<JSONObject> synIterator = synonyms.iterator();
					while (synIterator.hasNext()) {
						JSONObject synJsonObject =	synIterator.next();
						words.addAll(synJsonObject.keySet());
						Iterator<String> synArrayIterator = synJsonObject.keySet().iterator();
						while (synArrayIterator.hasNext()) {
							String synWord = synArrayIterator.next();
							List<String> synArray = (List<String>) synJsonObject.get(synWord);
							words.addAll(synArray);
							//							System.out.println(synArray);
						}

					}	
					System.out.println(words);
				}

				//Calling Similarity function
				boolean isSimilar;
				for (int i = 0; i < keywords.length ; i++){
					isSimilar = findSimilarArts(keywords[i], words);
					if(isSimilar){
						System.out.println(((JSONObject)paintingJsonObject.get("title")).toString());
					}
				}
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static boolean findSimilarArts(String classifier, List<String> wordsList) {
		// TODO Auto-generated method stub
		boolean withStemSimilarity = false, withoutStemSimilarity = false;

		for(String word : wordsList){
			withoutStemSimilarity = isSimilarWord(classifier,word);

			//Stem Words
			String stemWord = stemWord(word);
			withStemSimilarity = isSimilarWord(classifier,stemWord);
		}

		return withoutStemSimilarity || withStemSimilarity;

	}

	private static String stemWord(String inputWord){
		Stemmer stemmer = new Stemmer();
		for(int i=0; i<inputWord.length(); i++){
			stemmer.add(inputWord.charAt(i));
		}

		stemmer.stem();
		return stemmer.toString();
	}
}