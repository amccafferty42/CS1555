/*
Driver.java
CS1555
*/

import java.util.*;
import java.sql.*;  //import the file containing definitions for the parts
import java.text.ParseException;  //needed by java for database connection and manipulation
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Driver extends MyAuction {
    private static Connection dbcon;
    private Statement statement;
    private PreparedStatement prepStatement; 
    private ResultSet resultSet; 
    private String query;  
	
	public static void main(String [] args) {
	String username = "daj59", password = "Eckeiscute12#";
	

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
	
		//1,2,3 for order
		try{
			printProducts("Cleaning", 1, dbcon);
		}
		catch(SQLException s){
			System.out.print(s.toString());                
		System.exit(1);
		}
		

}

}


//FUNCTIONS FOR DRIVER AND BENCHMARK	

//static void printProducts(String cat, String order) throws SQLException {
   
//static ResultSet createCustomer(String username, String password, String name, String address, String email) throws SQLException{

//static ResultSet updateTime(String date) throws SQLException{
	
//static void listProducts(String where_user) throws SQLException{

//static ResultSet keywordProducts(String[] keywords) throws SQLException{

//static void putUpAuction(String username, String name, String categories, String days, String desc, String price) throws SQLException{

//static void insertBid(int auctionID, String username, int amount) throws SQLException{

//static void sellThisProduct(int x, int amount, boolean toSell) throws SQLException{

//static ResultSet getSuggestions(String username) throws SQLException}