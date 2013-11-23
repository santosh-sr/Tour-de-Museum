package edu.usc.tourdemuseum.exportToMysql;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringEscapeUtils;

public class InsertClusters {
	private static Connection connection = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		connectDB();
		for(int i =0; i<args.length ; i++)
		{
			readFile(args[i],i);
		}	
	}

	private static void readFile(String string,int fileNbr) {
		// TODO Auto-generated method stub
		BufferedReader br =null;
		String line = "";
		try {
			br = new BufferedReader(new FileReader(string));
			line=br.readLine();
			while((line=br.readLine()) != null)
			{
				String[] fields = line.split("\\|");
				int painting_id = getPaintingId(fields[2]);
				if (fileNbr==0)
				{
					insertArtistCluster(painting_id,fields);
				}
				else if (fileNbr==1)
				{
					insertMediumCluster(painting_id,fields);
				}
				else if (fileNbr==2)
				{
					insertPaintedYearCluster(painting_id,fields);
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void insertPaintedYearCluster (int painting_id, String[] fields) {
		// TODO Auto-generated method stub
		String insertStmt = null;
		try {
			insertStmt = "INSERT INTO painted_year values (\""+StringEscapeUtils.escapeJava(new String(fields[0].getBytes(),"UTF-8"))
					+"\","+painting_id+",\""+StringEscapeUtils.escapeJava(new String(fields[1].getBytes(),"UTF-8"))+"\")";
			System.out.println(insertStmt);
			insertDB(insertStmt);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//			System.exit(0);
		}
	}
	private static void insertMediumCluster(int painting_id, String[] fields) {
		// TODO Auto-generated method stub
		String insertStmt = null;
		try {
			insertStmt = "INSERT INTO medium values (\""+StringEscapeUtils.escapeJava(new String(fields[0].getBytes(),"UTF-8"))
					+"\","+painting_id+",\""+StringEscapeUtils.escapeJava(new String(fields[1].getBytes(),"UTF-8"))+"\")";
			System.out.println(insertStmt);
			insertDB(insertStmt);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//			System.exit(0);
		}
	}
	private static void insertArtistCluster(int painting_id, String[] fields) {
		// TODO Auto-generated method stub
		String insertStmt = null;
		try {
			insertStmt = "INSERT INTO artist values (\""+StringEscapeUtils.escapeJava(new String(fields[0].getBytes(),"UTF-8"))
					+"\","+painting_id+",\""+StringEscapeUtils.escapeJava(new String(fields[1].getBytes(),"UTF-8"))+"\")";
			System.out.println(insertStmt);
			insertDB(insertStmt);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//			System.exit(0);
		}
	}
	protected static void insertDB(String insertStmnt) {
		// TODO Auto-generated method stub
		//			connectDB();
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connection.prepareStatement(insertStmnt);	
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//			System.exit(0);
		}finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			/*	if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/	 
		}		
	}	
	private static int getPaintingId(String string) {
		// TODO Auto-generated method stub
		String selectStmt = "SELECT painting_id FROM painting where imageURL like \""+string+"\"";
		System.out.println(selectStmt);

		//		connectDB();
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connection.prepareStatement(selectStmt);	
			//			preparedStatement.exe;
			ResultSet rs =  preparedStatement.executeQuery();
			int painting_id;
			while(rs.next()){
				painting_id = (int) rs.getObject("painting_id");
				return (painting_id);
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;

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
