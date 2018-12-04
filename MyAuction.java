import java.util.*;
import java.sql.*;  //import the file containing definitions for the parts
import java.text.ParseException;  //needed by java for database connection and manipulation
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class MyAuction {
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

            Scanner reader = new Scanner(System.in);
            int input = 0;
            do{
                System.out.println("Sign In:\n" +
                                    "Admin\t\t(1)\n" +
                                    "Customer\t(2)\n");
                if(reader.hasNextInt()){
                    input = reader.nextInt();
                }else{
                    reader.next();
                }
            }while(input != 1 && input != 2);
            login(input);
        }
        catch (SQLException s) {
            System.out.print(s.toString());                
            System.exit(1);
        }
    }

    static void login(int input) throws SQLException {
        Scanner reader = new Scanner(System.in);
        String username, password;
        boolean badLogin = true;
        do{
            System.out.print("Username: ");
            username = reader.next();
			
            System.out.print("Password: ");
            password = reader.next();
            Statement statement;
            String query;
            ResultSet resultSet;
            if(input == 1){
                statement = dbcon.createStatement();
                query = "SELECT login, password FROM Administrator WHERE login='"+username+"' AND password='"+password+"'";
                resultSet = statement.executeQuery(query);
                //returns boolean, check if result set is not empty
                if(resultSet.next()){
                    adminInterface();
                }else{
                    System.out.println("Admin does not exist");
                }
                badLogin = false;
            }else{
                statement = dbcon.createStatement();
                query = "SELECT login, password FROM Customer WHERE login='"+username+"' AND password='"+password+"'";
                resultSet = statement.executeQuery(query);
                if(resultSet.next()){
                    customerInterface(username);
                }else{
                    System.out.println("Customer does not exist");
                }
                badLogin = false;
            }
        }while(badLogin);
        
    }

    static void adminInterface() throws SQLException {
        Scanner reader = new Scanner(System.in);
        int input = 0;
        do{
            System.out.println("\nAdmin Interface:\n" +
                                "Register New Customer\t(1)\n" +
                                "Update System Time\t(2)\n" +
                                "Product Stats\t\t(3)\n" +
								"Statistics\t\t(4)\n" +
                                "Quit\t\t\t(5)\n");
            if(reader.hasNextInt()){
                input = reader.nextInt();
            }else{
                input = 0;
                reader.next();
            }
            if(input == 1){
                registerCustomer();
            }else if(input == 2){
				try{
					updateSystemTime();
				}
				catch (SQLException s) {
					System.out.print(s.toString());                
					System.exit(1);
				}
            }else if(input == 3){
                productStats();
            }
			else if(input == 4){
                statistics();
            }
			
			
			
        }while(input != 5);
    }

    static void registerCustomer() throws SQLException {
        Scanner reader = new Scanner(System.in);
        System.out.println("Register New Customer");
        System.out.print("Name: ");
        String name = reader.next();
        System.out.print("Address: ");
        String address = reader.next();
        System.out.print("Email: ");
        String email = reader.next();
        System.out.print("Username: ");
        String username = reader.next();
        System.out.print("Password: ");
        String password = reader.next();

        Statement statement = dbcon.createStatement();
        String query = "INSERT INTO Customer VALUES ('"+username+"','"+password+"','"+name+"','"+address+"','"+email+"')";
        ResultSet resultSet = statement.executeQuery(query);

        printCustomer();
    }

    //TEST method to print out customer table entries
    static void printCustomer() throws SQLException {
        Statement statement = dbcon.createStatement();
        String query = "SELECT * FROM Customer";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + ", "+resultSet.getString(2) + ", "+resultSet.getString(3) + ", "+resultSet.getString(4) + ", "+resultSet.getString(5));
        }
    }

    static void updateSystemTime() throws SQLException{
		Scanner reader = new Scanner(System.in);
        System.out.println("Update System Time: ");
		String date = "";
		
		System.out.println("MONTH in numbers: ");
		int month = reader.nextInt();
		date += (month);
		date += ("/");
		
		System.out.println("DAY: ");
		int day = reader.nextInt();
		date += (day);
		date += ("/");
		
		System.out.println("YEAR: ");
		int year = reader.nextInt();
		date += (year);
		date += (" ");
		
		System.out.println("HOUR: ");
		int hour = reader.nextInt();
		date += (hour);
		date += (":");
		
		System.out.println("MINUTES: ");
		int minutes = reader.nextInt();
		date += (minutes);
		date += (":");
		
		System.out.println("SECONDS");
		int seconds = reader.nextInt();
		date += (seconds);
		
		
		Statement statement = dbcon.createStatement();
        String query = "UPDATE ourSysDate set c_date = (to_date('"+date+"', 'mm/dd/yyyy hh24:mi:ss'))";
        ResultSet resultSet = statement.executeQuery(query);
		
		query = "SELECT * from ourSysDate";
        resultSet = statement.executeQuery(query);
	resultSet.next();
		System.out.println(resultSet.getString(1));
		
        //update DB
    }

    static void productStats() throws SQLException{
       System.out.println("Product Stats:");
       Scanner reader = new Scanner(System.in);
       int input = 0;
       do{
           System.out.println("List all products\t(1)\n" +
                               "List user products\t(2)\n");
           if(reader.hasNextInt()){
               input = reader.nextInt();
           }else{
               reader.nextLine();
           }
       }while(input != 1 && input != 2);
	   
       String where_user = " WHERE ";
       if(input == 2){
           System.out.print("Username: ");
           String username = reader.next();

           where_user += " seller = '" + username + "' AND";
		   
       }

       Statement statement = dbcon.createStatement();
       String query = "SELECT name, status, amount, bidder FROM Product natural join bidLog" + where_user + " status = 'under auction'";
       ResultSet resultSet = statement.executeQuery(query);

       while (resultSet.next()) {
           System.out.println(resultSet.getString(1) + " '" + resultSet.getString(2) + "' " + resultSet.getString(3) + " " + resultSet.getString(4));
       }
	   
	   statement = dbcon.createStatement();
       query = "SELECT name, status, amount, buyer FROM Product" + where_user + " status = 'sold'";
       resultSet = statement.executeQuery(query);

       while (resultSet.next()) {
           System.out.println(resultSet.getString(1) + " '" + resultSet.getString(2) + "' " + resultSet.getString(3) + " " + resultSet.getString(4));
       }
	   
   }


    static void customerInterface(String username){
        Scanner reader = new Scanner(System.in);
        int input = 0;
        do{
            System.out.println("\nCustomer Interface:\n" +
                                "Browse Products\t\t(1)\n" +
                                "Search Products\t\t(2)\n" +
                                "Auction Product\t\t(3)\n" +
                                "Bid on Product\t\t(4)\n" +
                                "Sell Product\t\t(5)\n" +
                                "Suggestions\t\t(6)\n" +
                                "Quit\t\t\t(7)\n");
            if(reader.hasNextInt()){
                input = reader.nextInt();
            }else{
                input = 0;
                reader.next();
            }
            if(input == 1){
				try{
					browseProducts();
				}
			 catch (SQLException s) {
					System.out.print(s.toString());                
					System.exit(1);
				}
            }else if(input == 2){
                try{
				searchProducts();
				}
			 catch (SQLException s) {
					System.out.print(s.toString());                
					System.exit(1);
				}
				
            }else if(input == 3){
                try{
				auctionProducts(username);
				}
			 catch (SQLException s) {
					System.out.print(s.toString());                
					System.exit(1);
				}
            }else if(input == 4){
                try{
				bidOnProducts(username);
				}
			 catch (SQLException s) {
					System.out.print(s.toString());                
					System.exit(1);
				}
            }else if(input == 5){
                try{
				sellProduct();
				}
			 catch (SQLException s) {
					System.out.print(s.toString());                
					System.exit(1);
				}
            }else if(input == 6){
                try{
				suggestions(username);
				}
			 catch (SQLException s) {
					System.out.print(s.toString());                
					System.exit(1);
				}
            }
        }while(input != 7);
    }

    static void browseProducts() throws SQLException{
		Scanner reader = new Scanner(System.in);
		
		
        System.out.println("Browsing Products....\n");

		Statement statement = dbcon.createStatement();
		String query = "SELECT * from Category where parent_category is null";
		ResultSet resultSet = statement.executeQuery(query);

		
		while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
		
		String category = "";
		boolean exist = false;
		while(!exist){
			ResultSet resultSet1 = statement.executeQuery(query);
		
			System.out.print("\nWhat Category: ");
			
			category = reader.nextLine();
			
			while (resultSet1.next()) {
				if(category.equalsIgnoreCase(  resultSet1.getString(1))){
					exist = true;
				}
			
			}
		}
	
		query = "SELECT parent_category from category where parent_category = '"+category+"'";
		resultSet = statement.executeQuery(query);
	
		if(resultSet.next()){
			query = "SELECT name from category where parent_category = '"+category+"'";
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
			}
			
			exist = false;
			while(!exist){
				ResultSet resultSet1 = statement.executeQuery(query);
			
				System.out.print("\nWhat Category: ");
				
				category = reader.nextLine();
				
				while (resultSet1.next()) {
					if(category.equalsIgnoreCase(  resultSet1.getString(1))){
						exist = true;
					}
				
				}
			}
		}
		
		String where_cat = " WHERE category = '" + category + "'AND status = 'under auction'";

       int order = 0;
	   String order_by = "";
       do{
           System.out.println("Order?:\n" +
                       "Alphabetical\t\t(1)\n" +
                       "Highest Bid\t\t(2)\n" +
                       "No Order\t\t(3)\n");
           order = reader.nextInt();
       }while(order != 1 && order != 2 && order != 3);
       if(order == 1){
           order_by = " ORDER BY name";
       }else if(order == 2){
           order_by = " ORDER BY amount desc";
       }else{
           order_by = "";
       }
      
       printProducts(where_cat, order_by);
		
	
	}











static void searchProducts() throws SQLException{
       Scanner reader = new Scanner(System.in);
       System.out.println("Search Products");
       System.out.print("Keywords:");
       String words = reader.nextLine();
       String[] keywords = words.split(" ");
      
       //query Product Table
       Statement statement = dbcon.createStatement();
       String query = "SELECT auction_id, name, description FROM Product WHERE description LIKE '%" + keywords[0] +"%'";
       if(keywords.length >= 2){
           query += " AND description LIKE '%" + keywords[1] + "%'";
       }
       ResultSet resultSet = statement.executeQuery(query);
      
       while (resultSet.next()) {
           System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " '" + resultSet.getString(3) + "'");
       }
   }

   
   
   
   
   
   
   
   
   
   
   

 static void auctionProducts(String username) throws SQLException{
       Scanner reader = new Scanner(System.in);
       System.out.println("Put a product up for auction");
       System.out.print("Name: ");
       String name = reader.nextLine();
       System.out.print("Description: ");
       String desc = reader.nextLine();
       System.out.print("Categories: ");
       String categories = reader.nextLine();
       int days = 0;
       do{
           System.out.print("Days for Auction: ");
           if(reader.hasNextInt()){
               days = reader.nextInt();
               if(days > 0){
                   break;
               }
           }else{
               reader.nextLine();
			   
           }
       }while(true);
       int price = 0;
       do{
           System.out.print("Min Price: ");
           if(reader.hasNextInt()){
               price = reader.nextInt();
               if(price > 0){
                   break;
               }
           }else{
               reader.nextLine();
           }
       }while(true);
	  
  	   //String inputs = "'" + username + "', '" + name + "', '" + categories + "', " + days + ", '" + desc + "', " + price + "";
       String query = "{CALL proc_putProduct(?, ?, ?, ?, ?, ?)}";
	   CallableStatement statement = dbcon.prepareCall(query);
	   statement.setString(1, username);
	   statement.setString(2, name);
	   statement.setString(3, categories);
	   statement.setString(4, days + "");
	   statement.setString(5, desc);
	   statement.setString(6, price + "");
	   
       statement.executeQuery();
   }

   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   

static void bidOnProducts(String username) throws SQLException{
       Scanner reader = new Scanner(System.in);
       System.out.println("Bid on Products");
	   
       Statement statement = dbcon.createStatement();
       String query = "SELECT max(auction_id) FROM Product";
       ResultSet resultSet = statement.executeQuery(query);
	   resultSet.next();

	   int auctionID = 0;
	   
       do{
           System.out.print("Product ID: ");
           if(reader.hasNextInt()){
               auctionID = reader.nextInt();
               if(auctionID > 0 && auctionID <= resultSet.getInt(1)){
                   break;
               }
           }else{
               reader.next();
           }
       }while(true);
	   
	   int amount = 0;
	   
       do{
           System.out.print("Amount: ");
           if(reader.hasNextInt()){
               amount = reader.nextInt();
               if(amount > 0){
                   break;
               }
           }else{
               reader.next();
           }
       }while(true);


	   
       query = "SELECT * from view_maxBidsn";
       resultSet = statement.executeQuery(query);
	   resultSet.next();
	   int num = 1 + resultSet.getInt(1);
	   
       query = "INSERT INTO Bidlog VALUES("+num+", " + auctionID + ", '" + username + "', (SELECT c_date FROM ourSysDate)  ," + amount + ")";
	   statement.executeQuery(query);
   }


	
	
	
	
	
	
	
	
	
	
	
    static void sellProduct() throws SQLException{
        System.out.println("Selling Product");
    }
	
	
	
	
	
	
	
	
	
	static void statistics() throws SQLException{
       Scanner reader = new Scanner(System.in);
       int x = 0;
       do{
           System.out.println("Number of months to look back: ");
           if(reader.hasNextInt()){
               x = reader.nextInt();
           }
       }while(x <= 0);
       System.out.print("Enter the category name: ");
       String c = reader.next();
	   
       System.out.println("Enter username: ");
       String u = reader.next();

       CallableStatement statement = dbcon.prepareCall("{? = CALL func_productCount(?, ?)}");
       statement.registerOutParameter(1, Types.INTEGER);
       statement.setString(2, x + "");
       statement.setString(3, c);
       statement.execute();
       int output_1 = statement.getInt(1);

       statement = dbcon.prepareCall("{? = CALL func_bidCount(?, ?)}");
       statement.registerOutParameter(1, Types.INTEGER);
       statement.setString(2, x + "");
       statement.setString(3, u);
       statement.execute();
       int output_2 = statement.getInt(1);

       statement = dbcon.prepareCall("{? = CALL func_buyingAmount(?, ?)}");
       statement.registerOutParameter(1, Types.INTEGER);
       statement.setString(2, x + "");
       statement.setString(3, u);
       statement.execute();
       int output_3 = statement.getInt(1);

       System.out.println("Product Count: " + output_1);
       System.out.println("Bid Count: " + output_2);
       System.out.println("Buying Amount: " + output_3);
   }

	
	
	
	
	
	
	
	

    static void suggestions(String username) throws SQLException{
        System.out.println("Suggestions:");
		
		Statement statement = dbcon.createStatement();
		String query = "select auction_id, count(bidder) as bidders from bidlog natural join (select bidder from bidlog natural join (select auction_id from bidlog where bidder = 'juice26') where bidder != '" +username + "') where (auction_id not in (select auction_id from bidlog where bidder = '" +username + "'))Group by auction_id Order by bidders desc";
		ResultSet resultSet = statement.executeQuery(query);
		
		while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
			}
    }
	
	//static void browseProducts(){
	//   System.out.println("Browsing Products");
	///   printProducts();
	//}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

 static void printProducts(String cat, String order) throws SQLException {
       Statement statement = dbcon.createStatement();
       String amount = "";
       if(order.equals(" ORDER BY amount desc")){
           amount = ", amount ";
       }
       String query = "SELECT auction_id, name" + amount +" FROM (Product NATURAL JOIN BelongsTo)" + cat + order;
       ResultSet resultSet = statement.executeQuery(query);

       while (resultSet.next()) {
           String output = resultSet.getString(1) + " " + resultSet.getString(2);
           if(order.equals(" ORDER BY amount desc")){
               output += " " + resultSet.getString(3);
           }
           System.out.println(output);
       }
   }

}





/*
"THIS QUERY IS FOR SUGGESTIONS just put the customer that is looking for 
suggestions in place of 'juice26' will return in most recommended order"

select auction_id, count(bidder) as bidders
    from bidlog natural join (select bidder from bidlog natural join
        (select auction_id from bidlog where bidder = 'juice26') where bidder != 'juice26')
    where 
        (auction_id not in (select auction_id from bidlog where bidder = 'juice26'))
    Group by auction_id
    Order by bidders desc;
*/