package databank.dao;

import java.util.Set;

public interface Physician {
	/**
	 * 
	 * @return the primary key of physician
	 */
	public int getId();
	public void setId(int id);
	
	/**
	 * 
	 * @return the first name of the physician
	 */
	public String getFirstName();
	public String setFirstName(String firstname);

	/**
	 * @return the last name of the physician
	 */
	public String getLastName();
	public String setLastName(String lastname);
	
	/**
	 * 
	 * @return email of the physician
	 */
	public String getEmail();
	public String setEmail(String email);
	
	
	/**
	 * 
	 * @return phone number of the physician
	 */
	public String getPhone();
	public String setPhone(String phone);
	
	
	
	
	
}
