package models;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ManagedBean
@ViewScoped
public class Credentials {

	// initialize properties used for the Credentials class
	@NotNull(message = "Please enter a username. This is a required field.")
	@Size(min = 3, max = 15)
	String username = "";

	@NotNull(message = "Please enter a password. This is a required field.")
	@Size(min = 3, max = 15)
	String password = "";

	@NotNull(message = "Please enter an ID. This is a required field.")
	int ID;

	// Constructor with parameters
	public Credentials(String username, String password, int ID) {
		super();
		this.username = username;
		this.password = password;
		this.ID = ID;
	}

	// Default Constructor
	public Credentials() {
		this.username = "";
		this.password = "";
		this.ID = -1;
	}

	// getters and setters for the variables
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
