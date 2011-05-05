package test;

import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import dataSource.DBFacade;
import dataSource.UsersMapper;
import domain.Users;
import junit.framework.TestCase;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Test class tests the UsersMapper class.
 */
public class UsersMapperTest extends TestCase {

	Connection con;
	private String id = "knjofr8";
	private String pw = "John";
	UsersMapper um;

	@Before
	protected void setUp() {
		getConnection();
		Fixture.setUp(con);
		um = new UsersMapper();
	}

	@After
	protected void tearDown() {
		releaseConnection();
	}

	@Test
	public void testGetUserMatch() {
		Users u = um.getUser(2, con);
		assertTrue("Fail in GetUserMatch 1", u != null); // return value not
		// null?
		assertTrue("Fail in GetUserMatch 2", u.getUserId() == 2); // return
		// value OK?
	}

	@Test
	public void testGetUserNotMatch() {
		Users u = um.getUser(0, con);
		assertTrue("Fail in GetUserNotMatch", u == null); // return value null?
	}
	
	@Test
	public void testGetTaskSolverGroupMatch () {
		
	}

	/*
	 * public void testSaveNewUser() { assertTrue(db.saveNewUser(testUser)); }
	 * 
	 * public void testSaveNewTaskSolverGroup() {
	 * assertTrue(db.saveNewTaskSolverGroup(testTaskSolverGroup)); }
	 * 
	 * public void testGetUser() {
	 * assertEquals(db.getUser(testUser.getUserId()).getName(),
	 * testUser.getName()); }
	 * 
	 * public void testGetTaskSolverGroup() { assertEquals(
	 * db.getTaskSolverGroup(testTaskSolverGroup.getSubtaskBidId()),
	 * testTaskSolverGroup); }
	 */
	// === Connection specifics
	private void getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@delfi.lyngbyes.dk:1521:LUC", id, pw);
		} catch (Exception e) {
			System.out.println("fail in getConnection()");
			System.out.println(e);
		}
	}

	public void releaseConnection() {
		try {
			con.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
