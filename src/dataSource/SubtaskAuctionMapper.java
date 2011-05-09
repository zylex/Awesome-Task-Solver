package dataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	/**
	 * @param con
	 *            Connection to be used.
	 * @return SubtaskAuction that contains a HashMap of all the Subtasks
	 *         available for auction.
	 */
	public SubtaskAuction getSubtaskAuction(Connection con) {
		SubtaskAuction sa = null;
		String query = "select * from subtasks where subtaskAuctionEnded = 'N' ORDER BY subtaskId DESC";
		// get all subtask that are still available
		HashMap<Integer, Subtask> h = new HashMap<Integer, Subtask>();
		PreparedStatement statement = null;

		try {
			// === get subtask
			statement = con.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Subtask s = new Subtask();
				s.setSubtaskId(rs.getInt("subtaskId"));
				s.setSubtaskName(rs.getString("subtaskName"));
				s.setSubtaskDescription(rs.getString("subtaskDescription"));
				s.setSubtaskHour(rs.getInt("subtaskHour"));
				s.setSubtaskCreated(rs.getDate("subtaskCreated"));
				s.setSubtaskDeadline(rs.getDate("subtaskDeadline"));
				s.setSubtaskAuctionEnded(false); // is available
				if (rs.getString("subtaskComplete").equals("Y"))
					s.setSubtaskCompleted(true);
				else
					s.setSubtaskCompleted(false);
				s.setVersion(rs.getInt("subtaskv"));
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
				s.setVersion(rs.getInt("subtaskv"));
			}
		} catch (Exception e) {
			System.out.println("Fail in SubtaskAuctionMapper - getSubtask");
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
				sb.setSubtaskBidHours(rs.getInt("subtaskBidHours"));
				if (rs.getString("subtaskBidWon").equals("Y"))
					sb.setSubtaskBidWon(true);
				else
					sb.setSubtaskBidWon(false);
				sb.setVersion(rs.getInt("subtaskbidv"));
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
				sb.setVersion(rs.getInt("subtaskbidv"));
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
	 *            SubtaskBid that has won the auction.
	 * @param s
	 *            Subtask which has now ended it's auction.
	 * @param con
	 *            Connection to be used.
	 * @return Whether the update was successful or not.
	 */
	public boolean selectWinningSubtaskBid(SubtaskBid sb, Subtask s,
			Connection con) {
		if (sb == null || s == null)
			return false;
		else {
			int rowsUpdated = 0;
			String sql1 = "UPDATE SubtaskBids "
					+ "SET subtaskBidWon = 'Y', subtaskbidv = subtaskbidv + 1 "
					+ "WHERE subtaskBidId = ? AND subtaskbidv = ? AND subtaskId = ?";
			String sql2 = "UPDATE Subtasks "
					+ "SET subtaskAuctionEnded = 'Y', subtaskv = subtaskv + 1 "
					+ "WHERE subtaskId = ? AND subtaskv = ?";
			PreparedStatement statement = null;

			try {
				// == update SubtaskBids
				statement = con.prepareStatement(sql1);
				statement.setInt(1, sb.getSubtaskBidId()); // primary key value
				statement.setInt(2, sb.getVersion()); // version control
				statement.setInt(3, sb.getSubtaskId()); // foreign key
				rowsUpdated = statement.executeUpdate();
				sb.setSubtaskBidWon(true);
			} catch (Exception e) {
				System.out
						.println("SubtaskAuctionMapper went Kaput - selectWinningSubtaskBid SubtaskBids");
				System.out.println(e.getMessage());
			}
			try {
				// == update Subtasks
				statement = con.prepareStatement(sql2);
				statement.setInt(1, s.getSubtaskId()); // primary key value
				statement.setInt(2, s.getVersion()); // version control
				rowsUpdated += statement.executeUpdate();
				s.setSubtaskAuctionEnded(true);
			} catch (Exception e) {
				System.out
						.println("SubtaskAuctionMapper went Kaput - selectWinningSubtaskBid Subtasks");
				System.out.println(e.getMessage());
			}
			return rowsUpdated == 2;
		}
	}

	/**
	 * @param taskId
	 *            ID number of the Task that the Subtask belongs to.
	 * @param s
	 *            Subtask that we are trying to create.
	 * @param con
	 *            Connection to be used.
	 * @return Whether the save was successful or not.
	 */
	public boolean saveNewSubtask(int taskId, Subtask s, Connection con) {
		if (s == null)
			return false;
		else {
			int rowsInserted = 0;
			String SQLString1 = "SELECT subtaskIdSeq.nextval FROM dual";
			String SQLString2 = "SELECT sysdate FROM dual";
			String SQLString3 = "INSERT INTO Subtasks "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

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
	}

	/**
	 * @param sb
	 *            SubtaskBid that we are trying to save.
	 * @param subtaskId
	 *            ID number of the Subtask that the bid is for.
	 * @param con
	 *            Connection to be used.
	 * @return boolean whether the save was successful or not.
	 */
	public boolean saveNewSubtaskBid(SubtaskBid sb, int subtaskId,
			Connection con) {
		if (sb == null) {
			return false;
		} else {
			int rowsInserted = 0;
			String SQLString1 = "select SubtaskBidIdSeq.nextval from dual";
			String SQLString2 = "INSERT INTO SubtaskBids  VALUES (?, ?, ?, ?, 1)";

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
	}

	/**
	 * To be updated.
	 * 
	 * @param s
	 *            The edited Subtask to be saved.
	 * @param con
	 *            Connection to be used.
	 * @return
	 */
	public boolean editSubtask(Subtask s, Connection con) {
		if (s == null)
			return false;
		else {
			int rowsUpdated = 0;
			String sql = "UPDATE subtasks SET subtaskName = ?, subtaskDescription = ?, "
					+ "subtaskHour = ?, subtaskDeadline = ?, subtaskCreated = ?, subtaskv = subtaskv + 1 "
					+ "WHERE subtaskId = ? AND subtaskv = ?"; // SQL statement.
			PreparedStatement statement = null;

			try {
				statement = con.prepareStatement(sql);
				statement.setString(1, s.getSubtaskName());
				statement.setString(2, s.getSubtaskDescription());
				statement.setInt(3, s.getSubtaskHour());
				statement.setDate(4, s.getSubtaskDeadline());
				statement.setDate(5, s.getSubtaskCreated());
				statement.setInt(6, s.getSubtaskId());
				statement.setInt(7, s.getVersion());
				rowsUpdated = statement.executeUpdate();
			} catch (Exception e) {
				System.out
						.println("SubtaskAuctionMapper went Kaput - EditSubtask");
				System.out.println(e.getMessage());
			}
			return rowsUpdated == 1;
		}
	}

	/**
	 * @param subtaskId
	 *            ID number of the subtask that has been solved.
	 * @param con
	 *            Connection to be used.
	 * @return boolean whether the save was successful or not.
	 */
	public boolean subtaskSolved(Subtask s, Connection con) {
		if (s == null)
			return false;
		else {
			int rowsUpdated = 0;
			String SQLString = "UPDATE subtasks SET subtaskComplete = 'Y', subtaskv = subtaskv + 1 "
					+ "WHERE subtaskId = ? AND subtaskv = ?";

			PreparedStatement statement = null;

			try {
				// == update subtask
				statement = con.prepareStatement(SQLString);
				statement.setInt(1, s.getSubtaskId());
				statement.setInt(2, s.getVersion());
				rowsUpdated = statement.executeUpdate();
			} catch (Exception e) {
				System.out
						.println("SubtaskAuctionMapper got error - it's in SubtaskSolved!");
				System.out.println(e.getMessage());
			}
			return rowsUpdated == 1;
		}
	}

	/**
	 * @param taskId
	 * @param con
	 * @return
	 */
	public HashMap<Integer, Subtask> getSubtasksByTaskId(int taskId,
			Connection con) {
		HashMap<Integer, Subtask> st = new HashMap<Integer, Subtask>();
		String sql = "select * from subtasks where taskId = ? order by subtaskId";
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
				s.setVersion(rs.getInt("subtaskv"));
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
