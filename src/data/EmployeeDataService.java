package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import exception.DataServiceException;
import models.Employee;

@Stateless
@Local(DataAccessInterface.class)
@LocalBean
public class EmployeeDataService implements DataAccessInterface<Employee> {

	// Instantiate connection code
	Connection conn = null;
	String url = "jdbc:mysql://localhost:8889/employeeDirectory";
	String username = "root";
	String password = "root";

	/**
	 * This method will return a list of all the employees in the database. A SQL
	 * statement will join the Credential and Employee table in order to grab user name
	 * and Password as well.
	 *
	 * @return list - List(Type Employee) Class (List of all employees from the database.)
	 */
	@Override
	public List<Employee> findAll() {
		// create SQL statement to grab all the employees from the database
		String sql = "SELECT users.ID, users.FIRSTNAME, users.LASTNAME, users.EMAIL, users.PHONENUMBER,credentials.USERNAME, credentials.PASSWORD"
				+ " FROM credentials" + " INNER JOIN users ON users.credentials_ID = credentials.ID;";
		// create a list of type employees
		List<Employee> employees = new ArrayList<Employee>();

		try {
			// connect to the database
			conn = DriverManager.getConnection(url, username, password);
			// create a statement
			Statement stmt = conn.createStatement();
			// run the SQL statement and store the result set
			ResultSet rs = stmt.executeQuery(sql);
			// loop through the result set, create a new Employee based on the information in
			// the result set and add it to the list
			while (rs.next()) {
				employees.add(new Employee(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
						rs.getString("EMAIL"), rs.getString("PHONENUMBER"), rs.getString("USERNAME"),
						rs.getString("PASSWORD")));
			}
		} catch (SQLException e) {
			// print stack trace
			e.printStackTrace();
			// throw custom exception
			throw new DataServiceException(e);
		} finally {
			if (conn != null) {
				try {
					// close connection
					conn.close();
				} catch (SQLException e) {
					// print stack trace
					e.printStackTrace();
				}
			}
		}
		return employees;
	}

	/**
	 * This method will return a specific user from the database. It will select a
	 * employee from the users table where ID is equal to the ID sent to the method.
	 * 
	 * @param id - Integer Class (ID of the user that will be returned.)
	 * @return Employee Class - (User that corresponds to the ID sent.)
	 */
	@Override
	public Employee findById(int id) {
		String sql = String.format("SELECT * FROM `users` WHERE `users`.`ID` = %d", id);
		Employee returnUser = null;

		try {
			// connect to the database
			conn = DriverManager.getConnection(url, username, password);
			// create a statement
			Statement stmt = conn.createStatement();
			// run the SQL statement and store the result set
			ResultSet rs = stmt.executeQuery(sql);
			// loop through the result set, create a new Employee based on the information in
			// the result set and add it to the list
			while (rs.next()) {
				returnUser = new Employee(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
						rs.getString("EMAIL"), rs.getString("PHONENUMBER"), rs.getString("USERNAME"),
						rs.getString("PASSWORD"));
			}
		} catch (SQLException e) {
			// print stack trace
			e.printStackTrace();
			// throw custom exception
			throw new DataServiceException(e);
		} finally {
			if (conn != null) {
				try {
					// close connection
					conn.close();
				} catch (SQLException e) {
					// print stack trace
					e.printStackTrace();
				}
			}
		}
		return returnUser;
	}

	/**
	 * This method will write a Employee to the database. First it will write into the
	 * credentials table, grab the generated credentials ID in order to write a
	 * user.
	 * 
	 * @param user     - Employee Class (User model that will be added to the database)
	 * @return Integer Class (The value that results from executeUpdate() to see if
	 *         there was actual change in the database.)
	 */

	@Override
	public int create(Employee employee, int uniqueId) {
		// create SQL statement to insert into the credentials table
		String sqlCredentials = String.format("INSERT INTO credentials(USERNAME, PASSWORD) VALUES('%s', '%s')",
				employee.getCredentials().getUsername(), employee.getCredentials().getPassword());
		// create SQL statement to get the credential ID
		String sqlGetCredentialID = String.format(
				"SELECT `credentials`.`ID` FROM `credentials` WHERE `credentials`.`USERNAME` = '%s';",
				employee.getCredentials().getUsername());
		// create rowsAffected and credentialId integers
		int rowsAffected = -1;
		int credentialId = -1;

		try {
			// connect to database
			conn = DriverManager.getConnection(url, username, password);
			// create statement
			Statement stmt = conn.createStatement();
			// Run the SQL code
			rowsAffected = stmt.executeUpdate(sqlCredentials);
			ResultSet rs = stmt.executeQuery(sqlGetCredentialID);
			while (rs.next()) {
				credentialId = rs.getInt("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataServiceException(e);
		}

		// create SQL statement to add to users
		String sqlUsers = String.format(
				"INSERT INTO users(FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER, credentials_ID) VALUES('%s', '%s', '%s', '%s', %d)",
				employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPhoneNumber(), credentialId);

		try {
			// connect to database
			conn = DriverManager.getConnection(url, username, password);
			// create statement
			Statement stmt = conn.createStatement();
			rowsAffected = stmt.executeUpdate(sqlUsers);

		} catch (SQLException e) {
			// print stack trace
			e.printStackTrace();
			// throw custom exception
			throw new DataServiceException(e);
		} finally {
			if (conn != null) {
				try {
					// close connection
					conn.close();
				} catch (SQLException e) {
					// print stack trace
					e.printStackTrace();
				}
			}
		}
		return rowsAffected;
	}

	// This method will be implemented later once we have admin privileges
	@Override
	public int update(Employee t, int id) {
		// Use logic from update() in recipedataservice
		return -1;
	}

	// This method will be implemented later once we have admin privileges
	@Override
	public int delete(Employee t) {
		// Use logic from delete() in recipedataservice
		return -1;
	}

	// This method will be implemented later once we have admin privileges
	@Override
	public int getUniqueId(Employee t) {
		// TODO Auto-generated method stub
		return -1;
	}

}
