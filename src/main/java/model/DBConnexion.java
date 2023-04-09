package model;
import org.h2.jdbcx.JdbcDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/*public class DBConnexion {
	
	Connection dbConnection;
	
	public DBConnexion() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:tcp://localhost/~/test");
		dataSource.setUser("sa");
		dataSource.setPassword("sa");
		
		try {
			this.dbConnection=dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	
	public Connection getDbConnection() {
		return dbConnection;
	}


	public void setDbConnection(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

	
}
*/

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement;  

public class DBConnexion { 
   // JDBC driver name and database URL 
   static final String JDBC_DRIVER = "org.h2.Driver";   
   static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";  
   
   //  Database credentials 
   static final String USER = "sa"; 
   static final String PASS = "sa"; 
    
   
   
   private Statement DBConnexion() {
	  Connection conn = null; 
	  Statement stmt = null; 
      try { 
         // STEP 1: Register JDBC driver 
         Class.forName(JDBC_DRIVER); 
         
         // STEP 2: Open a connection 
         System.out.println("Connecting to database..."); 
         conn = DriverManager.getConnection(DB_URL,USER,PASS);  
         
         // STEP 3: Execute a query 
         System.out.println("Connected database successfully..."); 
         stmt = conn.createStatement(); 
         String sql = "SELECT * FROM Users"; 
         ResultSet rs = stmt.executeQuery(sql); 
         
         // STEP 4: Extract data from result set 
         while(rs.next()) { 
            // Retrieve by column name 
            String first = rs.getString(1); 
            String last = rs.getString(2);  
            
            // Display values 
            System.out.print("First: " + first); 
            System.out.println(", Last: " + last); 
         } 
         // STEP 5: Clean-up environment 
         rs.close(); 
      } catch(SQLException se) { 
         // Handle errors for JDBC 
         se.printStackTrace(); 
      } catch(Exception e) { 
         // Handle errors for Class.forName 
         e.printStackTrace(); 
      } finally { 
         // finally block used to close resources 
         try { 
            if(stmt!=null) stmt.close();  
         } catch(SQLException se2) { 
         } // nothing we can do 
         try { 
            if(conn!=null) conn.close(); 
         } catch(SQLException se) { 
            se.printStackTrace(); 
         } // end finally try 
      } // end try 
      System.out.println("Goodbye!"); 
      return stmt;
   }
}
