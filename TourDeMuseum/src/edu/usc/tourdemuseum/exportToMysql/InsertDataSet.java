package edu.usc.tourdemuseum.exportToMysql;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.commons.lang.StringEscapeUtils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.PreparedStatement;


public class InsertDataSet {
	private static Connection connection = null;
	private static String birth_year;
	private static String death_year;
	private static String title;
	private static String nationality;
	private static String classification;
	private static String artist_name;
	private static String accession_id;
	private static String dimensions;
	private static String credit;
	private static String date;
	private static String medium;
	private static String imageURL;
	private static String provenance;
	private static String description;
	private static String department;
	private static String museum_name;




	public static void main(String[] argv) {
		
		for(int i=0; i<argv.length ; i++)
		{
			getData(argv[i]);
		}
	}

	private static void getData(String file) {
		// TODO Auto-generated method stub
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(file));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray paintings = (JSONArray) jsonObject.get("paintings");
			Iterator<JSONObject> iterator = paintings.iterator();
			while (iterator.hasNext()) {
				JSONObject paintingJsonObject = iterator.next();
				//				System.out.println(paintingJsonObject.keySet());

				if (paintingJsonObject.containsKey("birth"))
				{
					birth_year = new String (((String) paintingJsonObject.get("birth")).getBytes(),"UTF-8");
				}
				else
				{
					birth_year ="";
				}
				if (paintingJsonObject.containsKey("death"))
				{
					death_year = new String (((String) paintingJsonObject.get("death")).getBytes(),"UTF-8");
				}
				else
				{
					death_year="";
				}
				if (paintingJsonObject.containsKey("title"))
				{
					title = new String (((String) paintingJsonObject.get("title")).getBytes(),"UTF-8");
				}
				else
				{
					title ="";
				}
				if (paintingJsonObject.containsKey("nationality"))
				{
					nationality = new String (((String) paintingJsonObject.get("nationality")).getBytes(),"UTF-8");
				}
				else
				{
					nationality="";
				}
				if (paintingJsonObject.containsKey("classification"))
				{
					classification = new String (((String) paintingJsonObject.get("classification")).getBytes(),"UTF-8");
				}
				else
				{
					classification="";
				}
				if (paintingJsonObject.containsKey("name"))
				{
					artist_name= new String (((String) paintingJsonObject.get("name")).getBytes(),"UTF-8");
				}
				else
				{
					artist_name="";
				}
				if (paintingJsonObject.containsKey("accession"))
				{
					accession_id= new String (((String) paintingJsonObject.get("accession")).getBytes(),"UTF-8");
				}
				else
				{
					accession_id="";
				}
				if (paintingJsonObject.containsKey("dimensions"))
				{
					dimensions= new String (((String) paintingJsonObject.get("dimensions")).getBytes(),"UTF-8");
				}
				else
				{
					dimensions="";
				}
				if (paintingJsonObject.containsKey("credit"))
				{
					credit= new String (((String) paintingJsonObject.get("credit")).getBytes(),"UTF-8");
				}
				else
				{
					credit="";
				}
				if (paintingJsonObject.containsKey("date"))
				{
					date= new String (((String) paintingJsonObject.get("date")).getBytes(),"UTF-8");
				}
				else
				{
					date="";
				}
				if (paintingJsonObject.containsKey("medium"))
				{
					medium= new String (((String) paintingJsonObject.get("medium")).getBytes(),"UTF-8");
				}
				else
				{
					medium="";
				}
                if (paintingJsonObject.containsKey("imageURL"))
                {
                	imageURL = new String (((String) paintingJsonObject.get("imageURL")).getBytes(),"UTF-8");
                }
                else
                {
                	imageURL ="";
                }
                if (paintingJsonObject.containsKey("provenance"))
                {
                	provenance = new String (((String) paintingJsonObject.get("provenance")).getBytes(),"UTF-8");
                }
                else
                {
                	provenance="";
                }
                if (paintingJsonObject.containsKey("description"))
                {
                	description= new String (((String) paintingJsonObject.get("description")).getBytes(),"UTF-8");
                }
                else
                {
                	description="";
                }
                if (paintingJsonObject.containsKey("department"))
                {
                	department= new String (((String) paintingJsonObject.get("department")).getBytes(),"UTF-8");
                }
                else
                {
                	department ="";
                }
                if(imageURL.contains("lacma"))
                {
                	museum_name="lacma";
                }
                else if(imageURL.contains("www.dia.org"))
                {
                	museum_name="detroit";
                }
                else if(imageURL.contains("artic"))
                {
                	museum_name="artic";
                }
                else
                {
                	System.out.println("WTF");
                	System.exit(0);
                }
                System.out.println(museum_name);
                insertData();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertData() {
		// TODO Auto-generated method stub
		artist_name = "abc\"nf";
		String insertStmnt = "INSERT INTO painting (birth_year,death_year,title,nationality,classification,"
                +"artist_name,accession_id,dimensions,credit,date,medium,imageURL,provenance,description,department,museum_name)"
                +" VALUES (\""+StringEscapeUtils.escapeJava(birth_year)+"\",\""+StringEscapeUtils.escapeJava(death_year)+"\",\""+StringEscapeUtils.escapeJava(title)+"\",\""+StringEscapeUtils.escapeJava(nationality)+"\",\""+StringEscapeUtils.escapeJava(classification)+"\",\""
                +StringEscapeUtils.escapeJava(artist_name)+"\",\""+StringEscapeUtils.escapeJava(accession_id)+"\",\""+StringEscapeUtils.escapeJava(dimensions)+"\",\""+StringEscapeUtils.escapeJava(credit)+"\",\""+StringEscapeUtils.escapeJava(date)+"\",\""+StringEscapeUtils.escapeJava(medium)+"\",\""
                +imageURL+"\",\""+StringEscapeUtils.escapeJava(provenance)+"\",\""+StringEscapeUtils.escapeJava(description)+"\",\""+StringEscapeUtils.escapeJava(department)+"\",\""+StringEscapeUtils.escapeJava(museum_name)+"\")";
		System.out.println(insertStmnt);
		
		connectDB();
		PreparedStatement preparedStatement = null;
		try {
			
			preparedStatement = connection.prepareStatement(insertStmnt);	
//			preparedStatement.exe;
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.exit(0);
		}finally {
 
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
 
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
 
		}
	}

	private static void connectDB() {
		// TODO Auto-generated method stub


//		System.out.println("-------- MySQL JDBC Connection Testing ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

//		System.out.println("MySQL JDBC Driver Registered!");


		try {
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/tourdemuseum","root", "mysql");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
//			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}

	}
}