package databank.dao;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;


@ViewScoped
public class PhysicianDTO implements Serializable, Physician {
	
	private static final long serialVersionUID = 1L;
	
	protected int id;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String phone;
	protected String specialty;
	
	@Override
	public int getId() {
		return id;
	}
	@Override
	public void setId(int id) {
		this.id = id;
		
	}
	@Override
	public String getFirstName() {
		return firstName;
	}
	@Override
	public void setFirstName(String firstname) {
		this.firstName = firstname;
		
	}
	@Override
	public String getLastName() {
		return lastName;
	}
	@Override
	public void setLastName(String lastname) {
		this.lastName = lastname;
		
	}
	@Override
	public String getEmail() {
		return email;
	}
	@Override
	public void setEmail(String email) {
		this.email = email;
		
	}
	@Override
	public String getPhone() {
		return phone;
	}
	@Override
	public void setPhone(String phone) {
		this.phone = phone;
		
	}
	@Override
	public String getSpecialty() {
		return specialty;
	}
	@Override
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
		
	}
	
}
