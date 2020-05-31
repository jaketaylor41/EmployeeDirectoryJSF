package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import business.EmployeeBusinessInterface;
import models.Credentials;
import models.Employee;

//This is the controller class for the User where it handles the front end page
//distribution, the methods in this class accept (or don't accept) data and
//based on the outcome the methods will return a string of the page it is
//directed.

@ManagedBean
@ViewScoped
public class EmployeeController{

	// inject beans
	@Inject
	EmployeeBusinessInterface employeeService;

	/**
	 * This method will grab the information from the register form and store it in
	 * a employee model so it can be displayed in the RegistrationSuccessful form.
	 * 
	 * @param employee - Employee class (The employee model that is being registered to the
	 *             web-site which will be added to the database)
	 * @return String Class - (Name of the page that the web-site will direct to.)
	 */
	public String onRegister(Employee employee) {
		try {
			// call business service to register and store outcome in a boolean object
			boolean outcome = employeeService.onRegister(employee);
			// if outcome true go to success/ false unsuccessful
			if (outcome) {
				return "registrationSuccessful.xhtml";
			} else {
				return "unsuccsessfulLogin.xhtml";
			}
			// if there is a database error catch it with custom exception
		} catch (Exception e) {
			return "error.xhtml";
		}
	}

	/**
	 * This method will grab the information from the login form, it will create a
	 * employee model with the inputed username and password will compare to username
	 * and password in the database and if the inputed one equals it it will display
	 * a welcome message on LoginSuccessful form.
	 * 
	 * @param employee - Employee Class (User model that is going to go through the
	 *             authentication logic.)
	 * @return String Class - (Name of the page that the web-site will direct to.)
	 */
	public String onLogin(Employee employee) {

		// Create session variable
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// put employee on the page
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("employee", employee);

		try {
			// login employee using the businessService
			if (employeeService.onLogin(employee) == true) {
				// set employee credentials to the user
				session.setAttribute("userCred", employee.getCredentials());
				Credentials c = (Credentials) session.getAttribute("userCred");
				System.out.println("in Employee Controller : onLogin() employee: " + c.getUsername());
				if (c.getID() == 2) {
					return "homeAdmin.xhtml";
				} else {
					return "successfulLogin.xhtml";
				}
			} else {
				// display unsuccessful page
				return "unsuccsessfulLogin.xhtml";
			}
			// if there is a database error catch it with custom exception
		} catch (Exception e) {
			return "error.xhtml";
		}

	}

	/**
	 * This method is called to end the session variable (this will be important
	 * later when I implement the logic so a user cannot access a page by simply
	 * putting in a URL)
	 * 
	 * @return String - Path to login page.
	 */
	public String onLogout() {
		/*
		 * // Create session variable HttpSession session = (HttpSession)
		 * FacesContext.getCurrentInstance().getExternalContext().getSession(false); //
		 * set the credentials in session to null session.setAttribute("userCred",
		 * null);
		 */
		return "loginForm.xhtml";
	}

	/**
	 * This is a method to go to register page
	 * 
	 * @return String Class - (Name of the page that the web-site will direct to.)
	 */
	public String onSubmitRegister() {
		return "registrationForm.xhtml";
	}

	/**
	 * This is a method to go to the login page
	 * 
	 * @return String Class - (Name of the page that the web-site will direct to.)
	 */
	public String onSubmitLogin() {
		System.out.println("In onSubmitLogin()");
		return "loginForm.xhtml";
	}

	/**
	 * This method will return the employee business interface once called.
	 * 
	 * @return EmployeeBusinessInterface Class - (Business Service)
	 */
	public EmployeeBusinessInterface getEmployeeService() {
		return employeeService;
	}
}