package domain;

import java.util.ArrayList;
import java.util.Collections;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class represents a group of Users that solve the Subtasks.
 */
public class TaskSolverGroup {
	private ArrayList<Users> users;

	public TaskSolverGroup(ArrayList<Users> users) {
		this.users = users;
	}

	public ArrayList<Users> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<Users> users) {
		this.users = users;
	}

	public String toString() {
		String res = "";
		if (users.isEmpty()) {
			res = "No users in this group yet!";
		} else {
			Collections.sort(users);
			for (int i = 0; i < users.size(); i++) {
				res += users.get(i).getUserId() + "\n";
			}
		}
		return res;
	}

}
