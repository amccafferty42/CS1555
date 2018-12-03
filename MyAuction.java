import java.util.Scanner;
import java.sql.*;  //import the file containing definitions for the parts
import java.text.ParseException;  //needed by java for database connection and manipulation

public class MyAuction {
    private static Connection dbcon;
    private Statement statement;
    private PreparedStatement prepStatement; 
    private ResultSet resultSet; 
    private String query;  

    public static void main(String [] args) {
        String username = "asm122", password = "3942566";
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
                    customerInterface();
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
                                "Quit\t\t\t(4)\n");
            if(reader.hasNextInt()){
                input = reader.nextInt();
            }else{
                input = 0;
                reader.next();
            }
            if(input == 1){
                registerCustomer();
            }else if(input == 2){
                updateSystemTime();
            }else if(input == 3){
                productStats();
            }
        }while(input != 4);
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

    static void updateSystemTime(){
        System.out.println("Update System Time: ");
        //update DB
    }

    static void productStats(){
        System.out.println("Product Stats:");
        //output oracle functions
        // func_productCount
        // func_bidCount
    }

    static void customerInterface(){
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
                browseProducts();
            }else if(input == 2){
                searchProducts();
            }else if(input == 3){
                auctionProducts();
            }else if(input == 4){
                bidOnProducts();
            }else if(input == 5){
                sellProduct();
            }else if(input == 6){
                suggestions();
            }
        }while(input != 7);
    }

    static void browseProducts(){
        System.out.println("Browsing Products");
    }

    static void searchProducts(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Search Products");
        System.out.print("Keywords:");
        String words = reader.next();
        String[] keywords = words.split(" ");
        
        //query Product Table
    }

    static void auctionProducts(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Put a product up for auction");
        System.out.print("Name: ");
        String name = reader.next();
        System.out.print("Description: ");
        String desc = reader.next();
        System.out.print("Categories: ");
        String categories = reader.next();
        do{
            System.out.print("Days for Auction: ");
            if(reader.hasNextInt()){
                int days = reader.nextInt();
                if(days > 0){
                    break;
                }
            }else{
                reader.next();
            }
        }while(true);
        
        //add product to Product Table
    }

    static void bidOnProducts(){
        Scanner reader = new Scanner(System.in);
        System.out.println("Bid on Products");
        do{
            System.out.print("Product ID: ");
            if(reader.hasNextInt()){
                int auctionID = reader.nextInt();
                if(auctionID > 0){
                    break;
                }
            }else{
                reader.next();
            }
        }while(true);
        do{
            System.out.print("Amount: ");
            if(reader.hasNextInt()){
                int amount = reader.nextInt();
                if(amount > 0){
                    break;
                }
            }else{
                reader.next();
            }
        }while(true);

        //check if product exists
        //add bid
    }

    static void sellProduct(){
        System.out.println("Selling Product");
    }

    static void suggestions(){
        System.out.println("Suggestions:");
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