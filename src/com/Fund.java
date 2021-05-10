package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Fund {
	
	
		
		//A common method to connect to the DB
		private Connection connect(){
			Connection con = null;
			try{
				Class.forName("com.mysql.jdbc.Driver");

				//Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf_project", "root", "");
				
			}catch (Exception e){
				e.printStackTrace();
			}
			
			return con;
		}
			
		
		
		//Insert Project Details
		public String insertfund(String ID,String Funders_name, String Project_name ,String Amount){
			String output = "";
			try{
					Connection con = connect();
					if (con == null){
						return "Error while connecting to the database for inserting."; 
				}
				
					
					// create a prepared statement
					String query = " insert into fundmanagement(`ID`,`Funders_name`,`Project_name`,`Amount`)"+ " values (?,?,?,?)";
					PreparedStatement preparedStmt = con.prepareStatement(query);
					
					
					 // binding values
					 preparedStmt.setInt(1, 0);
					 preparedStmt.setString(2, Funders_name);
					 preparedStmt.setString(3, Project_name);
					 preparedStmt.setDouble(4, Double.parseDouble(Amount)); 
					 
					 // execute the statement
					 preparedStmt.execute();
					 con.close();
					 
					 String newProj = readfund(); 
					 output = "{\"status\":\"success\", \"data\": \"" + newProj + "\"}";
					 
					 }catch (Exception e)
					 {
						 
						 output = "{\"status\":\"error\", \"data\":\"Error while inserting the project.\"}"; 
						 System.err.println(e.getMessage());
					 }
			 return output;
		 }
		
		
		
		
		public String readfund(){
			String output = "";
			try{
					Connection con = connect();
					if (con == null){
						return "Error while connecting to the database for reading."; 
			}
					
				// Prepare the html table to be displayed
				output = 
						"<table border='1' >"+ 
						"<tr >" +
							 "<th >Funders Name</th>" +
							 "<th >project Name</th>" +
							 "<th>Amount</th>" +
							 "<th>Update</th>" +
							 "<th>Remove</th>" +
						
						 "</tr>";
	
				 String query = "select * from fundmanagement";
				 Statement stmt = con.createStatement();
				 ResultSet rs = stmt.executeQuery(query);
				 
				 
				 // iterate through the rows in the result set
				 while (rs.next()){
					 
					 
					 String ID =  Integer.toString(rs.getInt("ID"));
					 String Funders_name = rs.getString("Funders_name");
					 String Project_name = rs.getString("Project_name");
					 String Amount = Double.toString(rs.getDouble("Amount"));
	
					 
					 // Add into the html table
					 
					 //output += "<tr><td>" + ID + "</td>";
					 output += "<td>" + Funders_name + "</td>";
					 output += "<td>" + Project_name + "</td>";
					 output += "<td>" + Amount + "</td>";
					
		
					 
					 
					 // buttons
					
					 output += "<td><input name='btnUpdate' type='button' value='Update' "
								+ "class='btnUpdate btn btn-secondary' data-userid='" + ID + "'></td>"
								+ "<td><input name='btnRemove' type='button' value='Remove' "
								+ "class='btnRemove btn btn-danger' data-userid='" + ID + "'></td></tr>"; 
				 }
			 con.close();
			 
			 // Complete the html table
			 output += "</table>";
			 
			 
			 }catch (Exception e){
				 
				 output = "Error while reading the Projects.";
				 System.err.println(e.getMessage());
			 }
			 return output;
			 
		}
		
		
		
		public String updatefund(String ID, String Funders_name, String Project_name, String Amount){ 
			String output = ""; 
			try{
				Connection con = connect(); 
				if (con == null){
					return "Error while connecting to the database for updating."; 
				} 
				
				 // create a prepared statement
				 String query = "UPDATE fundmanagement SET Funders_name=?,Project_name=?,Amount=? WHERE ID=?";
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				  
				 preparedStmt.setString(1, Funders_name); 
				 preparedStmt.setString(2, Project_name); 
				 preparedStmt.setString(3, Amount);
				 preparedStmt.setString(4, ID);
				 
				// preparedStmt.setString(4, sector);
				
				 
 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 
				 String newProj = readfund(); 
				 output = "{\"status\":\"success\", \"data\": \"" + newProj + "\"}";
				 
		
				 } catch (Exception e) {
					 
					 output = "{\"status\":\"error\", \"data\": \"Error while updating the project.\"}";
					 System.err.println(e.getMessage()); 
				 } 
				 return output; 
		 }
		
		

		public String deletefund(String ID) { 
			String output = ""; 
			try{ 
				Connection con = connect(); 
				if (con == null) { 
					return "Error while connecting to the database for deleting."; 
				} 
					// create a prepared statement
					String query = "delete from fundmanagement where ID=?"; 
					PreparedStatement preparedStmt = con.prepareStatement(query); 
					
					// binding values
					preparedStmt.setInt(1, Integer.parseInt(ID)); 
					
					// execute the statement
					preparedStmt.execute(); 
					con.close(); 
					
					String newUser = readfund(); 
					output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}"; 
					
			} catch (Exception e) { 
				output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}"; 
				System.err.println(e.getMessage()); 
			} 
			return output; 
		}
		
}