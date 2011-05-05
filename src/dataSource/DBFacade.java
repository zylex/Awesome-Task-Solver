package dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import domain.Subtask;
import domain.SubtaskAuction;
import domain.SubtaskBid;
import domain.Task;
import domain.TaskAuction;
import domain.TaskBid;
import domain.TaskSolverGroup;
import domain.Users;

/**
 * @author 
 * 		Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Facade is called to retrieve various objects from the database. The Facade
 * uses the various mappers.
 */
public class DBFacade {
	private SubtaskAuctionMapper sam;
	private TaskAuctionMapper tam;
	private UsersMapper um;
	private ConversionMapper cm;
	private String user = "knjofr8";
	private String pass = "John";

	// is a Singleton
	private static DBFacade instance;

	/**
	 * private constructor because is a singleton
	 */
	private DBFacade() {
		sam = new SubtaskAuctionMapper();
		tam = new TaskAuctionMapper();
		um = new UsersMapper();
		cm = new ConversionMapper();
	}

	/**
	 * get instance, to be used instead of constructor to make sure there is
	 * only 1 instance
	 * 
	 * @return the single instance of DBFacade
	 */
	public static DBFacade getInstance() {
		if (instance == null)
			instance = new DBFacade();
		return instance;
	}

	/**
	 * @param userId
	 *            The primary key of the users in the database.
	 * @return Users with the specified ID number
	 */
	public Users getUser(int userId) {
		Users u = null;
		Connection con = null;
		try {
			con = getConnection();
			// call mapper to return the Users with specified ID.
			u = um.getUser(userId, con);
		} finally {
			releaseConnection(con);
		}
		return u;
	}

	/**
	 * @param subtaskBidId
	 *            The subtask bid associated with the ID number
	 * @return TaskSolverGroup by subtask bid ID number
	 */
	public TaskSolverGroup getTaskSolverGroup(int subtaskBidId) {
		TaskSolverGroup t = null;
		Connection con = null;
		try {
			con = getConnection();
			t = um.getTaskSolverGroup(subtaskBidId, con);
		} finally {
			releaseConnection(con);
		}
		return t;
	}

	/**
	 * @param subtaskId
	 *            the ID number associated with the subtask.
	 * @return Subtask related to the ID number.
	 */
	public Subtask getSubtask(int subtaskId) {
		Subtask s = null;
		Connection con = null;
		try {
			con = getConnection();
			// get the subtask
			s = sam.getSubtask(subtaskId, con);
			// get the task manager of the subtask
			s.setTaskManager(um.getTaskManagerBySubtaskId(s.getSubtaskId(), con));
			// get the HashMap of subtask bids
			HashMap<Integer, SubtaskBid> sb = sam.getSubtaskBidBySubtaskId(
					s.getSubtaskId(), con);
			Iterator<Integer> itBids = sb.keySet().iterator();
			while (itBids.hasNext()) {
				SubtaskBid sBid = sb.get(itBids.next());
				// set the TaskSolverGroup of each bid
				sBid.setTaskSolverGroup(um.getTaskSolverGroup(subtaskId, con));
				sb.put(subtaskId, sBid);
				s.setSubtaskBids(sb);
			}
		} finally {
			releaseConnection(con);
		}
		return s;
	}

	/**
	 * @param cache
	 *            The Subtask that is stored in the subtaskAuction.
	 * @param s
	 *            The edited Subtask that we wish to save.
	 * @return Whether the edit was successful or not.
	 */
	public boolean editSubtask(Subtask cache, Subtask s) {
		Connection con = null;
		boolean status = false;
		try {
			con = getConnection();
			status = sam.editSubtask(s, cache, con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @param subtaskBidId
	 *            The ID number of the SubtaskBid we are wish to retrieve.
	 * @return SubtaskBid with the specified ID number from the database.
	 */
	public SubtaskBid getSubtaskBid(int subtaskBidId) {
		SubtaskBid sb = null;
		Connection con = null;
		try {
			con = getConnection();
			sb = sam.getSubtaskBidById(subtaskBidId, con);
			// get the bid's TaskSolverGroup
			sb.setTaskSolverGroup(um.getTaskSolverGroup(sb.getSubtaskBidId(),
					con));
		} finally {
			releaseConnection(con);
		}
		return sb;
	}

	/**
	 * @return The SubtaskAuction with all Subtasks that are available for
	 *         bidding.
	 */
	public SubtaskAuction getSubtaskAuction() {
		SubtaskAuction sa = null;
		Connection con = null;
		try {
			con = getConnection();
			sa = sam.getSubtaskAuction(con);
			HashMap<Integer, Subtask> hm = sa.getAvailableSubtasks();
			Iterator<Integer> it = hm.keySet().iterator();
			while (it.hasNext()) {
				Subtask s = hm.get(it.next());
				// set TaskManager for each Subtask in the HashMap.
				s.setTaskManager(um.getTaskManagerBySubtaskId(s.getSubtaskId(),
						con));
				// get the Bids for each Subtask
				HashMap<Integer, SubtaskBid> sb = sam.getSubtaskBidBySubtaskId(
						s.getSubtaskId(), con);
				Iterator<Integer> itBids = sb.keySet().iterator();
				while (itBids.hasNext()) {
					SubtaskBid sBid = sb.get(itBids.next());
					int subtaskBidId = sBid.getSubtaskBidId();
					// set the TaskSolverGroup for each Bid
					sBid.setTaskSolverGroup(um.getTaskSolverGroup(subtaskBidId,
							con));
					sb.put(subtaskBidId, sBid);

				}
				s.setSubtaskBids(sb);
				hm.put(s.getSubtaskId(), s);
			}
			sa.setAvailableSubtasks(hm);
		} finally {
			releaseConnection(con);
		}
		return sa;
	}

	/**
	 * @param u
	 *            The Users that we wish to insert into the database
	 * @return Whether the save was successful
	 */
	public boolean saveNewUser(Users u) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = um.saveNewUser(u, con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @param subtaskBidId
	 *            The ID of the Bid that the group is making.
	 * @param t
	 *            The TaskSolverGroup making the Bid.
	 * @return Whether the save was successful
	 */
	public boolean saveNewTaskSolverGroup(int subtaskBidId, TaskSolverGroup t) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = um.saveNewTaskSolverGroup(subtaskBidId, t, con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @param taskId
	 *            The ID of the Task that the Subtask is a partition of.
	 * @param s
	 *            The Subtask we wish to create/save.
	 * @return Whether the save was successful or not.
	 */
	public boolean saveNewSubtask(int taskId, Subtask s) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = sam.saveNewSubtask(taskId, s, con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @param taskOwner
	 *            The ID of the user who has created the task.
	 * @param t
	 *            The Task that we wish to save/create.
	 * @return Whether the save was successful or not.
	 */
	public boolean saveNewTask(int taskOwner, Task t) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = tam.saveNewtask(taskOwner, t, con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @param sb
	 *            The SubtaskBid that we wish to save/create.
	 * @param subtaskId
	 *            The ID of the Subtask that is being bid on.
	 * @return Whether the save was successful or not.
	 */
	public boolean saveNewSubtaskBid(SubtaskBid sb, int subtaskId) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = sam.saveNewSubtaskBid(sb, subtaskId, con);
			// must also save the group of users making the bid
			status = um.saveNewTaskSolverGroup(sb.getSubtaskBidId(),
					sb.getTaskSolverGroup(), con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @param s
	 *            The Subtask that has been solved.
	 * @return Whether the save/edit was successful or not.
	 */
	public boolean SubtaskSolved(Subtask s) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = sam.SubtaskSolved(s.getSubtaskId(), con);
			// must also change it in the local cache.
			s.setSubtaskCompleted(true);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @param conversion
	 *            The 6 letter name of the conversion we are trying to save.
	 * @param rate
	 *            The floating point number that is the conversion rate.
	 * @return Whether the save was successful or not.
	 */
	public boolean saveNewConversion(String conversion, float rate) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = cm.saveNewConversion(conversion, rate, con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @param conversion
	 *            The 6 letter name of the conversion we are trying to edit.
	 * @param rate
	 *            The floating point number that is the conversion rate.
	 * @return Whether the edit was successful or not.
	 */
	public boolean editConversion(String conversion, float rate) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = cm.editConversion(conversion, rate, con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @param conversion
	 *            Name of the conversion we wish to retrieve
	 * @return The floating point number that is the conversion rate.
	 */
	public float getConversionRate(String conversion) {
		float rate = 0;
		Connection con = null;
		try {
			con = getConnection();
			rate = cm.getConversionRate(conversion, con);
		} finally {
			releaseConnection(con);
		}
		return rate;
	}

	/**
	 * @param sb
	 *            The Bid that has won the auction.
	 * @param subtaskId
	 *            The ID of the subtask that the bid has won.
	 * @return Whether the save was successful or not.
	 */
	public boolean selectWinningSubtaskBid(SubtaskBid sb, int subtaskId) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = sam.selectWinningSubtaskBid(sb, subtaskId, con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	public ArrayList<Task> getMyTasks(int userId) {
		ArrayList<Task> tasks = null;
		Connection con = null;
		try {
			con = getConnection();
			tasks = tam.getMyTasks(userId, con);
			for (int i = 0; i < tasks.size(); i++) {
				Task t = tasks.get(i);
				t.setTaskBids(tam.getTaskBidByTaskId(t.getTaskId(), con));
				t.setSubtasks(sam.getSubtasksByTaskId(t.getTaskId(), con));
			}
		} finally {
			releaseConnection(con);
		}
		return tasks;
	}

	/**
	 * @param tb
	 *            The TaskBid that has won the auction
	 * @param taskId
	 *            The ID of the Task that the bid has won.
	 * @return Whether the save was successful or not.
	 */
	public boolean selectWinningTaskBid(TaskBid tb, int taskId) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = tam.selectWinningTaskBid(taskId, tb, con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @return The TaskAuction of all Tasks available for bidding.
	 */
	public TaskAuction getTaskAuction() {
		TaskAuction ta = null;
		Connection con = null;
		try {
			con = getConnection();
			ta = tam.getTaskAuction(con);
			HashMap<Integer, Task> hm = ta.getAvailableTasks();
			Iterator<Integer> it = hm.keySet().iterator();
			while (it.hasNext()) {
				Task t = hm.get(it.next());
				// load the bids
				HashMap<Integer, TaskBid> tBids = tam.getTaskBidByTaskId(
						t.getTaskId(), con);
				Iterator<Integer> itBids = tBids.keySet().iterator();
				while (itBids.hasNext()) {
					TaskBid tb = tBids.get(itBids.next());
					// must set the Task Manager of each Bid.
					tb.setTaskManager(um.getTaskManagerByTaskBidId(
							tb.getTaskBidId(), con));
					tBids.put(tb.getTaskBidId(), tb);
					// no subtasks because these are all still available for
					// bidding.
				}
				t.setTaskOwner(um.getTaskOwner(t.getTaskId(), con));
				t.setTaskBids(tBids);
			}
			ta.setAvailableTasks(hm);
		} finally {
			releaseConnection(con);
		}
		return ta;
	}

	/**
	 * @param taskId
	 *            The ID number of the Task we are looking for.
	 * @return The Task we are looking for.
	 */
	public Task getTask(int taskId) {
		Task t = null;
		Connection con = null;
		try {
			con = getConnection();
			t = tam.getTask(taskId, con);
			HashMap<Integer, TaskBid> tBids = tam.getTaskBidByTaskId(
					t.getTaskId(), con);
			Iterator<Integer> it = tBids.keySet().iterator();
			while (it.hasNext()) {
				TaskBid tb = tBids.get(it.next());
				// must set the Task Manager of each Bid.
				tb.setTaskManager(um.getTaskManagerByTaskBidId(
						tb.getTaskBidId(), con));
				tBids.put(tb.getTaskBidId(), tb);
			}
			t.setTaskOwner(um.getTaskOwner(t.getTaskId(), con));
			t.setTaskBids(tBids);
		} finally {
			releaseConnection(con);
		}
		return t;
	}

	/**
	 * @param taskBidId
	 *            The ID number of the Bid we are looking for.
	 * @return The TaskBid we are looking for.
	 */
	public TaskBid getTaskBid(int taskBidId) {
		TaskBid tb = null;
		Connection con = null;
		try {
			con = getConnection();
			tb = tam.getTaskBidById(taskBidId, con);
			// get the Task Manager of the bid.
			tb.setTaskManager(um.getTaskManagerByTaskBidId(taskBidId, con));
		} finally {
			releaseConnection(con);
		}
		return tb;
	}

	/**
	 * @param tb
	 *            The TaskBid we wish to save/create.
	 * @param taskId
	 *            The ID number of the Task that the Bid is for.
	 * @return Whether the save was successful
	 */
	public boolean saveNewTaskBid(TaskBid tb, int taskId) {
		boolean status = false;
		Connection con = null;
		try {
			con = getConnection();
			status = tam.saveNewTaskBid(taskId, tb, con);
		} finally {
			releaseConnection(con);
		}
		return status;
	}

	/**
	 * @param subtaskId
	 *            The ID number of the Subtask.
	 * @return The User that is the Task Manager of the Subtask in question.
	 */
	public Users getTaskManagerBySubtaskId(int subtaskId) {
		Users tm = null;
		Connection con = null;
		try {
			con = getConnection();
			um.getTaskManagerBySubtaskId(subtaskId, con);
		} finally {
			releaseConnection(con);
		}
		return tm;
	}

	/**
	 * @return An ArrayList of all the Users in the database.
	 */
	public ArrayList<Users> getAllUsers() {
		ArrayList<Users> getAllUsers = null;
		Connection con = null;
		try {
			con = getConnection();
			getAllUsers = um.getAllUsers(con);
		} finally {
			releaseConnection(con);
		}
		return getAllUsers;

	}

	// === Connection specifics

	// --------------------Connection specifics----------------------
	/**
	 * @return The Connection to the database.
	 */
	private Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@delfi.lyngbyes.dk:1521:LUC", user, pass);
			con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		} catch (Exception e) {
			System.out.println("Epic Phail in DBFacade.getConnection()");
			System.out.println(e);
		}
		return con;
	}

	/**
	 * @param con
	 *            The Connection that is to be closed.
	 */
	public void releaseConnection(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
