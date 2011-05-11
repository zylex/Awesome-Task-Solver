package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import dataSource.DBFacade;
import dataSource.UsersMapper;
import domain.TaskSolverGroup;
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
	public void testGetTaskSolverGroupMatch() {
		TaskSolverGroup tsg = um.getTaskSolverGroup(18000111, con);
		assertTrue("Fail in GetTaskSolverGroupMatch 1", tsg != null); // return
																		// value
																		// not
																		// null?
		assertTrue("Fail in GetTaskSolverGroupMatch 2", tsg.getUsers().get(1)
				.getUserId() == 2); // return value OK?
	}

	@Test
	public void testGetTaskSolverGroupNotMatch() {
		TaskSolverGroup tsg = um.getTaskSolverGroup(0, con);
		assertTrue("Fail in GetTaskSolverGroupNotMatch", tsg == null); // return
																		// value
																		// null?
	}

	@Test
	public void testSaveNewUserTrue() {
		Users u = new Users(1, 1, "testSaveNewUserTrue", "XXX", "USA");
		boolean success = um.saveNewUser(u, con);
		assertTrue("Fail in SaveNewUserTrue", success); // save successful?
	}

	@Test
	public void testSaveNewUserFail() {
		Users u = null;
		boolean success = um.saveNewUser(u, con);
		assertTrue("Fail in SaveNewUserFail", !success); // save failed?
	}

	@Test
	public void testSaveNewTaskSolverGroupTrue() {
		ArrayList<Users> u = new ArrayList<Users>(0);
		u.add(new Users(5, 25, "testSaveNewTaskSolverGroupTrue", "XXX", "USA"));
		TaskSolverGroup tsg = new TaskSolverGroup(u);
		boolean success = um.saveNewTaskSolverGroup(18000111, tsg, con);
		assertTrue("Fail in SaveNewTaskSolverGroupTrue", !success); // save
																	// successful?
	}

	@Test
	public void testSaveNewTaskSolverGroupFail() {
		TaskSolverGroup tsg = null;
		boolean success = um.saveNewTaskSolverGroup(18000111, tsg, con);
		assertTrue("Fail in SaveNewTaskSolverGroupFail", !success); // save
																	// failed?
	}

	@Test
	public void testGetTaskOwnerTrue() {
		Users u = um.getTaskOwner(80001, con);
		assertTrue("Fail in GetTaskOwnerMatch 1", u != null); // return value
																// not null?
		assertTrue("Fail in GetTaskOwnerMatch 2", u.getUserId() == 2); // return
																		// value
																		// OK?
	}

	@Test
	public void testGetTaskOwnerFail() {
		Users u = um.getTaskOwner(0, con);
		assertTrue("Fail in GetTaskOwner", u == null); // return value null?
	}

	@Test
	public void testGetTaskManagerByTaskBidIdMatch() {
		Users u = um.getTaskManagerByTaskBidId(040001, con);
		assertTrue("Fail in GetTaskManagerByTaskBidIdMatch 1", u != null); // return
																			// value
																			// not
																			// null?
		assertTrue("Fail in GetTaskManagerByTaskBidIdMatch 2",
				u.getUserId() == 2); // return value OK?
	}

	@Test
	public void testGetTaskManagerByTaskBidIdFail() {
		Users u = um.getTaskManagerByTaskBidId(0, con);
		assertTrue("Fail in GetTaskManagerByTaskBidIdFail", u == null); // return
																		// value
																		// null?
	}

	@Test
	public void testGetTaskManagerBySubtaskIdMatch() {
		Users u = um.getTaskManagerBySubtaskId(800011, con);
		assertTrue("Fail in GetTaskManagerBySubtaskIdMatch 1", u != null); // return
																			// value
																			// not
																			// null?
		assertTrue("Fail in GetTaskManagerBySubtaskIdMatch 2",
				u.getUserId() == 2); // return value OK?
	}

	@Test
	public void testGetTaskManagerBySubtaskIdNotMatch() {
		Users u = um.getTaskManagerBySubtaskId(0, con);
		assertTrue("Fail in GetTaskManagerBySubtaskIdNotMatch", u == null); // return
																			// value
																			// null?
	}

	@Test
	public void testGetAllUsers() {
		ArrayList<Users> u = um.getAllUsers(con);
		assertTrue("Fail in GetAllUsers 1", u != null); // return value not
														// null?
		assertTrue("Fail in GetAllUsers 2", u.get(1).getUserId() == 2); // return
																		// value
																		// OK?
	}

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
