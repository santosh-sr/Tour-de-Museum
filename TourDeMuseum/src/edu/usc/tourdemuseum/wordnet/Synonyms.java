package edu.usc.tourdemuseum.wordnet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;

public class Synonyms {
	private static Dictionary dictionary;

	public static void initDictionary(String absPropertiesPath) throws JWNLException, IOException{
		dictionary = Dictionary.getInstance(Files.newInputStream(Paths.get(absPropertiesPath)));
		//"C:\\Semester\\Fall 2013\\CS 548\\Project\\tools\\extjwnl-1.6.10\\extjwnl-1.6.10\\src\\extjwnl\\src\\main\\resources\\net\\sf\\extjwnl\\file_properties.xml"
	}

	public static Set<String> getSynonyms(String lemmaWord, POS... posargs) throws JWNLException{
		Set<String> synonymsSet = new HashSet<>();

		//when the pos arguments is null, by default fetch synset for NOUN speech
		if(posargs == null){
			posargs = new POS[]{POS.NOUN};
		}

		for(POS pos : posargs){
			IndexWord indexedWord = dictionary.lookupIndexWord(pos, lemmaWord);
			if(indexedWord != null){
				for(Synset senses : indexedWord.getSenses()){
					for(Word word : senses.getWords()){
						String synLemma = word.getLemma();
						if(!synLemma.equalsIgnoreCase(lemmaWord))
							synonymsSet.add(synLemma);
					}
				}
			}
		}

		return synonymsSet;
	}
}
