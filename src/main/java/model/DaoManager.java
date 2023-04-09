package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class DaoManager {

	private static DaoManager instance = null;
	private Connection connection = null;

	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("User App");

	private DaoManager() {

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/bouta/workspace_j2ee/Projet_ArcelorMittal/src/main/java/database/User.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		log.info("Opened database successfully");
		
		//deleteAllUsers();
		createDatabase();
	}

	public static DaoManager getInstance() {
		if (instance == null) {
			instance = new DaoManager();
		}
		return instance;
	}

	private void createDatabase() {
		try {
			Statement stmt = connection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS User ( ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL)";

			stmt.executeUpdate(sql);
			log.info("Database created");
			stmt.close();

		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	private void deleteDatabase() {
		try {
			PreparedStatement preparedStatment = connection.prepareStatement("DROP TABLE IF EXISTS User");

			preparedStatment.execute();
			log.info("Table 'User' Deleted");
			preparedStatment.close();

		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public void addUser(String username, String password) {

		try {
			PreparedStatement preparedStatment = connection
					.prepareStatement("insert into User(USERNAME,PASSWORD) values( ? , ? )");

			preparedStatment.setString(1, username);
			preparedStatment.setString(2, password);

			preparedStatment.execute();
			preparedStatment.close();

		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public void deleteUser(String texte) {
		try {
			PreparedStatement preparedStatment = connection
					.prepareStatement("delete from User where TEXTE='" + texte + "'");

			// preparedStatment.setString(1, texte);
			preparedStatment.execute();
			log.info(texte + " deleted");
			preparedStatment.close();

		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public void updateUsers(List<User> lesUsers) {
		try {
			for(User User: lesUsers) {
			PreparedStatement preparedStatment = connection
					.prepareStatement("insert into User(TEXTE,ACTIF) values( ? , ? )");

			preparedStatment.setString(1, User.getUsername());
			preparedStatment.setString(2, User.getPassword());
			log.info(User.getUsername() + " added");
			preparedStatment.execute();
			preparedStatment.close();
			}

		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	public boolean checkUserInformations(String username, String pwd) {
		try {

			Statement st = connection.createStatement();
			String request = "select * from User where username='"+username+"' AND Password='"+pwd+"'";
			ResultSet rs = st.executeQuery(request);
			
			if(rs.next())
			{
				log.info(username + " existant en base de donnée");
				return true;
				
			}else {
				return false;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
		return false;
		
	}

	public List<User> getAllUser() {
		List<User> returnListUser = new ArrayList<User>();
		try {
			Statement statement = connection.createStatement();

			if (statement.execute("Select TEXTE,ACTIF FROM User ")) {
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					String username = resultSet.getString("TEXTE");
					String password = resultSet.getString("ACTIF");

					returnListUser.add(new User(username, password));
				}
			}
			statement.close();

		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}
		return returnListUser;
	}

}