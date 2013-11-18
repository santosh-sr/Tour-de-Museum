package edu.usc.tourdemuseum.opencalais;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/************************************************************
	- Simple Calais client to process file or files in a folder
	- Takes 2 arguments
		1. File or folder name to process
		2. Output folder name to store response from Calais
	- Please specify the correct web service location url for CALAIS_URL variable
	- Please adjust the values of different request parameters in the createPostMethod

 **************************************************************/

public class HttpClientPost {

	private static final String CALAIS_URL = "http://api.opencalais.com/tag/rs/enrich";

	private String inputStr;
	private HttpClient client;
	private Map<String, ArrayList<String>> entity= new HashMap<>();

	private PostMethod createPostMethod() {

		PostMethod method = new PostMethod(CALAIS_URL);

		// Set mandatory parameters
		method.setRequestHeader("x-calais-licenseID", "z2z6qfuxew52rrmme588sxxx");

		// Set input content type
		method.setRequestHeader("Content-Type", "text/html; charset=UTF-8");

		//method.setRequestHeader("Content-Type", "text/html; charset=UTF-8");
		//method.setRequestHeader("Content-Type", "text/raw; charset=UTF-8");

		// Set response/output format
		//method.setRequestHeader("Accept", "xml/rdf");
		method.setRequestHeader("Accept", "application/json");

		// Enable Social Tags processing
		method.setRequestHeader("enableMetadataType", "SocialTags");

		return method;
	}

	private void run() {
		try {
			postFile(inputStr, createPostMethod());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doRequest(String inputStr2, PostMethod method) {
		try {
			int returnCode = client.executeMethod(method);
			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err.println("The Post method is not implemented by this URI");
				// still consume the response body
				method.getResponseBodyAsString();
			} else if (returnCode == HttpStatus.SC_OK) {
				//				System.out.println("File post succeeded: " + inputStr2);
//				System.out.println("success....");
				saveResponse(inputStr2, method);
			} else {
				System.err.println("File post failed: " + inputStr2);
				System.err.println("Got code: " + returnCode);
				System.err.println("response: "+method.getResponseBodyAsString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
	}

	private void saveResponse(String inputStr2, PostMethod method) {
			PrintWriter writer = null;
		try {						
			JSONParser parser = new JSONParser();
			//Getting output in an object 
			Object obj = parser.parse(method.getResponseBodyAsString());
			JSONObject jsonObject = (JSONObject) obj;
			//getting all keys to iterate and get the type and name of entities
			Iterator<String> iterator = jsonObject.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();					
				JSONObject value = (JSONObject) jsonObject.get(key);
				// Store in a hashMap

				String type;
				String name;
				String name1;
				ArrayList<String> arr = new ArrayList<>();
				ArrayList<String> arr1 = new ArrayList<>();
				String type1 = (String) value.get("_typeGroup");
				
				if (type1 != null && type1.equalsIgnoreCase("socialTag") && (name1 = (String) value.get("name")) != null)
				{
					if (entity.containsKey(type1))
					{
						arr1 = entity.get(type1);							
					}
						arr1.add(name1);
					
					entity.put(type1,arr1);

				}
				
				if ((type = (String) value.get("_type")) != null && (name = (String) value.get("name")) != null)
				{
					if(type.equalsIgnoreCase("Person") || type.equalsIgnoreCase("Organization")){
						if (entity.containsKey(type))
						{
							arr = entity.get(type);							
						}						
						arr.add(name);
						entity.put(type,arr);

					}
				}


			}


				

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	private void postFile(String inputStr, PostMethod method) throws IOException {
		method.setRequestEntity(new StringRequestEntity(inputStr));
		doRequest(inputStr, method);
	}


	public void openCalais(String str){
		this.inputStr = str;
		this.client = new HttpClient();
		this.client.getParams().setParameter("http.useragent", "Calais Rest Client");

		run();
		System.out.println("run:" + entity);
	}

	public Map<String, ArrayList<String>> getMap(){
		return entity;
	}

}