package edu.usc.tourdemuseum.similarity;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SimilarityCalculation {

	protected static int simVal = 0;
	protected static String[] keywords = {"battle", "still life", "seascape"};

	private static ILexicalDatabase db = new NictWordNet();
	private static RelatednessCalculator[] rcs = {
		new HirstStOnge(db)
		//		, new LeacockChodorow(db), new Lesk(db),  new WuPalmer(db), 
		//		new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db)
	};

	private static void run( String word1, String word2 ) {
		WS4JConfiguration.getInstance().setMFS(true);
		for ( RelatednessCalculator rc : rcs ) {
			double s = rc.calcRelatednessOfWords(word1, word2);
			//                        if (s >= (double)5)
			//			System.out.println( rc.getClass().getName()+"\t"+s );
			
				if (s >= 4 || word1.toLowerCase().contains(word2.toLowerCase()))
					System.out.println(word1+" "+word2+" : "+s);	
			

		}
	}
	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		int i = 0;
		while(i != args.length)
		{
			getSynonyms(args[i]);
			//                run( "emotional","romantic" );
			i++;
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
			Iterator<JSONObject> iterator = paintings.iterator();
			while (iterator.hasNext()) {
				JSONObject paintingJsonObject = iterator.next();
				//				System.out.println(paintingJsonObject.toJSONString());
				ArrayList<String> words = new ArrayList<>();
				if(paintingJsonObject.containsKey("synonyms"))
				{
					JSONArray synonyms = (JSONArray) paintingJsonObject.get("synonyms");
					Iterator<JSONObject> synIterator = synonyms.iterator();
					while (synIterator.hasNext()) {
						JSONObject synJsonObject =	synIterator.next();
						words.addAll(synJsonObject.keySet());
						Iterator<String> synArrayIterator = synJsonObject.keySet().iterator();
						while (synArrayIterator.hasNext()) {
							String synWord = synArrayIterator.next();
							ArrayList<String> synArray = (ArrayList<String>) synJsonObject.get(synWord);
							words.addAll(synArray);
							//							System.out.println(synArray);
						}

					}	
					System.out.println(words);
				}
				//Calling Similarity function
				Similarity(words,paintingJsonObject);
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void Similarity(ArrayList<String> words,
			JSONObject paintingJsonObject) {
		// TODO Auto-generated method stub
		int i=0;
		for (i=0; i<keywords.length ; i++)
		{
			if (simVal >=4)
			{
				
			}
			simVal=0;
			Iterator<String> wordsIterator = words.iterator();
			while(wordsIterator.hasNext())
			{
				String word = wordsIterator.next();
				String currentKeyword = keywords[i];
				run(currentKeyword,word);
				String stemWord = stemWord(word);
				run(currentKeyword,stemWord);
			}

		}		
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