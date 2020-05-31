package business;

import java.util.List;

import models.Employee;

public interface EmployeeBusinessInterface {
	
	
	public List<Employee> getEmployee();
	
	
	public Boolean onRegister(Employee employee);

	
	public Boolean onLogin(Employee employee);
	
	

}
