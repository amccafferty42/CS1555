import java.util.Scanner;
import java.sql.*;  //import the file containing definitions for the parts
import java.text.ParseException;  //needed by java for database connection and manipulation

public class MyAuction {

    public static void main(String [] args){
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

    static void login(int input){
        Scanner reader = new Scanner(System.in);
        String username, password;
        boolean badLogin = true;
        do{
            System.out.print("Username: ");
            username = reader.next();
            System.out.print("Password: ");
            password = reader.next();
            if(input == 1){
                //if(check if admin exists in db){
                    adminInterface();
                //}else{
                    //System.out.println("Admin does not exist");
                //}
                badLogin = false;
            }else{
                //if(check if admin exists in db){
                    customerInterface();
                //}else{
                    //System.out.println("Customer does not exist");
                //}
                badLogin = false;
            }
        }while(badLogin);
        
    }

    static void adminInterface(){
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

    static void registerCustomer(){
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

        //add to Customer Table
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