package dataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import domain.TaskSolverGroup;
import domain.Users;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Mapper that retrieves all the User related objects from the database.
 */
public class UsersMapper {

	/**
	 * @param userId
	 *            is the Id number of the User
	 * @param con
	 *            is the Connection that is to be used.
	 * @return Users with the Id passed as a parameter
	 */
	public Users getUser(int userId, Connection con) {
		Users u = null;
		String query1 = "SELECT * FROM users WHERE userId = ?";
		// get user by id

		PreparedStatement statement = null;

		try {
			// === get user
			statement = con.prepareStatement(query1);
			statement.setInt(1, userId); // primary key value
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				u = new Users(userId, rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5));
			}

		} catch (Exception e) {
			System.out.println("Fail in UsersMapper - getUser");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return u;
	}

	/**
	 * @param subtaskBidId
	 *            ID number of the SubtaskBid that the group has bid on.
	 * @param con
	 *            The Connection to be used.
	 * @return TaskSolverGroup that has the bid specified by the ID number.
	 */
	public TaskSolverGroup getTaskSolverGroup(int subtaskBidId, Connection con) {
		TaskSolverGroup t = null;
		String query = "SELECT userId FROM groups WHERE subtaskBidId = ? "
				+ "ORDER BY userId DESC"; // get the userIds
		PreparedStatement statement = null;
		ArrayList<Users> users = new ArrayList<Users>(1);
		try {
			// === get location
			statement = con.prepareStatement(query);
			statement.setInt(1, subtaskBidId); // search value
			ResultSet rs = statement.executeQuery();
			while (rs.next()) { // keep getting the next user while there are
								// lines
				users.add(getUser(rs.getInt("userId"), con)); // add user to the
				// ArrayList
			}

		} catch (Exception e) {
			System.out.println("Fail in UsersMapper - getTaskSolverGroup");
			System.out.println(e.getMessage());
		}
		t = new TaskSolverGroup(users);
		return t;
	}

	/**
	 * @param u
	 *            The Users to be saved
	 * @param con
	 *            The Connection to be used.
	 * @return whether or not the save was successful.
	 */
	public boolean saveNewUser(Users u, Connection con) {
		if (u == null)
			return false;
		else {
			int rowsInserted = 0;
			String SQLString1 = "select userIdSeq.nextval from dual";
			String SQLString2 = "insert into Users values (?,?,?,?,?)";

			PreparedStatement statement = null;

			try {
				// == get unique userId
				statement = con.prepareStatement(SQLString1);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					u.setUserId(rs.getInt(1));
				}

				// == insert tuple
				statement = con.prepareStatement(SQLString2);
				statement.setInt(1, u.getUserId());
				statement.setInt(2, u.getSecurityLayer());
				statement.setString(3, u.getName());
				statement.setString(4, u.getPassword());
				statement.setString(5, u.getLocation());
				rowsInserted = statement.executeUpdate();
			} catch (Exception e) {
				System.out.println("OrderMapper went Kaput - saveNewUser");
				System.out.println(e.getMessage());
			}
			return rowsInserted == 1;
		}
	}

	/**
	 * @param subtaskId
	 *            ID number of the SubtaskBid that the TaskSolverGroup has made.
	 * @param t
	 *            TaskSolverGroup we are trying to save,
	 * @param con
	 *            The Connection to be used.
	 * @return Whether the save was successful or not.
	 */
	public boolean saveNewTaskSolverGroup(int subtaskId, TaskSolverGroup t,
			Connection con) {
		if (t == null)
			return false;
		else {
			int rowsInserted = 0;
			String SQLString = "insert into Groups values (?,?)";

			PreparedStatement statement = null;

			try {
				// == insert tuple
				statement = con.prepareStatement(SQLString);
				for (int i = 0; i < t.getUsers().size(); i++) {
					statement.setInt(1, subtaskId);
					statement.setInt(2, t.getUsers().get(i).getUserId());
					rowsInserted += statement.executeUpdate();
				}

			} catch (Exception e) {
				System.out
						.println("OrderMapper went Kaput - saveNewTaskSolvergroup");
				System.out.println(e.getMessage());
			}
			return rowsInserted == t.getUsers().size();
		}
	}

	/**
	 * @param taskId
	 *            ID number of the Task for which we are looking for the Owner
	 * @param con
	 *            The Connection to be used.
	 * @return Users we are looking for, the Task Owner of the Task.
	 */
	public Users getTaskOwner(int taskId, Connection con) {
		Users u = null;
		String query1 = "select u.userId, u.securityLayer, u.name, u.password, u.location "
				+ "from Users u, Tasks t where t.userId = u.userId and t.taskId = ? order by u.NAME asc";
		// get user by taskId

		PreparedStatement statement = null;

		try {
			// === get user
			statement = con.prepareStatement(query1);
			statement.setInt(1, taskId); // input taskId
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				u = new Users(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5));
			}

		} catch (Exception e) {
			System.out.println("Fail in UsersMapper - getTaskOwner");
			System.out.println(e.getMessage());
		}
		return u;
	}

	/**
	 * @param taskBidId
	 *            ID number of the TaskBid for which we are looking for the
	 *            Manager
	 * @param con
	 *            The Connection to be used
	 * @return The Users that is the Task Manager of the specified Task Bid.
	 */
	public Users getTaskManagerByTaskBidId(int taskBidId, Connection con) {
		Users u = null;
		String query1 = "select u.userId, u.securityLayer, u.name, u.password, u.location "
				+ "from Users u, TaskBids tb where tb.userId = u.userId and tb.taskBidId = ? "
				+ "order by u.NAME asc"; // get user by taskBidId

		PreparedStatement statement = null;

		try {
			// === get user
			statement = con.prepareStatement(query1);
			statement.setInt(1, taskBidId); // input taskBidId
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				u = new Users(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5));
			}

		} catch (Exception e) {
			System.out
					.println("Fail in UsersMapper - getTaskManagerByTaskBidId");
			System.out.println(e.getMessage());
		}
		return u;
	}

	/**
	 * @param subtaskId
	 *            ID number of the Subtask for which we are looking for the
	 *            Manager.
	 * @param con
	 *            The Connection to be used.
	 * @return Users which is the Task Manager of the specified Subtask.
	 */
	public Users getTaskManagerBySubtaskId(int subtaskId, Connection con) {
		Users u = null;
		String query1 = "select u.userId, u.securityLayer, u.name, u.password, u.location "
				+ "from Users u, TaskBids tb, Subtasks s WHERE tb.userId = u.userId "
				+ "and tb.taskbidWon = 'Y' and tb.taskId = s.taskId and s.subtaskId = ?";
		// get user by subtaskId

		PreparedStatement statement = null;

		try {
			// === get user
			statement = con.prepareStatement(query1);
			statement.setInt(1, subtaskId); // input subtaskId
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				u = new Users(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5));
			}
		} catch (Exception e) {
			System.out
					.println("Fail in UserMapper - getTaskManagerBySubtaskId");
			System.out.println(e.getMessage());
		}
		return u;
	}

	/**
	 * @param con
	 *            The Connection to be used.
	 * @return ArrayList of all the Users in the database.
	 */
	public ArrayList<Users> getAllUsers(Connection con) {
		ArrayList<Users> alu = new ArrayList<Users>(1);
		String query1 = "SELECT * FROM Users ORDER BY userId DESC";
		PreparedStatement statement = null;

		try {
			// === get user
			statement = con.prepareStatement(query1);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				alu.add(new Users(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5)));
			}

		} catch (Exception e) {
			System.out.println("Fail in UserMapper - getAllUsers");
			System.out.println(e.getMessage());
		}
		return alu;
	}

	/**
	 * @param u
	 *            Users that we want to edit.
	 * @param con
	 *            Connection to be used.
	 * @return boolean whether the save as successful or not.
	 */
	public boolean editUser(Users u, Connection con) {
		if (u == null)
			return false;
		else {
			int rowsUpdated = 0;
			String sql = "UPDATE Users SET name = ?, password = ?, securityLayer = ?, location = ? "
					+ "WHERE userId = ?"; // update query
			PreparedStatement statement = null;

			try {
				statement = con.prepareStatement(sql);
				statement.setString(1, u.getName());
				statement.setString(2, u.getPassword());
				statement.setInt(3, u.getSecurityLayer());
				statement.setString(4, u.getLocation());
				statement.setInt(5, u.getUserId());
				rowsUpdated = statement.executeUpdate();
			} catch (Exception e) {
				System.out.println("Fail in UsersMapper - editUser");
				System.out.println(e.getMessage());
			}
			return rowsUpdated == 1;
		}
	}
}
