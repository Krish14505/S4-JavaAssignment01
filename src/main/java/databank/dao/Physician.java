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
	public void setFirstName(String firstname);

	/**
	 * @return the last name of the physician
	 */
	public String getLastName();
	public void setLastName(String lastname);
	
	/**
	 * 
	 * @return email of the physician
	 */
	public String getEmail();
	public void setEmail(String email);
	
	
	/**
	 * 
	 * @return phone number of the physician
	 */
	public String getPhone();
	public void setPhone(String phone);
	
	
	/**
	 * 
	 * @return specialty of the physician
	 */
	public String getSpecialty();
	public void setSpecialty(String specialty);
	
	
	
	
	
}
