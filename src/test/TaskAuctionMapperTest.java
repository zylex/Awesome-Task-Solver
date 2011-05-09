package test;

import java.sql.Connection;
import java.sql.DriverManager;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import dataSource.TaskAuctionMapper;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Test class tests the TaskAuctionMapper class.
 */
public class TaskAuctionMapperTest extends TestCase {

	Connection con;
	private String id = "knjofr8";
	private String pw = "John";
	TaskAuctionMapper tam;

	@Before
	public void setUp() throws Exception {
		getConnection();
		Fixture.setUp(con);
		tam = new TaskAuctionMapper();
	}

	@After
	public void tearDown() throws Exception {
		releaseConnection();
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
