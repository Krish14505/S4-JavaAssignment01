/*********************************************************************************************************
 * File:  PhysicianController.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.jsf;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.SessionMap;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import databank.dao.ListDataDao;
import databank.dao.PhysicianDao;
import databank.model.PhysicianPojo;

/**
 * Description:  Responsible for collection of Physician Pojo's in XHTML (list) <h:dataTable> </br>
 * Delegates all C-R-U-D behavior to DAO
 */

//TODO Don't forget this is a managed bean with a session scope
@Named
@SessionScoped
public class PhysicianController implements Serializable {
	private static final long serialVersionUID = 1L;

	//TODO Use the proper annotations here so that this session map object will be 
	//     injected.  Please refer to Week 3 sample project to know how this is to be done. 
	@Inject
	@SessionMap
	private Map<String, Object> sessionMap;

	@Inject
	private PhysicianDao physicianDao;

	@Inject
	private ListDataDao listDataDao;

	private List<PhysicianPojo> physicians;

	//Necessary methods to make controller work

	public List<PhysicianPojo> getPhysicians() {
		return physicians;
	}

	public void setPhysicians(List<PhysicianPojo> physicians) {
		this.physicians = physicians;
	}

	public void loadPhysicians() {
		setPhysicians(physicianDao.readAllPhysicians());
	}



	public List<String> getSpecialties() {
		return this.listDataDao.readAllSpecialties();
	}
	
	public String navigateToAddForm() {
		//Pay attention to the name here, it will be used as the object name in add-physician.xhtml
		//ex. <h:inputText value="#{newPhysician.firstName}" id="firstName" />
		sessionMap.put("newPhysician", new PhysicianPojo());
		return "add-physician.xhtml?faces-redirect=true";
	}

	public String submitButtonLabel(PhysicianPojo physician) {
		//TODO Update the physician object with current date here.  You can use LocalDateTime::now().
		physician.setCreated(LocalDateTime.now());
		
		//TODO Use DAO to insert the physician to the database
		physicianDao.createPhysician(physician);
		//TODO Do not forget to navigate the user back to list-physicians.xhtml
		return "list-physicians.xhtml?faces-redirect=true";
	}

	public String navigateToUpdateForm(int physicianId) {
		//TODO Use DAO to find the physician object from the database first
		PhysicianPojo physician = physicianDao.readPhysicianById(physicianId);
		//TODO Use session map to keep track of of the object being edited
		sessionMap.put("currentPhysician", physician);
		
		//TODO Do not forget to navigate the user to the edit/update form
		return "edit-physician.xhtml?faces-redirect=true";
	}

	public String submitUpdatedPhysician(PhysicianPojo physician) {
		//TODO Use DAO to update the physician in the database
		physicianDao.updatePhysician(physician);
		//TODO Do not forget to navigate the user back to list-physicians.xhtml
		
		return "list-physicians.xhtml?faces-redirect=true";
	}

	public String deletePhysician(int physicianId) {
		//TODO Use DAO to delete the physician from the database
		physicianDao.deletePhysicianById(physicianId);
		//TODO Do not forget to navigate the user back to list-physicians.xhtml
		return "list-physicians.xhtml?faces-redirect=true";
	}

}
