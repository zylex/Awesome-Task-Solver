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
	 * @param userId is the Id number of the User
	 * @param con is the Connection that is to be used.
	 * @return Users with the Id passed as a parameter
	 */
	public Users getUser(int userId, Connection con) {
		Users u = null;
		String query1 = "select * from Users where userId = ? order by NAME asc";
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
			System.out.println("Fail in UserMapper - getUser");
			System.out.println(e.getMessage());
		}
		return u;
	}

	/**
	 * @param subtaskBidId
	 * @param con
	 * @return
	 */
	public TaskSolverGroup getTaskSolverGroup(int subtaskBidId, Connection con) {
		TaskSolverGroup t = null;
		String query = "select userId from Groups where subtaskBidId = ?"; // get
																			// the
																			// userIds
		PreparedStatement statement = null;
		ArrayList<Users> users = new ArrayList<Users>(1);
		try {
			// === get location
			statement = con.prepareStatement(query);
			statement.setInt(1, subtaskBidId); // search value
			ResultSet rs = statement.executeQuery();
			while (rs.next()) { // keep getting the next user while there are
								// lines
				users.add(getUser(rs.getInt(1), con)); // add user to the
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
	 * @param con
	 * @return
	 */
	public boolean saveNewUser(Users u, Connection con) {
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

	/**
	 * @param subtaskId
	 * @param t
	 * @param con
	 * @return
	 */
	public boolean saveNewTaskSolverGroup(int subtaskId, TaskSolverGroup t,
			Connection con) {
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

	/**
	 * @param taskId
	 * @param con
	 * @return
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
			System.out.println("Fail in UserMapper - getUser");
			System.out.println(e.getMessage());
		}
		return u;
	}

	/**
	 * @param taskBidId
	 * @param con
	 * @return
	 */
	public Users getTaskManagerByTaskBidId(int taskBidId, Connection con) {
		Users u = null;
		String query1 = "select u.userId, u.securityLayer, u.name, u.password, u.location "
				+ "from Users u, TaskBids tb where tb.userId = u.userId and tb.taskBidId = ? order by u.NAME asc";
		// get user by taskId

		PreparedStatement statement = null;

		try {
			// === get user
			statement = con.prepareStatement(query1);
			statement.setInt(1, taskBidId); // input taskId
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				u = new Users(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5));
			}

		} catch (Exception e) {
			System.out.println("Fail in UserMapper - getUser");
			System.out.println(e.getMessage());
		}
		return u;
	}

	/**
	 * @param subtaskId
	 * @param con
	 * @return
	 */
	public Users getTaskManagerBySubtaskId(int subtaskId, Connection con) {
		Users u = new Users();
		String query1 = "select u.userId, u.securityLayer, u.name, u.password, u.location "
				+ "from Users u, TaskBids tb, Subtasks s where tb.userId = u.userId "
				+ "and tb.taskbidWon = 'Y' and tb.taskId = s.taskId and s.subtaskId = ? order by u.NAME asc";
		// get user by taskId

		PreparedStatement statement = null;

		try {
			// === get user
			statement = con.prepareStatement(query1);
			statement.setInt(1, subtaskId); // input taskId
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				u.setUserId(rs.getInt(1));
				u.setSecurityLayer(rs.getInt(2));
				u.setName(rs.getString(3));
				u.setPassword(rs.getString(4));
				u.setLocation(rs.getString(5));
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
	 * @return
	 */
	public ArrayList<Users> getAllUsers (Connection con)
	{
	ArrayList<Users> alu = new ArrayList<Users>(1);
		String query1 = "select * from Users order by NAME asc";
		PreparedStatement statement = null;

		try {
			// === get user
			statement = con.prepareStatement(query1);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				alu.add(new Users(rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getString(4), rs.getString(5))) ;
			}

		} catch (Exception e) {
			System.out.println("Fail in UserMapper - getAllUser");
			System.out.println(e.getMessage());
		}
		return alu;
	}
	
}
