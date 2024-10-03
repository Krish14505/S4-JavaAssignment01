/*********************************************************************************************************
 * File:  ListDataDaoImpl.java Course Materials CST8277
 *
 * @author Teddy Yap
 */
package databank.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.mysql.cj.protocol.ResultsetRow;

@SuppressWarnings("unused")
/**
 * Description:  API for reading list data from the database
 */
//TODO Annotate this class so that it becomes a managed bean
//TODO Provide the proper scope for this managed bean
@Named
@ApplicationScoped
public class ListDataDaoImpl implements ListDataDao, Serializable {
	/** Explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	//TODO Set the value of this string constant properly.  This is the JNDI name
	//     for the data source.
	private static final String DATABANK_DS_JNDI = "java:app/jdbc/databank";
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to retrieve the list of specialties from the database.
	private static final String READ_ALL_SPECIALTIES = "SELECT name FROM specialty ;";
	

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	//TODO Use the proper annotation here so that the correct data source object
	//     will be injected
	@Resource(lookup = "java:app/jdbc/databank")
	protected DataSource databankDS;

	protected Connection conn;
	protected PreparedStatement readAllSpecialtiesPstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection and stmts");
			conn = databankDS.getConnection();
			//TODO Initialize PreparedStatement here
			readAllSpecialtiesPstmt = conn.prepareStatement(READ_ALL_SPECIALTIES);
			
		} catch (Exception e) {
			logMsg("something went wrong getting connection from database:  " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing stmts and connection");
			//TODO Close PreparedStatement here
			readAllSpecialtiesPstmt.close();
			conn.close();
		} catch (Exception e) {
			logMsg("something went wrong closing stmts or connection:  " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<String> readAllSpecialties() {
		logMsg("reading all specialties");
		List<String> specialties = new ArrayList<>();
		//TODO Complete the retrieval of all specialties here
		//TODO Be sure to use try-and-catch statement here
		try(ResultSet rs = readAllSpecialtiesPstmt.executeQuery() ){
			while(rs.next()) {
				specialties.add(rs.getString("name")); // fetch all the name and add it to list
			}
			
		}catch(SQLException e ) {
			logMsg("something went wrong reading all specialies: " + e.getLocalizedMessage());
		}
		return specialties; //return the arrayList

	}
	
	
}
