/*
Driver.java
CS1555
*/

import java.util.*;
import java.sql.*;  //import the file containing definitions for the parts
import java.text.ParseException;  //needed by java for database connection and manipulation
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

public class Driver extends MyAuction {
    private static Connection dbcon;
    private Statement statement;
    private PreparedStatement prepStatement; 
    private ResultSet resultSet; 
    private String query;  
	
	public static void main(String [] args) {
	String username = "", password = "";
	String[] categories = {"Pants", "Shirts", "Socks", "Underwear", "Phones", "Computers", "Stereos", "Radios", "Electric", "Engine", "Body", "Accesories", "Tools", "Furniture", "Misc", "Cleaning", "Decorations"};
	

	try {
		//Oracle variable MUST BE SET by sourcing bash.env or tcsh.env or the following line will not compile
		//DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
		String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
		dbcon = DriverManager.getConnection(url, username, password);

	
		
	}
	catch (SQLException s) {
		System.out.print(s.toString());                
		System.exit(1);
	}
	
	//Insert customers
	try{
		
		for(int i = 1; i<100; i++){
			createCustomer("dj"+i+"", "abc"+(i*3)+"", "Joe Rogan"+i+"", ""+i+" G Rd", "bb"+i+"@p.com", dbcon);
			System.out.println("...Customer Added");
		}
	
	}
	catch  (SQLException s){
		System.out.print(s.toString());                
	}
	
	
	//Worked first time then second time it didnt run \-0-/
	
	/*
	
	try{
		 Random rand = new Random();

		int r = rand.nextInt(categories.length);
		int i = 1;
		int x = 0;

		
		while(x <2000){
			putUpAuction("dj"+i+"", "toy", categories[r], ""+(rand.nextInt(15)+1)+"", "ABCD", Integer.toString(rand.nextInt(100)), dbcon);
			x++;
			System.out.println("x");
			i = rand.nextInt(99)+1;
		}
	}
	catch (SQLException s){
		System.out.print(s.toString());                
	}*/
	
	
	
		//Print products per category in the 1,2, and 3 order
		try{
			
			//Household
			printProducts("Cleaning", 1, dbcon);
			printProducts("Cleaning", 2, dbcon);
			printProducts("Cleaning", 3, dbcon);
			
			printProducts("Tools", 1, dbcon);
			printProducts("Tools", 2, dbcon);
			printProducts("Tools", 3, dbcon);
			
			printProducts("Furniture", 1, dbcon);
			printProducts("Furniture", 2, dbcon);
			printProducts("Furniture", 3, dbcon);
			
			printProducts("Decorations", 1, dbcon);
			printProducts("Decorations", 2, dbcon);
			printProducts("Decorations", 3, dbcon);
			
			//Automotive
			printProducts("Electric", 1, dbcon);
			printProducts("Electric", 2, dbcon);
			printProducts("Electric", 3, dbcon);
			
			printProducts("Engine", 1, dbcon);
			printProducts("Engine", 2, dbcon);
			printProducts("Engine", 3, dbcon);
			
			printProducts("Body", 1, dbcon);
			printProducts("Body", 2, dbcon);
			printProducts("Body", 3, dbcon);
			
			printProducts("Accesories", 1, dbcon);
			printProducts("Accesories", 2, dbcon);
			printProducts("Accesories", 3, dbcon);
			
			
			//Electronics
			printProducts("Phones", 1, dbcon);
			printProducts("Phones", 2, dbcon);
			printProducts("Phones", 3, dbcon);
			
			printProducts("Computers", 1, dbcon);
			printProducts("Computers", 2, dbcon);
			printProducts("Computers", 3, dbcon);
			
			printProducts("Stereos", 1, dbcon);
			printProducts("Stereos", 2, dbcon);
			printProducts("Stereos", 3, dbcon);
			
			printProducts("Radios", 1, dbcon);
			printProducts("Radios", 2, dbcon);
			printProducts("Radios", 3, dbcon);
			
			
			//Clothes
			printProducts("Pants", 1, dbcon);
			printProducts("Pants", 2, dbcon);
			printProducts("Pants", 3, dbcon);
			
			printProducts("Shirts", 1, dbcon);
			printProducts("Shirts", 2, dbcon);
			printProducts("Shirts", 3, dbcon);
			
			printProducts("Socks", 1, dbcon);
			printProducts("Socks", 2, dbcon);
			printProducts("Socks", 3, dbcon);
			
			printProducts("Underwear", 1, dbcon);
			printProducts("Underwear", 2, dbcon);
			printProducts("Underwear", 3, dbcon);
			
			
		}
		catch(SQLException s){
			System.out.print(s.toString());
		}
		

		
		try{
			updateTime("02/22/2001 13:08:55", dbcon);
			System.out.print("Time Updated");
		}
		catch(SQLException s){
			System.out.print(s.toString());
		}
}

}


//FUNCTIONS FOR DRIVER AND BENCHMARK	 ***also add in the parameters (dbcon)***

//DONE
//static void printProducts(String cat, String order) throws SQLException {
 
//DONE 
//static ResultSet createCustomer(String username, String password, String name, String address, String email) throws SQLException{

//DONE
//static ResultSet updateTime(String date) throws SQLException{
	
//static void listProducts(String where_user) throws SQLException{

//static ResultSet keywordProducts(String[] keywords) throws SQLException{

//static void putUpAuction(String username, String name, String categories, String days, String desc, String price) throws SQLException{

//static void insertBid(int auctionID, String username, int amount) throws SQLException{

//static void sellThisProduct(int x, int amount, boolean toSell) throws SQLException{

//static ResultSet getSuggestions(String username) throws SQLException}