package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import model.User;

public class DaoManager {

	private static DaoManager instance = null;
	private Connection connection = null;

	private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("User App");

	private DaoManager() {

		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(
					"jdbc:sqlite:C:/Users/bouta/workspace_j2ee/Projet_ArcelorMittal/src/main/java/database/Users.db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		log.info("Opened database successfully");

		//deleteDatabase();
		createDatabase();
		//create4Test();
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
			String sql = "CREATE TABLE IF NOT EXISTS Users ( ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT NOT NULL, PASSWORD TEXT NOT NULL, OCCUPATION TEXT NOT NULL)";

			stmt.executeUpdate(sql);
			log.info("Database created");
			stmt.close();

		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	private void deleteDatabase() {
		try {
			PreparedStatement preparedStatment = connection.prepareStatement("DROP TABLE IF EXISTS Users");

			preparedStatment.execute();
			log.info("Table 'Users' Deleted");
			preparedStatment.close();

		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public void addUser(String username, String password, String occupation) {

		try {
			PreparedStatement preparedStatment = connection
					.prepareStatement("insert into Users(USERNAME,PASSWORD,OCCUPATION) values( ? , ? , ? )");

			preparedStatment.setString(1, username);
			preparedStatment.setString(2, password);
			preparedStatment.setString(3, occupation);

			preparedStatment.execute();
			preparedStatment.close();

		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public void deleteUser(String username) {
		try {
			PreparedStatement preparedStatment = connection
					.prepareStatement("delete from Users where TEXTE='" + username + "'");

			// preparedStatment.setString(1, texte);
			preparedStatment.execute();
			log.info(username + " deleted");
			preparedStatment.close();

		} catch (Exception e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}
	}

	public void updateUsers(List<User> lesUsers) {
		try {
			for (User User : lesUsers) {
				PreparedStatement preparedStatment = connection
						.prepareStatement("insert into Users(TEXTE,ACTIF) values( ? , ? )");

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
			String request = "select * from Users where username='" + username + "' AND Password='" + pwd + "'";
			ResultSet rs = st.executeQuery(request);

			if (rs.next()) {
				log.info(username + " existant en base de donnée");
				return true;

			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return false;

	}

	public List<User> getAllUser() {
		List<User> returnListUser = new ArrayList<User>();
		try {
			Statement statement = connection.createStatement();

			if (statement.execute("Select TEXTE,ACTIF FROM Users ")) {
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

	private void create4Test() {

		System.out.println("Users added to the database successfully!");

		// Close the PreparedStatement and Connection objects
		try {
			// Prepare SQL statement to insert data into the 'users' table
			String insertQuery = "INSERT INTO Users (USERNAME, PASSWORD, OCCUPATION) VALUES (?, ?, ?)";
			PreparedStatement pstmt = connection.prepareStatement(insertQuery);

			// Add first user
			pstmt.setString(1, "mohammed");
			pstmt.setString(2, "password123");
			pstmt.setString(3, "admin");
			pstmt.executeUpdate();

			// Add second user
			pstmt.setString(1, "safaa");
			pstmt.setString(2, "password123");
			pstmt.setString(3, "admin");
			pstmt.executeUpdate();

			// Add third user
			pstmt.setString(1, "ihssane");
			pstmt.setString(2, "password123");
			pstmt.setString(3, "user");
			pstmt.executeUpdate();
			log.info("3 users added for test");

			pstmt.close();
		} catch (SQLException e) {
			log.error(e.getClass().getName() + ": " + e.getMessage());
		}

	}

}