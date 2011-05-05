package dataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import domain.Subtask;
import domain.SubtaskAuction;
import domain.SubtaskBid;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Mapper that retrieves all the subtask related objects from the database.
 */
public class SubtaskAuctionMapper {

	public final static boolean TEST = false;

	/**
	 * @param con
	 *            Connection to be used.
	 * @return SubtaskAuction that contains a HashMap of all the Subtasks
	 *         available for auction.
	 */
	public SubtaskAuction getSubtaskAuction(Connection con) {
		SubtaskAuction sa = null;
		String query = "select * from subtasks where subtaskAuctionEnded = 'N'";
		// get all subtask that are still available
		HashMap<Integer, Subtask> h = new HashMap<Integer, Subtask>();
		PreparedStatement statement = null;

		try {
			// === get subtask
			statement = con.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Subtask s = new Subtask();
				s.setSubtaskId(rs.getInt(1));
				s.setSubtaskName(rs.getString(3));
				s.setSubtaskDescription(rs.getString(4));
				s.setSubtaskHour(rs.getInt(5));
				s.setSubtaskCreated(rs.getDate(6));
				s.setSubtaskDeadline(rs.getDate(7));
				s.setSubtaskAuctionEnded(false);
				if (rs.getString(9).equals("Y"))
					s.setSubtaskCompleted(true);
				else
					s.setSubtaskCompleted(false);
				s.setSubtaskBids(getSubtaskBidBySubtaskId(s.getSubtaskId(), con));
				h.put(s.getSubtaskId(), s);
			}
			sa = new SubtaskAuction(h);
		} catch (Exception e) {
			System.out
					.println("Fail in SubtaskAuctionMapper - getSubtaskAuction");
			System.out.println(e.getMessage());
		}
		return sa;
	}

	/**
	 * @param subtaskId
	 *            ID number of the Subtask we are looking for.
	 * @param con
	 *            Connection to be used.
	 * @return Subtask with the ID number passed as a parameter.
	 */
	public Subtask getSubtask(int subtaskId, Connection con) {
		Subtask s = null;
		String query = "select * from subtasks where subtaskId = ?";
		// get subtask by Id

		PreparedStatement statement = null;

		try {
			// === get subtask
			statement = con.prepareStatement(query);
			statement.setInt(1, subtaskId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				s = new Subtask();
				s.setSubtaskId(subtaskId);
				s.setSubtaskName(rs.getString(3));
				s.setSubtaskDescription(rs.getString(4));
				s.setSubtaskHour(rs.getInt(5));
				s.setSubtaskCreated(rs.getDate(6));
				s.setSubtaskDeadline(rs.getDate(7));
				if (rs.getString(8).equals("Y"))
					s.setSubtaskAuctionEnded(true);
				else
					s.setSubtaskAuctionEnded(false);
				if (rs.getString(9).equals("Y"))
					s.setSubtaskCompleted(true);
				else
					s.setSubtaskCompleted(false);
			}
		} catch (Exception e) {
			System.out
					.println("Fail in SubtaskAuctionMapper - getSubtask");
			System.out.println(e.getMessage());
		}
		return s;
	}

	/**
	 * @param subtaskBidId
	 *            ID number of the SubtaskBid we are looking for.
	 * @param con
	 *            Connection to be used.
	 * @return SubtaskBid with the ID number passed as a parameter.
	 */
	public SubtaskBid getSubtaskBidById(int subtaskBidId, Connection con) {
		SubtaskBid sb = null;
		String query = "select * from subtaskBids where subtaskBidId = ?";
		// get subtaskBid by Id

		PreparedStatement statement = null;

		try {
			// === get subtaskBid
			statement = con.prepareStatement(query);
			statement.setInt(1, subtaskBidId); // primary key value
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				sb = new SubtaskBid();
				sb.setSubtaskBidId(subtaskBidId);
				sb.setSubtaskId(rs.getInt("subtaskId"));
				sb.setSubtaskBidHours(rs.getInt("subtaskBidId"));
				if (rs.getString("subtaskBidWon").equals("Y"))
					sb.setSubtaskBidWon(true);
				else
					sb.setSubtaskBidWon(false);
			}
		} catch (Exception e) {
			System.out
					.println("Fail in SubtaskAuctionMapper - getSubtaskBidById");
			System.out.println(e.getMessage());
		}
		return sb;
	}

	/**
	 * @param subtaskId
	 *            ID number of the Subtask that
	 * @param con
	 *            Connection to be used.
	 * @return
	 */
	public HashMap<Integer, SubtaskBid> getSubtaskBidBySubtaskId(int subtaskId,
			Connection con) {
		HashMap<Integer, SubtaskBid> h = null;
		SubtaskBid sb;
		String query = "select * from subtaskBids where subtaskId = ?";
		// get subtaskBid by subtaskId

		PreparedStatement statement = null;
		try {
			// === get subtaskBids
			statement = con.prepareStatement(query);
			statement.setInt(1, subtaskId); // foreign key
			ResultSet rs = statement.executeQuery();
			h = new HashMap<Integer, SubtaskBid>();
			while (rs.next()) {
				sb = new SubtaskBid();
				sb.setSubtaskBidId(rs.getInt("subtaskBidId"));
				sb.setSubtaskId(rs.getInt("subtaskId"));
				sb.setSubtaskBidHours(rs.getInt("subtaskBidHours"));
				if (rs.getString("subtaskBidWon").equals("Y"))
					sb.setSubtaskBidWon(true);
				else
					sb.setSubtaskBidWon(false);
				h.put(sb.getSubtaskBidId(), sb);
			}
		} catch (Exception e) {
			System.out
					.println("Fail in SubtaskAuctionMapper - getSubtaskBidBySubtaskId");
			System.out.println(e.getMessage());
		}
		return h;
	}

	/**
	 * @param sb
	 * @param subtaskId
	 * @param con
	 * @return
	 */
	public boolean selectWinningSubtaskBid(SubtaskBid sb, int subtaskId,
			Connection con) {
		int rowsUpdated = 0;
		String SQLString1 = "update SubtaskBids "
				+ "set subtaskBidWon = 'Y' where subtaskBidId = ?";
		String SQLString2 = "update Subtasks "
				+ "set subtaskAuctionEnded = 'Y' where subtaskId = ?";

		PreparedStatement statement = null;

		try {
			// == update SubtaskBids
			statement = con.prepareStatement(SQLString1);
			statement.setInt(1, sb.getSubtaskBidId());
			rowsUpdated = +statement.executeUpdate();

			// == update Subtasks
			statement = con.prepareStatement(SQLString2);
			statement.setInt(1, subtaskId);
			rowsUpdated = +statement.executeUpdate();

		} catch (Exception e) {
			System.out
					.println("SubtaskAuctionMapper went Kaput - selectWinningSubtaskBid");
			System.out.println(e.getMessage());
		}

		return rowsUpdated == 2;
	}

	/**
	 * @param taskId
	 * @param s
	 * @param con
	 * @return
	 */
	public boolean saveNewSubtask(int taskId, Subtask s, Connection con) {
		int rowsInserted = 0;
		String SQLString1 = "SELECT subtaskIdSeq.nextval FROM dual";
		String SQLString2 = "SELECT sysdate FROM dual";
		String SQLString3 = "INSERT INTO Subtasks  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = null;

		try {
			// == get unique SubtaskId
			statement = con.prepareStatement(SQLString1);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				s.setSubtaskId(rs.getInt(1));
			}
			// == get the current system date
			statement = con.prepareStatement(SQLString2);
			rs = statement.executeQuery();
			if (rs.next()) {
				s.setSubtaskCreated(rs.getDate(1));
			}

			// == insert tuple
			statement = con.prepareStatement(SQLString3);
			statement.setInt(1, s.getSubtaskId());
			statement.setInt(2, taskId);
			statement.setString(3, s.getSubtaskName());
			statement.setString(4, s.getSubtaskDescription());
			statement.setInt(5, s.getSubtaskHour());
			statement.setDate(6, s.getSubtaskCreated());
			statement.setDate(7, s.getSubtaskDeadline());
			if (s.isSubtaskAuctionEnded())
				statement.setString(8, "Y");
			else
				statement.setString(8, "N");
			if (s.isSubtaskCompleted())
				statement.setString(9, "Y");
			else
				statement.setString(9, "N");
			rowsInserted = statement.executeUpdate();
		} catch (Exception e) {
			System.out
					.println("SubtaskAuctionMapper went Kaput - saveNewSubtask");
			System.out.println(e.getMessage());
		}

		return rowsInserted == 1;
	}

	/**
	 * @param sb
	 * @param subtaskId
	 * @param con
	 * @return
	 */
	public boolean saveNewSubtaskBid(SubtaskBid sb, int subtaskId,
			Connection con) {
		int rowsInserted = 0;
		String SQLString1 = "select SubtaskBidIdSeq.nextval from dual";
		String SQLString2 = "INSERT INTO SubtaskBids  VALUES (?, ?, ?, ?)";

		PreparedStatement statement = null;

		try {
			// == get unique SubtaskBidId
			statement = con.prepareStatement(SQLString1);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				sb.setSubtaskBidId(rs.getInt(1));
			}

			// == insert tuple
			statement = con.prepareStatement(SQLString2);
			statement.setInt(1, sb.getSubtaskBidId());
			statement.setInt(2, subtaskId);
			statement.setInt(3, sb.getSubtaskBidHours());
			if (sb.isSubtaskBidWon())
				statement.setString(4, "Y");
			else
				statement.setString(4, "N");
			rowsInserted = statement.executeUpdate();
		} catch (Exception e) {
			System.out
					.println("SubtaskAuctionMapper went Kaput - saveNewSubtaskBid");
			System.out.println(e.getMessage());
		}

		return rowsInserted == 1;
	}

	/**
	 * @param editedSubtask
	 * @param cachedSubtask
	 * @param con
	 *            Connection to be used.
	 * @return
	 */
	public boolean editSubtask(Subtask editedSubtask, Subtask cachedSubtask,
			Connection con) {
		int rowsUpdated = 0;
		int subtaskId = editedSubtask.getSubtaskId();
		if (TEST)
			System.out.println("subtask ID: " + subtaskId);
		// check first that the cached subtask and the one we are trying to edit
		// has the same ID number.

		String sqlString1 = "select * from subtasks where subtaskId = ? for update";
		String sqlString2 = "UPDATE subtasks SET subtaskName = ?, subtaskDescription = ?, "
				+ "subtaskHour = ?, subtaskDeadline = ?, subtaskCreated = ? "
				+ "WHERE subtaskId = ?";

		PreparedStatement statement = null;
		try {
			con.setAutoCommit(false);
			con.setSavepoint();
			// lock the row and get the subtask from the database
			if (TEST)
				System.out.println("execute lock query");
			statement = con.prepareStatement(sqlString1);
			statement.setInt(1, subtaskId);
			ResultSet rs = statement.executeQuery();
			Subtask s = new Subtask();
			if (TEST)
				System.out.println("if statement");
			if (rs.next()) {
				s.setSubtaskId(subtaskId);
				s.setSubtaskName(rs.getString("subtaskName"));
				s.setSubtaskDescription(rs.getString("subtaskDescription"));
				if (TEST)
					System.out.println("String from db: "
							+ rs.getString("subtaskDescription"));
				s.setSubtaskHour(rs.getInt("subtaskHour"));
				s.setSubtaskCreated(rs.getDate("subtaskCreated"));
				s.setSubtaskDeadline(rs.getDate("subtaskDeadline"));
				if (rs.getString(8).equals("Y"))
					s.setSubtaskAuctionEnded(true);
				else
					s.setSubtaskAuctionEnded(false);
				if (rs.getString(9).equals("Y"))
					s.setSubtaskCompleted(true);
				else
					s.setSubtaskCompleted(false);
			}
			if (TEST)
				System.out.println("String from object "
						+ s.getSubtaskDescription());
			// if (s.compareTo(cachedSubtask) == 0)
			if (!s.equals(cachedSubtask)) {
				if (TEST)
					System.out.println("not equal!");
				return false;
			} else {
				statement = con.prepareStatement(sqlString2);
				statement.setString(1, editedSubtask.getSubtaskName());
				statement.setString(2, editedSubtask.getSubtaskDescription());
				statement.setInt(3, editedSubtask.getSubtaskHour());
				statement.setDate(4, editedSubtask.getSubtaskDeadline());
				statement.setDate(5, editedSubtask.getSubtaskCreated());
				statement.setInt(6, subtaskId);
				rowsUpdated = statement.executeUpdate();

			}
		} catch (Exception e) {
			System.out
					.println("SubtaskAuctionMapper went Kaput - EditSubtask2");
			System.out.println(e.getMessage());
		} finally {
			try {
				con.commit();
				con.setAutoCommit(true);
			} catch (SQLException e) {
				try {
					con.rollback();
					con.setAutoCommit(true);
				} catch (SQLException e1) {
					System.out
							.println("SubtaskAuctionMapper went Kaput - EditSubtask3");
					e1.printStackTrace();
				}
				System.out
						.println("SubtaskAuctionMapper went Kaput - EditSubtask4");
				e.printStackTrace();
			}
		}
		return rowsUpdated == 1;
	}

	/**
	 * @param subtaskId
	 * @param con
	 * @return
	 */
	public boolean SubtaskSolved(int subtaskId, Connection con) {
		int rowsUpdated = 0;
		String SQLString = "update subtasks set subtaskComplete = 'Y' where subtaskId = ?";

		PreparedStatement statement = null;

		try {
			// == update subtask
			statement = con.prepareStatement(SQLString);
			statement.setInt(1, subtaskId);
			rowsUpdated = statement.executeUpdate();
		} catch (Exception e) {
			System.out
					.println("SubtaskAuctionMapper got error - it's in SubtaskSolved!");
			System.out.println(e.getMessage());
		}

		return rowsUpdated == 1;
	}

	/**
	 * @param taskId
	 * @param con
	 * @return
	 */
	public HashMap<Integer, Subtask> getSubtasksByTaskId(int taskId,
			Connection con) {
		HashMap<Integer, Subtask> st = new HashMap<Integer, Subtask>();
		String sql = "select * from subtasks where taskId = ? order by subtaskCreated";
		// get subtasks by taskId

		PreparedStatement statement = null;

		try {
			// === get subtask
			statement = con.prepareStatement(sql);
			statement.setInt(1, taskId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Subtask s = new Subtask();
				s.setSubtaskId(rs.getInt("subtaskId"));
				s.setSubtaskName(rs.getString("subtaskName"));
				s.setSubtaskDescription(rs.getString("subtaskDescription"));
				s.setSubtaskHour(rs.getInt("subtaskHour"));
				s.setSubtaskCreated(rs.getDate("subtaskCreated"));
				s.setSubtaskDeadline(rs.getDate("subtaskDeadline"));
				if (rs.getString("subtaskAuctionEnded").equals("Y"))
					s.setSubtaskAuctionEnded(true);
				else
					s.setSubtaskAuctionEnded(false);
				if (rs.getString("subtaskComplete").equals("Y"))
					s.setSubtaskCompleted(true);
				else
					s.setSubtaskCompleted(false);
				s.setSubtaskBids(getSubtaskBidBySubtaskId(s.getSubtaskId(), con));
				st.put(s.getSubtaskId(), s);
			}
		} catch (Exception e) {
			System.out
					.println("Fail in SubtaskAuctionMapper - getSubtaskByTaskId");
			System.out.println(e.getMessage());
		}
		return st;
	}
}
