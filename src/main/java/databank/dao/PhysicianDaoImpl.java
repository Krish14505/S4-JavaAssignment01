/*********************************************************************************************************
 * File:  PhysicianDaoImpl.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import javax.sql.DataSource;

import databank.model.PhysicianPojo;

@SuppressWarnings("unused")
/**
 * Description:  Implements the C-R-U-D API for the database
 */
//TODO Don't forget this is a managed bean with an application scope

@Named
@ApplicationScoped
public class PhysicianDaoImpl implements PhysicianDao, Serializable {
	/** Explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	//TODO Set the value of this string constant properly.  This is the JNDI name
	//     for the data source.
	private static final String DATABANK_DS_JNDI = "java:app/jdbc/databank";
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to retrieve the list of physicians from the database.
	private static final String READ_ALL = "SELECT * FROM physician;";
	//TODO Set the value of this strincaht constant properly.  This is the SQL
	//     statement to retrieve a physician by ID from the database.
	private static final String READ_PHYSICIAN_BY_ID = "SELECT * FROM physician WHERE id = ? ";
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to insert a new physician to the database.
	private static final String INSERT_PHYSICIAN = "INSERT INTO physician (first_name,last_name,email,phone,specialty,created) VALUES (?,?,?,?,?,now())";
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to update the fields of a physician in the database.
	private static final String UPDATE_PHYSICIAN_ALL_FIELDS = "UPDATE physician SET first_name=?,last_name=?,email=?,phone=?,specialty=? WHERE id=?";
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to delete a physician from the database.
	private static final String DELETE_PHYSICIAN_BY_ID = "DELETE FROM physician WHERE id = ?";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	//TODO Use the proper annotation here so that the correct data source object
	//     will be injected
	@Resource(lookup=DATABANK_DS_JNDI)
	protected DataSource databankDS;

	protected Connection conn;
	protected PreparedStatement readAllPstmt;
	protected PreparedStatement readByIdPstmt;
	protected PreparedStatement createPstmt;
	protected PreparedStatement updatePstmt;
	protected PreparedStatement deleteByIdPstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection and stmts");
			conn = databankDS.getConnection();
			readAllPstmt = conn.prepareStatement(READ_ALL);
			createPstmt = conn.prepareStatement(INSERT_PHYSICIAN, RETURN_GENERATED_KEYS);
			//TODO Initialize other PreparedStatements here
			readByIdPstmt = conn.prepareStatement(READ_PHYSICIAN_BY_ID);
			updatePstmt = conn.prepareStatement(UPDATE_PHYSICIAN_ALL_FIELDS);
			deleteByIdPstmt = conn.prepareStatement(DELETE_PHYSICIAN_BY_ID);
					
		} catch (Exception e) {
			logMsg("something went wrong getting connection from database:  " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing stmts and connection");
			readAllPstmt.close();
			createPstmt.close();
			//TODO Close other PreparedStatements here
			readByIdPstmt.close();
			updatePstmt.close();
			deleteByIdPstmt.close();
			conn.close();
			
		} catch (Exception e) {
			logMsg("something went wrong closing stmts or connection:  " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<PhysicianPojo> readAllPhysicians() {
		logMsg("reading all physicians");
		List<PhysicianPojo> physicians = new ArrayList<>();
		try (ResultSet rs = readAllPstmt.executeQuery();) {

			while (rs.next()) {
				PhysicianPojo newPhysician = new PhysicianPojo();
				newPhysician.setId(rs.getInt("id"));
				newPhysician.setLastName(rs.getString("last_name"));
				//TODO Complete the physician initialization here
				newPhysician.setFirstName(rs.getString("first_name"));
				newPhysician.setEmail(rs.getString("email"));
				newPhysician.setPhoneNumber(rs.getString("phone"));
				newPhysician.setSpecialty(rs.getString("specialty"));
				physicians.add(newPhysician);
			}
			
		} catch (SQLException e) {
			logMsg("something went wrong accessing database:  " + e.getLocalizedMessage());
		}
		
		return physicians;

	}

	@Override
	public PhysicianPojo createPhysician(PhysicianPojo physician) {
		logMsg("creating a physician");
		//TODO Complete the insertion of a new physician here
		//TODO Be sure to use try-and-catch statement
		try {
			createPstmt.setString(1, physician.getFirstName());
			createPstmt.setString(2, physician.getLastName());
			createPstmt.setString(3, physician.getEmail());
			createPstmt.setString(4, physician.getPhoneNumber());
			createPstmt.setString(5, physician.getSpecialty());
			createPstmt.executeUpdate();
			
		}catch(SQLException e) {
			logMsg("something went wrong creating physician"+ e.getLocalizedMessage());
		}
		return physician;
	}

	@Override
	public PhysicianPojo readPhysicianById(int physicianId) {
		logMsg("read a specific physician");
		//TODO Complete the retrieval of a specific physician by its id here
		//TODO Be sure to use try-and-catch statement
		PhysicianPojo physician = null;
		
		try {
			readByIdPstmt.setInt(1, physicianId);
			ResultSet rs = readByIdPstmt.executeQuery();
			
			if(rs.next()) {
				physician = new PhysicianPojo();
				physician.setId(rs.getInt("id"));
				physician.setFirstName(rs.getString("first_name"));
				physician.setLastName(rs.getString("last_name"));
				physician.setEmail(rs.getString("email"));
				physician.setPhoneNumber(rs.getString("phone"));
				physician.setSpecialty(rs.getString("specialty"));
			}
			
		}catch(SQLException e){
			logMsg("something went wrong while retrieving physician by id: "+ e.getLocalizedMessage());
		}
		
		
		return physician;
	}

	@Override
	public void updatePhysician(PhysicianPojo physician) {
		logMsg("updating a specific physician");
		//TODO Complete the update of a specific physician here
		//TODO Be sure to use try-and-catch statement
		try {
			updatePstmt.setString(1, physician.getFirstName());
			updatePstmt.setString(2, physician.getLastName());
			updatePstmt.setString(3, physician.getEmail());
			updatePstmt.setString(4, physician.getPhoneNumber());
			updatePstmt.setString(5, physician.getSpecialty());
			updatePstmt.setInt(6, physician.getId());
			updatePstmt.executeUpdate();
			
		}catch(SQLException exception) {
			logMsg("something went wrong when updating physician: "+ exception.getLocalizedMessage());
		}
		
	}

	@Override
	public void deletePhysicianById(int physicianId) {
		logMsg("deleting a specific physician");
		
		try {
			deleteByIdPstmt.setInt(1, physicianId);
			deleteByIdPstmt.executeUpdate();
			
		} catch (SQLException e) {
			logMsg("something went wrong when deleting physician: " + e.getLocalizedMessage());
		}
		
	}

}