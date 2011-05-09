package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Test class sets up the database information for testing.
 */
public class Fixture {

	@Before
	public static void setUp(Connection con) {
		try {
			Statement st = con.createStatement();
			// start transaction
			con.setAutoCommit(false);

			// drop sequences
			st.addBatch("DROP sequence SubtaskIdSeq");
			st.addBatch("DROP sequence SubtaskBidIdSeq");
			st.addBatch("DROP sequence TaskIdSeq");
			st.addBatch("DROP sequence TaskBidIdSeq");
			st.addBatch("DROP sequence UserIdSeq");
			
			// re-create sequences
			st.addBatch("CREATE sequence SubtaskIdSeq");
			st.addBatch("CREATE sequence SubtaskBidIdSeq");
			st.addBatch("CREATE sequence TaskIdSeq");
			st.addBatch("CREATE sequence TaskBidIdSeq");
			st.addBatch("CREATE sequence UserIdSeq");
			
			
			// empty tables
			st.addBatch("DELETE FROM groups");
			st.addBatch("DELETE FROM subtaskbids");
			st.addBatch("DELETE FROM subtasks");
			st.addBatch("DELETE FROM taskbids");
			st.addBatch("DELETE FROM tasks");
			st.addBatch("DELETE FROM users");
			st.addBatch("DELETE FROM locations");
			
			// values taken from InsertHardCodedTuples.sql

			// insert tuples into locations.
			String insert = "INSERT INTO locations VALUES ";
			st.addBatch(insert + "('Denmark', 200, 'DKK')");
			st.addBatch(insert + "('USA', 30, 'USD')");
			st.addBatch(insert + "('China', 122, 'CNY')");

			// insert tuples into users.
			insert = "INSERT INTO users VALUES ";
			st.addBatch(insert + "(2, 25, 'AA', 'XXX', 'Denmark')");
			st.addBatch(insert + "(3, 25, 'Jonathan Anasta', 'XXX', 'USA')");
			st.addBatch(insert + "(4, 25, 'Jackie Chan', 'XXX', 'China')");
			st.addBatch(insert + "(11, 50, 'Daniel Sampi', 'XXX', 'USA')");
			st.addBatch(insert + "(12, 50, 'Anthony Arena', 'XXX', 'China')");
			st.addBatch(insert + "(13, 50, 'Casper Caspersen', 'XXX', 'Denmark')");
			st.addBatch(insert + "(111, 75, 'Xiu Wang','XXX', 'China')");
			st.addBatch(insert + "(112, 75, 'Carl Carlsen', 'XXX', 'Denmark')");
			st.addBatch(insert + "(113, 75, 'Jesper Hvid', 'XXX', 'Denmark')");
			st.addBatch(insert + "(114, 75, 'Brian Johnson', 'XXX', 'USA')");
			st.addBatch(insert + "(115, 75, 'Bo Hund', 'XXX', 'Denmark')");

			// insert tuples into tasks.
			insert = "INSERT INTO tasks VALUES ";
			st.addBatch(insert
					+ "(80001, 2, 'First Task', 'Make a program', to_date('12-MAR-11','DD MON YY'), "
					+ "to_date('12-APR-11','DD MON YY'), 20000, 'N', 1)");
			st.addBatch(insert
					+ "(86501, 2, 'Test Task2', 'description2', to_date('10-MAR-11','DD MON YY'), "
					+ "to_date('13-APR-11','DD MON YY'), 19000, 'N', 1)");
			st.addBatch(insert
					+ "(89001, 4, 'Test Task3', 'Description3', to_date('26-MAR-11','DD MON YY'), "
					+ "to_date('14-APR-11','DD MON YY'), 5800, 'N', 1)");
			st.addBatch(insert
					+ "(57747, 2, 'Test Task4', 'Description4',to_date('13-MAR-11','DD MON YY'), "
					+ "to_date('15-APR-11','DD MON YY'), 45600, 'N', 1)");
			st.addBatch(insert
					+ "(99035, 4, 'Test Task5', 'Description5', to_date('18-MAR-11','DD MON YY'), "
					+ "to_date('16-APR-11','DD MON YY'), 92500, 'N', 1)");
			st.addBatch(insert
					+ "(50001, 3, 'Printing Task', 'Printing out 4 differents output', "
					+ "to_date('24-FEB-11','DD-MON-YY'), to_date('30-APR-11','DD-MON-YY'), "
					+ "5000, 'Y', 1)");

			// insert tuples into taskBids.
			insert = "INSERT INTO taskbids VALUES ";
			st.addBatch(insert + "(040001, 50001, 11, 100, 'N', 1)");
			st.addBatch(insert + "(040002, 50001, 12, 80, 'N', 1)");
			st.addBatch(insert + "(040003, 80001, 13, 60, 'Y', 1)");
			st.addBatch(insert + "(040004, 80001, 11, 120, 'N', 1)");

			// insert tuples into subtasks.
			insert = "INSERT INTO subtasks VALUES ";
			st.addBatch(insert
					+ "(800011, 80001, '1st part', 'Do the first part', "
					+ "20, to_date('24-APR-11','DD-MON-YY'), "
					+ "to_date('30-APR-11','DD-MON-YY'), 'N', 'N', 1)");
			st.addBatch(insert
					+ "(800012, 80001, '2nd part', 'Do the second part',"
					+ "10, to_date('25-APR-11','DD-MON-YY'), "
					+ "to_date('30-APR-11','DD-MON-YY'), 'N', 'N', 1)");
			st.addBatch(insert
					+ "(800013, 80001, '3rd part', 'Do the third part',"
					+ "30, to_date('26-APR-11','DD-MON-YY'), "
					+ "to_date('30-APR-11','DD-MON-YY'), 'N', 'N', 1)");

			// insert tuples into subtasksBids.
			insert = "INSERT INTO subtaskbids VALUES ";
			st.addBatch(insert + "(18000111, 800011, 18, 'N', 1)");
			st.addBatch(insert + "(18000112, 800011, 10, 'N', 1)");
			st.addBatch(insert + "(18000113, 800011, 12, 'N', 1)");
			st.addBatch(insert + "(18000131, 800013, 10, 'N', 1)");
			st.addBatch(insert + "(18000231, 800013, 10, 'N', 1)");
			st.addBatch(insert + "(18000121, 800012, 17, 'N', 1)");
			st.addBatch(insert + "(18000221, 800013, 8, 'N', 1)");
			st.addBatch(insert + "(18000321, 800013, 8, 'N', 1)");

			// insert tuples into groups.
			insert = "INSERT INTO groups VALUES ";
			st.addBatch(insert + "(18000111, 112)");
			st.addBatch(insert + "(18000112, 113)");
			st.addBatch(insert + "(18000113, 112)");
			st.addBatch(insert + "(18000131, 113)");
			st.addBatch(insert + "(18000131, 115)");
			st.addBatch(insert + "(18000231, 111)");
			st.addBatch(insert + "(18000231, 114)");
			st.addBatch(insert + "(18000121, 111)");
			st.addBatch(insert + "(18000121, 112)");
			st.addBatch(insert + "(18000121, 113)");

			//int[] opcounts = st.executeBatch();
			st.executeBatch();
			
			// end transaction
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Fail in Fixture.setup()");
			System.out.println(e.getMessage());
		}
	}
}
