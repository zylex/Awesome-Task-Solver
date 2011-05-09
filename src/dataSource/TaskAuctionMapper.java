package dataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import domain.Task;
import domain.TaskAuction;
import domain.TaskBid;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Mapper that retrieves all the task related objects from the database.
 */
public class TaskAuctionMapper {

	/**
	 * @param con
	 *            The Connection to be used
	 * @return
	 */
	public TaskAuction getTaskAuction(Connection con) {
		TaskAuction ta = null;
		String query = "SELECT * FROM Tasks WHERE TaskAuctionEnded = 'N' ORDER BY taskId DESC";
		// get all tasks that are still available
		HashMap<Integer, Task> h = new HashMap<Integer, Task>();
		PreparedStatement statement = null;

		try {
			// === get Task
			statement = con.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Task t = new Task();
				t.setTaskId(rs.getInt(1));
				t.setTaskName(rs.getString(3));
				t.setTaskDescription(rs.getString(4));
				t.setTaskCreated(rs.getDate(5));
				t.setDeadlineBid(rs.getDate(6));
				t.setPrice(rs.getInt(7));
				t.setTaskAuctionEnded(false);
				t.setVersion(rs.getInt(9));

				t.setTaskBids(getTaskBidByTaskId(t.getTaskId(), con));
				h.put(t.getTaskId(), t);
			}
			ta = new TaskAuction(h);
		} catch (Exception e) {
			System.out.println("Fail in TaskAuctionMapper - getTaskAuction");
			System.out.println(e.getMessage());
		}
		return ta;
	}

	/**
	 * @param taskId
	 *            ID number of the Task we are looking for.
	 * @param con
	 *            Connection to be used.
	 * @return Task we are looking for.
	 */
	public Task getTask(int taskId, Connection con) {
		Task t = null;
		String query = "select * from Tasks where taskId = ?";
		// get task by Id

		PreparedStatement statement = null;

		try {
			// === get task
			statement = con.prepareStatement(query);
			statement.setInt(1, taskId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				t = new Task();
				t.setTaskId(taskId);
				t.setTaskName(rs.getString(3));
				t.setTaskDescription(rs.getString(4));
				t.setTaskCreated(rs.getDate(5));
				t.setDeadlineBid(rs.getDate(6));
				t.setPrice(rs.getInt(7));
				if (rs.getString(8).equals("Y"))
					t.setTaskAuctionEnded(true);
				else
					t.setTaskAuctionEnded(true);
				t.setVersion(rs.getInt(9));

				t.setTaskBids(getTaskBidByTaskId(t.getTaskId(), con));
			}
		} catch (Exception e) {
			System.out.println("Fail in TaskAuctionMapper - getTask");
			System.out.println(e.getMessage());
		}
		return t;
	}

	/**
	 * @param taskBidId
	 *            The ID number of the TaskBid we are looking for.
	 * @param con
	 *            Connection to be used
	 * @return TaskBid we are looking for.
	 */
	public TaskBid getTaskBidById(int taskBidId, Connection con) {
		TaskBid tb = null;
		String query = "select * from TaskBids where taskBidId = ?";
		// get taskBid by Id

		PreparedStatement statement = null;

		try {
			// === get taskBid
			statement = con.prepareStatement(query);
			statement.setInt(1, taskBidId); // primary key value
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				tb = new TaskBid();
				tb.setTaskBidId(taskBidId);
				tb.setTaskId(rs.getInt("taskId"));
				tb.setTaskBidHour(rs.getFloat("taskBidHour"));
				if (rs.getString("taskbidWon").equals("Y"))
					tb.setTaskBidWon(true);
				else
					tb.setTaskBidWon(false);
				tb.setVersion(rs.getInt("taskbidv"));
			}
		} catch (Exception e) {
			System.out.println("Fail in TaskAuctionMapper - getTaskBidById");
			System.out.println(e.getMessage());
		}
		return tb;
	}

	/**
	 * @param taskId
	 *            ID number of the TaskBids we are looking for
	 * @param con
	 *            The Connection to be used.
	 * @return HashMap with the TaskBids linked to the given taskId
	 */
	public HashMap<Integer, TaskBid> getTaskBidByTaskId(int taskId,
			Connection con) {
		HashMap<Integer, TaskBid> h = null;
		TaskBid tb;
		String query = "select * from TaskBids where taskId = ?";
		// get taskBid by taskId

		PreparedStatement statement = null;

		try {
			// === get taskBids
			statement = con.prepareStatement(query);
			statement.setInt(1, taskId);
			ResultSet rs = statement.executeQuery();
			h = new HashMap<Integer, TaskBid>();
			while (rs.next()) {
				tb = new TaskBid();
				tb.setTaskBidId(rs.getInt("taskbidId"));
				tb.setTaskId(rs.getInt("taskId"));
				tb.setTaskBidHour(rs.getFloat("taskBidHour"));
				if (rs.getString("taskbidWon").equals("Y"))
					tb.setTaskBidWon(true);
				else
					tb.setTaskBidWon(false);
				h.put(tb.getTaskBidId(), tb);
				tb.setVersion(rs.getInt("taskbidv"));
			}
		} catch (Exception e) {
			System.out
					.println("Fail in TaskAuctionMapper - getTaskBidBySubtaskId");
			System.out.println(e.getMessage());
		}
		return h;
	}

	/**
	 * @param t
	 *            Task for which we have selected a winner.
	 * @param tb
	 *            TaskBid that has won the auction.
	 * @param con
	 *            Connection to be used.
	 * @return boolean whether the edit was successful or not.
	 */
	public boolean selectWinningTaskBid(Task t, TaskBid tb, Connection con) {
		if (t == null || tb == null)
			return false;
		else {
			int rowsUpdated = 0;
			String sql1 = "UPDATE Tasks "
					+ "SET taskAuctionEnded = 'Y', taskv = taskv + 1 "
					+ "WHERE taskId = ? AND taskv = ?";
			String sql2 = "UPDATE TaskBids "
					+ "SET taskBidWon = 'Y', taskbidv = taskbidv + 1 "
					+ "WHERE taskBidId = ? AND taskbidv = ?";
			// optimistic concurrency control.
			PreparedStatement statement = null;

			try {

				// == update Tasks
				statement = con.prepareStatement(sql1);
				statement.setInt(1, t.getTaskId()); // Task primary key
				statement.setInt(2, t.getVersion()); // Version number
				rowsUpdated += statement.executeUpdate();
				if (rowsUpdated == 1) {
					// == update TaskBids
					statement = con.prepareStatement(sql2);
					statement.setInt(1, tb.getTaskBidId()); // TaskBid primary
															// key
					statement.setInt(2, tb.getVersion()); // Version number
					rowsUpdated += statement.executeUpdate();
				}
			} catch (Exception e) {
				System.out
						.println("TaskAuctionMapper went Kaput - selectWinningTaskBid");
				System.out.println(e.getMessage());
			}
			return rowsUpdated == 2;
		}
	}

	/**
	 * @param taskOwner
	 *            ID number of the Task Owner of the Task we are trying to save.
	 * @param t
	 *            The new task we are trying to save.
	 * @param con
	 *            Connection to be used.
	 * @return boolean whether the save was successful or not.
	 */
	public boolean saveNewtask(int taskOwner, Task t, Connection con) {
		if (t == null)
			return false;
		else {
			int rowsInserted = 0;
			String SQLString1 = "SELECT TaskIdSeq.nextval FROM dual";
			String SQLString2 = "SELECT sysdate FROM dual";
			String SQLString3 = "INSERT INTO Tasks  VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1)";

			PreparedStatement statement = null;

			try {
				// == get unique TaskId
				statement = con.prepareStatement(SQLString1);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					t.setTaskId(rs.getInt(1));
				}
				// == get the system date from the server.
				statement = con.prepareStatement(SQLString2);
				rs = statement.executeQuery();
				if (rs.next()) {
					t.setTaskCreated(rs.getDate(1));
				}

				// == insert tuple
				statement = con.prepareStatement(SQLString3);
				statement.setInt(1, t.getTaskId());
				statement.setInt(2, taskOwner);
				statement.setString(3, t.getTaskName());
				statement.setString(4, t.getTaskDescription());
				statement.setDate(5, t.getTaskCreated());
				statement.setDate(6, t.getDeadlineBid());
				statement.setDouble(7, t.getPrice());
				statement.setString(8, "N");
				rowsInserted = statement.executeUpdate();
			} catch (Exception e) {
				System.out
						.println("TaskAuctionMapper went Kaput - saveNewTask");
				System.out.println(e.getMessage());
			}
			return rowsInserted == 1;
		}
	}

	/**
	 * @param taskId
	 *            ID number of the Task we are bidding on.
	 * @param tb
	 *            TaskBid we are trying to save.
	 * @param con
	 *            Connection to be used.
	 * @return boolean whether the save was successful or not.
	 */
	public boolean saveNewTaskBid(int taskId, TaskBid tb, Connection con) {
		if (tb == null)
			return false;
		else {
			int rowsInserted = 0;
			String SQLString1 = "select TaskBidIdSeq.nextval from dual";
			String SQLString2 = "INSERT INTO Taskbids  VALUES (?, ?, ?, ?, ?, 1)";

			PreparedStatement statement = null;

			try {
				// == get unique taskBidId
				statement = con.prepareStatement(SQLString1);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					tb.setTaskBidId(rs.getInt(1));
				}

				// == insert tuple
				statement = con.prepareStatement(SQLString2);
				statement.setInt(1, tb.getTaskBidId());
				statement.setInt(2, taskId);
				statement.setInt(3, tb.getTaskManager().getUserId());
				statement.setFloat(4, tb.getTaskBidHour());
				if (tb.isTaskBidWon())
					statement.setString(5, "Y");
				else
					statement.setString(5, "N");
				rowsInserted = statement.executeUpdate();
			} catch (Exception e) {
				System.out
						.println("TaskAuctionMapper went Kaput - saveNewTaskBid");
				System.out.println(e.getMessage());
			}
			return rowsInserted == 1;
		}
	}

	/**
	 * @param t
	 *            Task that we are trying to edit
	 * @param con
	 *            The Connection to be used.
	 * @return boolean whether the update was successful or not.
	 */
	public boolean editTask(Task t, Connection con) {
		if (t == null)
			return false;
		else {
			int rowsUpdated = 0;
			String sql = "UPDATE Tasks "
					+ "SET taskName = ?, taskDescription = ?, "
					+ "deadlineBid = ?, taskPrice = ?, " + "taskv = taskv + 1 "
					+ "WHERE taskId = ? AND taskv = ?"; // update using
														// optimistic
														// concurrency control
														// (version number)
			PreparedStatement statement = null;

			try {
				// == update Task
				statement = con.prepareStatement(sql);
				statement.setString(1, t.getTaskName());
				statement.setString(2, t.getTaskDescription());
				statement.setDate(3, t.getDeadlineBid());
				statement.setDouble(4, t.getPrice());
				statement.setInt(5, t.getTaskId());
				statement.setInt(6, t.getVersion());
				System.out.println("statement: " + statement + "\nsql: " + sql);
				rowsUpdated += statement.executeUpdate();
				System.out.println("rowsupdated: " + rowsUpdated);
			} catch (Exception e) {
				System.out.println("TaskAuctionMapper went Kaput - EditTask");
				System.out.println(e.getMessage());
			}
			return rowsUpdated == 1;
		}
	}

	/**
	 * @param userId
	 *            ID number of the Task Owner whose task we are trying to fetch.
	 * @param con
	 *            Connection to be used.
	 * @return ArrayList of the Task Owner's Tasks.
	 */
	public ArrayList<Task> getMyTasks(int userId, Connection con) {
		ArrayList<Task> tasks = new ArrayList<Task>(0);
		String sql = "SELECT * FROM Tasks WHERE userId = ? ORDER BY taskId DESC";
		// get task by userId

		PreparedStatement statement = null;

		try {
			// === get task
			statement = con.prepareStatement(sql);
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Task t = new Task();
				t.setTaskId(rs.getInt("taskId"));
				t.setTaskName(rs.getString("taskName"));
				t.setTaskDescription(rs.getString("taskDescription"));
				t.setTaskCreated(rs.getDate("taskCreated"));
				t.setDeadlineBid(rs.getDate("deadlineBid"));
				t.setPrice(rs.getInt("taskPrice"));
				if (rs.getString("taskAuctionEnded").equals("Y"))
					t.setTaskAuctionEnded(true);
				else
					t.setTaskAuctionEnded(false);
				tasks.add(t);
			}
		} catch (Exception e) {
			System.out.println("Fail in TaskAuctionMapper - getMyTasks");
			System.out.println(e.getMessage());
		}
		return tasks;
	}
}