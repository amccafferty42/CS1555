import java.util.*;
import java.sql.*;  //import the file containing definitions for the parts
import java.text.ParseException;  //needed by java for database connection and manipulation
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Driver extends MyAuction{
    private static Connection dbcon;
    
    public static void main(String[] args) {
        String username = "", password = "";
		
        try {
            //Oracle variable MUST BE SET by sourcing bash.env or tcsh.env or the following line will not compile
            //DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
            dbcon = DriverManager.getConnection(url, username, password);

            Driver driver = new Driver();
            driver.runMethods();
        }
        catch (SQLException s) {
            System.out.print(s.toString());                
            System.exit(1);
        }
    }

    public void Driver(){

    }

    public void runMethods() throws SQLException{
        super.printProducts("Pants", 1, dbcon);
        super.printProducts("Misc", 2, dbcon);
        super.printProducts("Tools", 3, dbcon);

        super.createCustomer("a", "b", "c", "d", "e", dbcon);
        selectAllFrom("Customer");

        super.updateTime("12/25/2010 05:32:12", dbcon);
        selectAllFrom("ourSysDate");

        super.listProducts("WHERE seller = 'juice26' AND ", dbcon);

        //still needs stats

        String [] ar = {"a", "b"};
        super.keywordProducts(ar, dbcon);

        super.putUpAuction("juice26", "Steeler's Jersey", "Cleaning", "3", "washed up", "5",dbcon);
        selectAllFrom("Product");

        super.insertBid(1, "juice26", 100 ,dbcon);
        selectAllFrom("Bidlog");

        super.sellThisProduct(1, 100, true ,dbcon);
        selectAllFrom("Product");
        super.sellThisProduct(2, 100, false ,dbcon);
        selectAllFrom("Product");

        super.getSuggestions("juice26", dbcon);
    }

    public void selectAllFrom(String table) throws SQLException{
        Statement statement = dbcon.createStatement();
        String query = "SELECT * FROM " + table;
        ResultSet resultSet = statement.executeQuery(query);
        ResultSetMetaData meta = resultSet.getMetaData();
        int columns = meta.getColumnCount();

        while(resultSet.next()){
            for(int i = 1; i <= columns; i++){
                System.out.print(resultSet.getString(i) + ", ");
            }
            System.out.println();
        }
    }
}