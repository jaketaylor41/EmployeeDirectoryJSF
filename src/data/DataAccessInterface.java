package data;

import java.util.List;


//This interface is used to implement the basic CRUD methods to the classes
//that implement it. This is used for Data Service classes.

public interface DataAccessInterface<T> {

	// Interface methods
	// Since generics are implemented, will refer to the object used as variable.

	/**
	 * This method will return a list of all variables.
	 * 
	 * @return list - List(Type T) Class (A list of all variables in the database)
	 */
	public List<T> findAll();

	/**
	 * This method will return a variable that has an ID equal to the ID that is
	 * sent.
	 * 
	 * @param id - Integer Class (ID of the variable)
	 * @return variable - T Generics (The variable corresponding to that ID)
	 */
	public T findById(int id);

	/**
	 * This method will add the variable sent onto the database.
	 * 
	 * @param t        - Variable Generics (Varible that will be written in the
	 *                 database.)
	 * @param uniqueId - Integer Class (Foreign Key of the variable.)
	 * @return Integer Class (The value that results from executeUpdate() to see if
	 *         there was actual change in the database.)
	 */
	public int create(T t, int uniqueId);

	/**
	 * This method will update the object in the database that has the same ID as
	 * the variable sent to it.
	 * 
	 * @param t - Variable Generics (Variable that will be updated in the database.)
	 * @param id - Id of the recipe that the user wants to update.
	 * @return Integer Class (The value that results from executeUpdate() to see if
	 *         there was actual change in the database.)
	 */
	public int update(T t, int id);

	/**
	 * This method will delete the variable sent from the database.
	 * 
	 * @param t - Variable Generics (Variable that will be deleted from the
	 *          database.)
	 * @return Integer Class (The value that results from executeUpdate() to see if
	 *         there was actual change in the database.)
	 */
	public int delete(T t);

	/**
	 * This method will return the fk ID of the variable sent.
	 * 
	 * @param t - Variable Generics (Variable that the user wants to find the FK
	 *          of.)
	 * @return Integer Class - (Foreign Key of the variable sent.)
	 */
	public int getUniqueId(T t);
}