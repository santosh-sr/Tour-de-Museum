package edu.usc.tourdemuseum.opencalais;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

	private File input;
	private String inputStr;
	private File output;
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
			//			if (input.isFile()) {
			//				postFile(input, createPostMethod());
			//			} else if (input.isDirectory()) {
			//				System.out.println("working on all files in " + input.getAbsolutePath());
			//				for (File file : input.listFiles()) {
			//					if (file.isFile())
			//						postFile(file, createPostMethod());
			//					else
			//						System.out.println("skipping "+file.getAbsolutePath());
			//				}
			//			}
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
		//		Map<String, String> mMap = new HashMap();
		try {

String content =method.getResponseBodyAsString();
						BufferedReader reader = new BufferedReader(new InputStreamReader(
								method.getResponseBodyAsStream(), "UTF-8"));
						File out = new File("C:\\cygwin64\\home\\Ankit\\cs548\\Project\\data and scripts\\asd.xml");
						
						FileWriter fw = new FileWriter(out.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(content);
						bw.close();
						
			JSONParser parser = new JSONParser();
			//Getting output in an object 
			Object obj = parser.parse(method.getResponseBodyAsString());
			JSONObject jsonObject = (JSONObject) obj;
			//				Set<String> keys = jsonObject.keySet();
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
						//System.out.println("TYPE: "+type+"\nNAME: "+name);
						if (entity.containsKey(type))
						{
							arr = entity.get(type);							
						}						
						arr.add(name);
						entity.put(type,arr);

//						System.out.println("Ankita"+entity);
					}
				}


			}

//			System.out.println("santosh"+entity);
			//			System.out.println("Ankit"+mMap);

				

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void postFile(String inputStr, PostMethod method) throws IOException {
		method.setRequestEntity(new StringRequestEntity(inputStr));
		doRequest(inputStr, method);
	}


	//	public static void main(String[] args) {
	public void openCalais(String str){
		//		verifyArgs(args);

		this.inputStr = str;
		//		httpClientPost.input = new File(args[0]);
		//		httpClientPost.output = new File(args[1]);
		this.client = new HttpClient();
		this.client.getParams().setParameter("http.useragent", "Calais Rest Client");

		run();
		System.out.println("run:" + entity);
	}

	private static void verifyArgs(String[] args) {
		if (args.length==0) {
			usageError("no params supplied");
		} else if (args.length < 2) {
			usageError("2 params are required");
		} else {
			if (!new File(args[0]).exists())
				usageError("file " + args[0] + " doesn't exist");
			File outdir = new File(args[1]);
			if (!outdir.exists() && !outdir.mkdirs())
				usageError("couldn't create output dir");
		}
	}

	private static void usageError(String s) {
		System.err.println(s);
		System.err.println("Usage: java " + (new Object() { }.getClass().getEnclosingClass()).getName() + " input_dir output_dir");
		System.exit(-1);
	}

	public Map<String, ArrayList<String>> getMap(){
		return entity;
	}

}