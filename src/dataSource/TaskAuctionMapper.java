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
	 * @return
	 */
	public TaskAuction getTaskAuction(Connection con) {
		TaskAuction ta = null;
		String query = "select * from Tasks where TaskAuctionEnded = 'N' order by TASKCREATED desc";
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
	 * @param con
	 * @return
	 */
	public Task getTask(int taskId, Connection con) {
		Task t = null;
		String query = "select * from Tasks where taskId = ? order by TASKCREATED desc";
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
				t.setTaskAuctionEnded(false);

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
	 *            The Connection to be used
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
			}
		} catch (Exception e) {
			System.out
					.println("Fail in TaskAuctionMapper - getTaskBidBySubtaskId");
			System.out.println(e.getMessage());
		}
		return h;
	}

	/**
	 * @param taskId
	 * @param tb
	 * @param con
	 * @return
	 */
	public boolean selectWinningTaskBid(int taskId, TaskBid tb, Connection con) {
		int rowsUpdated = 0;
		String SQLString1 = "update TaskBids "
				+ "set taskBidWon = 'Y' where taskBidId = ?";
		String SQLString2 = "update Tasks "
				+ "set taskAuctionEnded = 'Y' where taskId = ?";

		PreparedStatement statement = null;

		try {
			// == update TaskBids
			statement = con.prepareStatement(SQLString1);
			statement.setInt(1, tb.getTaskBidId());
			rowsUpdated += statement.executeUpdate();

			// == update tasks
			statement = con.prepareStatement(SQLString2);
			statement.setInt(1, taskId);
			rowsUpdated += statement.executeUpdate();

		} catch (Exception e) {
			System.out
					.println("TaskAuctionMapper went Kaput - selectWinningTaskBid");
			System.out.println(e.getMessage());
		}

		return rowsUpdated == 2;
	}

	/**
	 * @param taskOwner
	 * @param t
	 * @param con
	 * @return
	 */
	public boolean saveNewtask(int taskOwner, Task t, Connection con) {
		int rowsInserted = 0;
		String SQLString1 = "select TaskIdSeq.nextval from dual";
		String SQLString2 = "SELECT sysdate FROM dual";
		String SQLString3 = "INSERT INTO Tasks  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
			System.out.println("TaskAuctionMapper went Kaput - saveNewTask");
			System.out.println(e.getMessage());
		}

		return rowsInserted == 1;
	}

	/**
	 * @param taskId
	 * @param tb
	 * @param con
	 * @return
	 */
	public boolean saveNewTaskBid(int taskId, TaskBid tb, Connection con) {
		int rowsInserted = 0;
		String SQLString1 = "select TaskBidIdSeq.nextval from dual";
		String SQLString2 = "INSERT INTO Taskbids  VALUES (?, ?, ?, ?, ?)";

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
			System.out.println("TaskAuctionMapper went Kaput - saveNewTaskBid");
			System.out.println(e.getMessage());
		}

		return rowsInserted == 1;
	}

	/**
	 * @param t
	 * @param con
	 * @return
	 */
	public boolean editTask(Task t, Connection con) {
		int rowsUpdated = 0;
		String SQLString = "update Tasks where taskName = ?, "
				+ "taskDescription = ?, deadLineBid = ?, "
				+ "taskPrice = ?) where taskId = ?";

		PreparedStatement statement = null;

		try {
			// == update task
			statement = con.prepareStatement(SQLString);
			statement.setString(1, t.getTaskName());
			statement.setString(2, t.getTaskDescription());
			statement.setDate(3, t.getDeadlineBid());
			statement.setDouble(4, t.getPrice());
			statement.setInt(5, t.getTaskId());
			rowsUpdated = statement.executeUpdate();

		} catch (Exception e) {
			System.out.println("TaskAuctionMapper went Kaput - EditTask");
			System.out.println(e.getMessage());
		}

		return rowsUpdated == 1;
	}

	/**
	 * @param userId
	 * @param con
	 * @return
	 */
	public ArrayList<Task> getMyTasks(int userId, Connection con) {
		ArrayList<Task> tasks = new ArrayList<Task>(0);
		String sql = "select * from tasks where userId = ? order by TASKCREATED desc";
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
					t.setTaskAuctionEnded(true);
				tasks.add(t);
			}
		} catch (Exception e) {
			System.out.println("Fail in TaskAuctionMapper - getMyTasks");
			System.out.println(e.getMessage());
		}
		return tasks;
	}
}