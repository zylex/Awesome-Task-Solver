package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataSource.SubtaskAuctionMapper;
import dataSource.UsersMapper;
import domain.Subtask;
import domain.SubtaskAuction;
import domain.SubtaskBid;
import domain.TaskSolverGroup;
import domain.Users;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Test class tests the SubtaskAuctionMapper class.
 */
public class SubtaskAuctionMapperTest extends TestCase {

	Connection con;
	private String id = "knjofr8";
	private String pw = "John";
	SubtaskAuctionMapper sam;

	@Before
	public void setUp() throws Exception {
		getConnection();
		Fixture.setUp(con);
		sam = new SubtaskAuctionMapper();
	}

	@After
	public void tearDown() throws Exception {
		releaseConnection();
	}

	@Test
	public void testGetSubtaskMatch() {
		Subtask s = sam.getSubtask(800011, con);
		assertTrue("Fail in GetSubtaskMatch 1", s != null); // return value not
															// null?
		assertTrue("Fail in GetSubtaskMatch 2", s.getSubtaskId() == 800011); // return
																				// value
																				// OK?
	}

	@Test
	public void testGetSubtaskNotMatch() {
		Subtask s = sam.getSubtask(0, con);
		assertTrue("Fail in GetSubtaskNotMatch", s == null); // return value
																// null?
	}

	@Test
	public void testGetSubtaskBidByIdMatch() {
		SubtaskBid sb = sam.getSubtaskBidById(18000111, con);
		assertTrue("Fail in GetSubtaskBidByIdMatch 1", sb != null); // return
																	// value
																	// not null?
		assertTrue("Fail in GetSubtaskBidByIdMatch 2",
				sb.getSubtaskBidId() == 18000111); // return value OK?
	}

	@Test
	public void testGetSubtaskBidByIdNotMatch() {
		SubtaskBid sb = sam.getSubtaskBidById(0, con);
		assertTrue("Fail in GetSubtaskBidByIdNotMatch", sb == null); // return
																		// value
																		// null?
	}

	@Test
	public void testGetSubtaskAuctionMatch() {
		SubtaskAuction sa = sam.getSubtaskAuction(con);
		assertTrue("Fail in GetSubtaskAuctionMatch 1", sa != null); // return
																	// value not
																	// null?
		ArrayList<Subtask> list = new ArrayList<Subtask>(sa
				.getAvailableSubtasks().values());
		Collections.sort(list);
		assertTrue("Fail in GetSubtaskAuctionMatch 2", list != null); // ArrayList
																		// not
																		// null?
		assertTrue("Fail in GetSubtaskAuctionMatch 3", list.size() == 3); // size
																			// OK?
		Subtask s = list.get(0);
		assertTrue("Fail in GetSubtaskAuctionMatch 4",
				s.getSubtaskId() == 800011); // beginning value OK
		s = list.get(1);
		assertTrue("Fail in GetSubtaskAuctionMatch 5",
				s.getSubtaskId() == 800012); // middle value OK
		s = list.get(2);
		assertTrue("Fail in GetSubtaskAuctionMatch 6",
				s.getSubtaskId() == 800013); // last value OK
	}

	/**
	 * coding stop has taken effect, the following test, that is commented out,
	 * is not completed.
	 */
	/*
	 * @Test public void testGetSubtaskAuctionFail() { try { Statement st =
	 * con.createStatement(); // empty tables st.addBatch("DELETE FROM groups");
	 * st.addBatch("DELETE FROM subtaskbids");
	 * st.addBatch("DELETE FROM subtasks"); st.addBatch("DELETE FROM taskbids");
	 * st.addBatch("DELETE FROM tasks"); st.addBatch("DELETE FROM users");
	 * st.addBatch("DELETE FROM locations"); st.executeBatch(); } catch
	 * (Exception e) { System.out.println("Exception in GetSubtaskAuctionFail");
	 * } }
	 */

	@Test
	public void testSelectWinningSubtaskBidTrue() {
		Subtask s = sam.getSubtask(800011, con);
		assertTrue("Fail in SelectWinningSubtaskBidTrue 1", s != null); // return
																		// value
																		// not
																		// null?
		SubtaskBid sb = sam.getSubtaskBidById(18000111, con);
		assertTrue("Fail in SelectWinningSubtaskBidTrue 2", sb != null); // return
																			// value
																			// not
																			// null?
		boolean success = sam.selectWinningSubtaskBid(sb, s, con);
		assertTrue("Fail in SelectWinningSubtaskBidTrue 3", success); // successful
																		// save.
		assertTrue("Fail in SelectWinningSubtaskBidTrue 4",
				s.isSubtaskAuctionEnded()); // values updated.
		assertTrue("Fail in SelectWinningSubtaskBidTrue 5",
				sb.isSubtaskBidWon()); // value updated.
	}

	@Test
	public void testSelectWinningSubtaskBidFail() {
		boolean success = sam.selectWinningSubtaskBid(null, null, con);
		assertTrue("Fail in SelectWinningSubtaskBidFail", !success); // failed
																		// save.
	}

	@Test
	public void testSaveNewSubtaskTrue() {
		Subtask s = new Subtask();
		String test = "Test";
		s.setSubtaskName(test);
		s.setSubtaskDescription("Test description");
		s.setSubtaskHour(1);
		s.setSubtaskDeadline(Date.valueOf("2011-05-30"));
		boolean success = sam.saveNewSubtask(80001, s, con);
		assertTrue("Fail in SaveNewSubtask 1", success); // save successful?
		s = sam.getSubtaskAuction(con).getAvailableSubtasks().get(1);
		assertTrue("Fail in SaveNewSubtask 2", s != null); // saved value not
															// null?
		assertTrue("Fail in SaveNewSubtask 3", s.getSubtaskName().equals(test)); // value
																					// same
																					// as
																					// the
																					// on
																					// we
																					// set?
	}

	@Test
	public void testSaveNewSubtaskFail() {
		Subtask s = null;
		boolean success = sam.saveNewSubtask(80001, s, con);
		assertTrue("Fail in SaveNewSubtaskFail", !success); // save unsuccessful
	}

	@Test
	public void testSaveNewSubtaskBidTrue() {
		SubtaskBid sb = new SubtaskBid();
		ArrayList<Users> u = new ArrayList<Users>(1);
		UsersMapper um = new UsersMapper();
		u.add(um.getUser(2, con));
		TaskSolverGroup tsg = new TaskSolverGroup(u);
		int subtaskId = 800011;
		sb.setTaskSolverGroup(tsg);
		sb.setSubtaskBidHours(20);
		sb.setSubtaskId(subtaskId);
		boolean success = sam.saveNewSubtaskBid(sb, subtaskId, con);
		assertTrue("Fail in SaveNewSubtaskBidTrue 1", success); // save
																// successful?
		sb = sam.getSubtaskBidById(1, con);
		assertTrue("Fail in SaveNewSubtaskBidTrue 2", sb != null); // return
																	// value not
																	// null?
		assertTrue("Fail in SaveNewSubtaskBidTrue 3",
				sb.getSubtaskBidHours() == 20); // same value that we set?
	}

	@Test
	public void testSaveNewSubtaskBidFail() {
		SubtaskBid sb = null;
		boolean success = sam.saveNewSubtaskBid(sb, 800011, con);
		assertTrue("Fail in SaveNewSubtaskBidFail", !success);
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
