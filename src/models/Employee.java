package models;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ManagedBean
@ViewScoped
public class Employee {

	// initialize properties used for the employee class
	@NotNull(message = "Please enter a First Name. This is a required field.")
	@Size(min = 1, max = 15)
	String firstName = "";

	@NotNull(message = "Please enter a Last Name. This is a required field.")
	@Size(min = 1, max = 15)
	String lastName = "";

	@NotNull(message = "Please enter an email. This is a required field.")
	@Size(min = 5, max = 30)
	String email = "";

	@NotNull(message = "Please enter a phone number. This is a required field.")
	@Size(min = 10, max = 30)
	String phoneNumber = "";

	@NotNull(message = "Please enter an ID. This is a required field.")
	private int ID;

	Credentials credentials = new Credentials();

	// Constructor
	public Employee(int ID, String firstName, String lastName, String email, String phoneNumber, String userName,
			String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.credentials.setUsername(userName);
		this.credentials.setPassword(password);
		this.ID = ID;
	}

	// For test purposes only
	public Employee() {
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.phoneNumber = "";
		this.credentials.setUsername("");
		this.credentials.setPassword("");
		this.ID = -1;

	}

	// getters and setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
