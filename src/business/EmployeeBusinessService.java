package business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import data.DataAccessInterface;
import models.Credentials;
import models.Employee;

//This is the business service for the Employee model, all the logic that connects
//the controller and the database is used in here.

@Stateless
@Local(EmployeeBusinessInterface.class)
@Alternative
public class EmployeeBusinessService implements EmployeeBusinessInterface {

	// Initialize the User list
	private List<Employee> employees;

	// Inject the dataSerice
	@Inject
	DataAccessInterface<Employee> employeeDataService;

	/**
	 * Default Constructor, creates an instance of a list of type Employee
	 */
	public EmployeeBusinessService() {
		employees = new ArrayList<Employee>();
	}

	/**
	 * This method will use comparing logic and return true or false if the
	 * credentials that were sent equal to the credentials in the database.
	 * 
	 * @param employee - Employee Class - (Employee that will be used in the login
	 *             authentication logic.)
	 * @return Boolean Class - (Boolean value depending on the result of the
	 *         dataService.)
	 */
	public Boolean onLogin(Employee employee) {
		// create session
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// test if Employee name is passed correctly
		Boolean validate = false;

		// get list of employees from database
		employees = employeeDataService.findAll();

		// validate information
		for (int i = 0; i < employees.size(); i++) {
			if (employee.getCredentials().getUsername().equals(employees.get(i).getCredentials().getUsername())
					&& employee.getCredentials().getPassword().equals(employees.get(i).getCredentials().getPassword())) {
				Credentials c = new Credentials(employees.get(i).getFirstName(), employees.get(i).getLastName(),
						employees.get(i).getID());
				employee.setCredentials(c);
				session.setAttribute("userCred", c);
				employee.setFirstName(employees.get(i).getFirstName());
				employee.setLastName(employees.get(i).getLastName());
				employee.setEmail(employees.get(i).getEmail());
				employee.setID(employees.get(i).getID());
				employee.setPhoneNumber(employees.get(i).getPhoneNumber());
				validate = true;
				break;
			}
		}
		return validate;
	}

	/**
	 * This method will register(write) a Employee to the database.
	 * 
	 * @param employee - Employee Class - (Employee that will be added to the database.)
	 * @return Boolean Class - (Boolean value depending on the result of the
	 *         dataService.)
	 */
	public Boolean onRegister(Employee employee) {
		// get the employee
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("employee", employee);
		// call the dataService to create a employee
		if (employeeDataService.create(employee, -1) == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method will return a list of all the employees.
	 * 
	 * @return list - List(Type Employee) Class (List of all employee in the database.)
	 */
	@Override
	public List<Employee> getEmployee() {
		return employeeDataService.findAll();
	}
}
