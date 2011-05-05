package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import dataSource.DBFacade;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * The controller communicates with the database and makes all necessary changes
 */
/**
 * @author zylex
 * 
 */
public class DomainController {
	private Users currentUser;
	private TaskAuction taskAuction;
	private SubtaskAuction subtaskAuction;
	private TaskSolverGroup taskSolverGroup;
	private DBFacade db;

	// is a Singleton
	private static DomainController instance;

	/**
	 * private constructor because it is a singleton
	 */
	private DomainController() {
		db = DBFacade.getInstance(); // set dbFacade
		currentUser = null;
	}

	/**
	 * to be run after login so as to load from the database.
	 */
	public void initAfterLogin() {
		getTaskAuctionFromDB();
		getSubtaskAuctionFromDB();
	}

	/**
	 * @return the single instance of the DomainController
	 */
	public static DomainController getInstance() {
		if (instance == null)
			instance = new DomainController();
		return instance;
	}

	/**
	 * @return currentUser
	 */
	public Users getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currentUser
	 *            The Users to be set as the currentUser
	 */
	public void setCurrentUser(Users currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * @return taskAuction
	 */
	public TaskAuction getTaskAuction() {
		return taskAuction;
	}

	/**
	 * @param taskAuction
	 *            The TaskAuction to be set as taskAuction
	 */
	public void setTaskAuction(TaskAuction taskAuction) {
		this.taskAuction = taskAuction;
	}

	/**
	 * @return subtaskAuction
	 */
	public SubtaskAuction getSubtaskAuction() {
		return subtaskAuction;
	}

	/**
	 * @param subtaskAuction
	 *            The SubtaskAuction to set as the subtaskAuction
	 */
	public void setSubtaskAuction(SubtaskAuction subtaskAuction) {
		this.subtaskAuction = subtaskAuction;
	}

	/**
	 * @param userId
	 *            ID number of the Users we are looking for.
	 * @return Users with the specified ID number.
	 */
	public Users getUser(int userId) {
		return db.getUser(userId);
	}

	/**
	 * @param userId
	 *            ID number of the user who is trying to login
	 * @param password
	 *            String password of the user who is trying to login
	 * @return boolean whether the information was correct or not.
	 */
	public boolean login(int userId, String password) {
		boolean status = false;
		Users u = db.getUser(userId);
		// check if the user exists.
		if (u == null)
			return false;
		// check if the password matches the users.
		if (password.equals(u.getPassword()))
			status = true;
		return status;
	}

	/**
	 * Load the TaskAuction from the database.
	 */
	public void getTaskAuctionFromDB() {
		setTaskAuction(db.getTaskAuction());
	}

	/**
	 * Load the SubtaskAuction from the database.
	 */
	public void getSubtaskAuctionFromDB() {
		setSubtaskAuction(db.getSubtaskAuction());
	}

	/**
	 * @return String with a list of information about all the available Tasks.
	 */
	public String showAllTasks() {
		// String.Format() aligns the text
		String display = String.format(
				"|%1$-10s|%2$-10s|%3$-20s|%4$-20s|%5$-20s|%6$-15s|\n",
				"Task ID", "User ID", "Task Name", "Created", "Deadline",
				"Price");
		ArrayList<Task> availableTasks = new ArrayList<Task>(taskAuction
				.getAvailableTasks().values());
		int size = availableTasks.size();
		for (int i = 0; i < size; i++) {
			Task t = availableTasks.get(i);
			// add the information to the String to be returned.
			display += String.format(
					"|%1$-10s|%2$-10s|%3$-20s|%4$-20s|%5$-20s|%6$-15.2f|\n",
					t.getTaskId(), t.getTaskOwner().getUserId(),
					t.getTaskName(), t.getTaskCreated(), t.getDeadlineBid(),
					t.getPrice());
		}
		return display;
	}

	/**
	 * @return String with a list of information about all the available
	 *         Subtasks.
	 */
	public String showAllSubtasks() {
		// String.Format() aligns the text
		String display = String.format(
				"|%1$-15s|%2$-15s|%3$-30s|%4$-20s|%5$-20s|\n", "Subtask ID",
				"User ID", "Subtask Name", "Created", "Deadline");
		ArrayList<Subtask> subtasks = new ArrayList<Subtask>(subtaskAuction
				.getAvailableSubtasks().values());
		for (int i = 0; i < subtasks.size(); i++) {
			Subtask s = subtasks.get(i);
			// add the information to the String to be returned.
			display += String.format(
					"|%1$-15s|%2$-15s|%3$-30s|%4$-20s|%5$-20s|\n",
					s.getSubtaskId(), s.getTaskManager().getUserId(),
					s.getSubtaskName(), s.getSubtaskCreated(),
					s.getSubtaskDeadline());
		}
		return display;
	}

	/**
	 * @param taskId
	 *            ID number of the Task we are looking for.
	 * @return String with information to be displayed about the Task.
	 */
	public String showTask(int taskId) {
		Task t = taskAuction.getAvailableTasks().get(taskId);
		if (t == null) // if not in cache look in database.
			t = db.getTask(taskId);
		String display;
		if (t == null) { // if not in database either return error message.
			display = "Could not find Task - showTask";
		} else {
			// add the information to the String to be returned.
			display = "Task ID: " + t.getTaskId() + "\nUser ID: "
					+ t.getTaskOwner().getUserId() + "\nTask Name: "
					+ t.getTaskName() + "\nTask Description: "
					+ t.getTaskDescription() + "\nCreated: "
					+ t.getTaskCreated() + "\nDeadline: " + t.getDeadlineBid()
					+ "\nPrice: " + t.getPrice() + "\nCompleted: "
					+ t.getCompleted() + "%" + "\nBid IDs: ";
			ArrayList<TaskBid> taskbids = new ArrayList<TaskBid>(t
					.getTaskBids().values());
			for (int i = 0; i < taskbids.size(); i++) {
				display += "\n\t" + taskbids.get(i).getTaskBidId();
			}
		}
		return display;
	}

	/**
	 * @param subtaskId
	 *            ID number of the Subtask we are looking for
	 * @return String with the information to be displayed of the Subtask.
	 */
	public String showSubtask(int subtaskId) {
		Subtask s = subtaskAuction.getAvailableSubtasks().get(subtaskId);
		if (s == null) // if not in cache, look in database
			s = db.getSubtask(subtaskId);
		String display;
		if (s == null) { // if not in database either return error message.
			display = "Could not find Subtask - showSubtask.";
		} else {
			// turn boolean into a String.
			String completed;
			if (s.isSubtaskCompleted())
				completed = "Yes";
			else
				completed = "No";
			// add the information to the String to be returned.
			display = "Subtask ID: " + s.getSubtaskId() + "\nUser ID: "
					+ s.getTaskManager().getUserId() + "\nSubtask Name: "
					+ s.getSubtaskName() + "\nSubtask Description: "
					+ s.getSubtaskDescription() + "\nCreated: "
					+ s.getSubtaskCreated() + "\nDeadline: "
					+ s.getSubtaskDeadline() + "\nCompleted: " + completed
					+ "\nBid IDs: ";
			ArrayList<SubtaskBid> al = new ArrayList<SubtaskBid>(s
					.getSubtaskBids().values());
			for (int i = 0; i < al.size(); i++) {
				display += "\n\t" + al.get(i).getSubtaskBidId();
			}
		}
		return display;
	}

	/**
	 * @param taskId
	 *            The ID number of the Task that this new Subtask belongs to.
	 * @param subtaskName
	 *            The name of the new Subtask.
	 * @param subtaskDescription
	 *            The description of the new Subtask.
	 * @param subtaskHour
	 *            The number of hours available for the new Subtask.
	 * @param deadlineBid
	 *            The end of the auction.
	 * @return String with information on the status of the save.
	 */
	public String createSubtask(int taskId, String subtaskName,
			String subtaskDescription, int subtaskHour, Date deadlineBid) {
		String result = "";
		Task t = db.getTask(taskId);
		// Task must exist in the database
		if (t == null) {
			result = "Could not find the Task - createSubtask";
		} else {
			// Task must not still be on auction
			if (!t.isTaskAuctionEnded()) {
				result = "This Task is still available for auction - createSubtask";
			} else {
				Users u = null;
				ArrayList<TaskBid> taskbids = new ArrayList<TaskBid>(t
						.getTaskBids().values());
				for (int i = 0; i < taskbids.size(); i++) {
					TaskBid tb = taskbids.get(i);
					if (tb.isTaskBidWon()) {
						u = tb.getTaskManager();
						break;
					}
				}
				// current user must also be the task manager of the task.
				if (!currentUser.equals(u)) {
					result = "You are not the Manager of this Task - createSubtask";
				} else {
					// create object and add the provided information
					Subtask s = new Subtask();
					s.setSubtaskName(subtaskName);
					s.setSubtaskAuctionEnded(false);
					s.setSubtaskCompleted(false);
					s.setSubtaskDescription(subtaskDescription);
					s.setSubtaskHour(subtaskHour);
					s.setSubtaskDeadline(deadlineBid);
					s.setSubtaskBids(new HashMap<Integer, SubtaskBid>());
					// attempt to save to database
					if (db.saveNewSubtask(taskId, s)) {
						result = "Subtask Successfully created - createSubtask";
						t.getSubtasks().put(s.getSubtaskId(), s);
						// must also add it to the subtask auction
						getSubtaskAuction().getAvailableSubtasks().put(
								s.getSubtaskId(), s);
					} else
						// if unsuccessful, return error message
						result = "Save went wrong - createSubtask";
				}
			}
		}
		return result;
	}

	/**
	 * @param subtaskId
	 *            ID number of the Subtask we are trying to edit (Cannot be
	 *            edited).
	 * @param subtaskName
	 *            New name of the Subtask we are editing.
	 * @param subtaskDescription
	 *            New description of the Subtask we are editing.
	 * @param subtaskHour
	 *            New number of hours for the Subtask we are creating.
	 * @param deadlineBid
	 *            New end date for the auction of the Subtask.
	 * @return String with the status of the edit.
	 */
	public String editSubTask(int subtaskId, String subtaskName,
			String subtaskDescription, int subtaskHour, Date deadlineBid) {
		Subtask s = new Subtask();
		s.setSubtaskId(subtaskId);
		s.setSubtaskName(subtaskName);
		s.setSubtaskDescription(subtaskDescription);
		s.setSubtaskHour(subtaskHour);
		s.setSubtaskDeadline(deadlineBid);
		String output = "";
		Subtask cache = subtaskAuction.getAvailableSubtasks().get(subtaskId);
		// make sure the Subtask exists
		if (cache == null)
			output = "Could not find the Subtask!";
		else {
			// make sure the currentUser is the Task Manager of the Subtask.
			if (!currentUser.equals(cache.getTaskManager()))
				output = "You are not the Manager of this Subtask!";
			else {
				if (db.editSubtask(cache, s)) { // save is successful.
					output = "Success! You subtask has successfully been edited.";
					getSubtaskAuctionFromDB();
				} else {
					output = "Fail! Could not save the subtask.";
				}
			}
		}
		return output;
	}

	/**
	 * @param taskId
	 *            ID number of the task we are trying to create.
	 * @param taskName
	 *            Name of the Task we are trying to create.
	 * @param taskDescription
	 *            Description of the Task we are trying to create.
	 * @param price
	 *            Price of the Task we are trying to create.
	 * @param deadline
	 *            End date for the auction of the Task we are trying to create.
	 * @return String representing the status of the save.
	 */
	public String createTask(int taskId, String taskName,
			String taskDescription, int price, Date deadline) {
		Task t = new Task();
		t.setTaskName(taskName);
		t.setTaskAuctionEnded(false);
		t.setTaskDescription(taskDescription);
		t.setPrice(price);
		t.setDeadlineBid(deadline);
		t.setTaskBids(new HashMap<Integer, TaskBid>());
		t.setSubtasks(new HashMap<Integer, Subtask>());
		String result;
		if (db.saveNewTask(currentUser.getUserId(), t)) {
			result = "Task Successfully created - createTask";
			getTaskAuction().getAvailableTasks().put(t.getTaskId(), t);
		} else
			result = "Save went wrong - createTask";
		return result;
	}

	/**
	 * @param subtaskId
	 *            ID number of the Subtask that is being bid on.
	 * @param subtaskBidHours
	 *            Number of hours for the bid.
	 * @return String with information about the save.
	 */
	public String makeSubtaskBid(int subtaskId, int subtaskBidHours) {
		String result = "";
		// must have created a group first.
		if (taskSolverGroup == null || taskSolverGroup.getUsers().isEmpty()) {
			result = "Must create a group first - makeSubtaskBid";
		} else {
			Subtask s = subtaskAuction.getAvailableSubtasks().get(subtaskId);
			// make sure the Subtask exists.
			if (s == null) {
				result = "Subtask does not exist or is not on auction - makeSubtaskBid";
			} else {
				SubtaskBid sb = new SubtaskBid();
				sb.setSubtaskBidHours(subtaskBidHours);
				sb.setTaskSolverGroup(taskSolverGroup);
				if (db.saveNewSubtaskBid(sb, subtaskId)) { // save was
															// successful.
					result = "Subtask Bid successfully created - makeSubtaskBid";
					s.getSubtaskBids().put(sb.getSubtaskBidId(), sb);
				} else
					result = "Save Went Wrong - makeSubtaskBid";
			}
		}
		return result;
	}

	/**
	 * @param subtaskBidId
	 *            ID number of the SubtaskBid we are looking for.
	 * @return String with information about the SubtaskBid.
	 */
	public String showSubtaskBid(int subtaskBidId) {
		// get subtaskBid from the database.
		SubtaskBid sb = db.getSubtaskBid(subtaskBidId);
		String result = "";
		if (sb == null) {
			result = "Could not find Subtask Bid with that ID - showSubtaskBid";
		} else {
			result = "User Id: ";
			ArrayList<Users> u = sb.getTaskSolverGroup().getUsers();
			for (int i = 0; i < u.size(); i++) {
				result += "\n\t" + u.get(i).getUserId();
			}
			result += "\nSubtask Bid Id: " + sb.getSubtaskBidId()
					+ "\nSubtask Bid Hours: " + sb.getSubtaskBidHours()
					+ "\nSubtaskId: " + sb.getSubtaskId();
		}
		return result;
	}

	/**
	 * @param taskId
	 *            ID number of the Task that is being bid on.
	 * @param taskBidHour
	 *            Number of hours the bid is.
	 * @return
	 */
	public String makeTaskbid(int taskId, int taskBidHour) {
		TaskBid tb = new TaskBid();
		tb.setTaskBidHour(taskBidHour);
		tb.setTaskManager(currentUser);
		String result; // to be returned
		// look for the task in the cache
		Task t = taskAuction.getAvailableTasks().get(taskId);
		// if task was not in cache look in the database.
		if (t == null)
			t = db.getTask(taskId);
		// if it is Still null then return an error message
		if (t == null)
			result = "Task does not exist or is not on auction anymore - makeTaskBid";
		else {
			t.getTaskBids().put(tb.getTaskBidId(), tb);
			t = taskAuction.getAvailableTasks().get(taskId);
			if (db.saveNewTaskBid(tb, taskId)) { // sae was successful
				result = "TaskBid successfully created - makeTaskBid";
				t.getTaskBids().put(tb.getTaskBidId(), tb);
			} else
				result = "Save went wrong - makeTaskBid";
		}
		return result;
	}

	/**
	 * @param taskBidId
	 *            ID number of the TaskBid we are looking for.
	 * @return String with information about the TaskBid.
	 */
	public String showTaskBid(int taskBidId) {
		TaskBid tb = db.getTaskBid(taskBidId);
		String display = "";
		// if the TaskBid does not exist, return an error message.
		if (tb == null) {
			display = "Could not find a Task Bid with that ID - showTaskBid";
		} else {
			display = "TaskBid ID: " + tb.getTaskBidId() + "\nUser ID: "
					+ tb.getTaskManager().getUserId() + "\nOffer: "
					+ tb.getTaskBidHour() + " Hours" + "\nTask Id: "
					+ tb.getTaskId();
		}
		return display;
	}

	/**
	 * @param securityLayer
	 *            securityLayer of the User we are trying to create.
	 * @param name
	 *            name of the User we are trying to create.
	 * @param password
	 *            password of the User we are trying to create.
	 * @param location
	 *            location of the User we are trying to create.
	 * @return String representing the success of the save.
	 */
	public String createUsers(int securityLayer, String name, String password,
			String location) {
		Users u = new Users();
		u.setSecurityLayer(securityLayer);
		u.setName(name);
		u.setPassword(password);
		u.setLocation(location);
		String result;
		if (db.saveNewUser(u)) { // save was successful
			result = "User Successfully created - createUsers" + "\nUser ID: "
					+ u.getUserId() + "\nName: " + u.getName() + "\nPassword"
					+ u.getPassword() + "\nLocation" + u.getLocation();
		} else
			result = "Save went wrong - createUsers";
		return result;
	}

	/**
	 * @return String with information on all the Users in the database.
	 */
	public String showAllUsers() {
		String display = String.format(
				"|%1$-15s|%2$-15s|%3$-25s|%4$-20s|%5$-20s|\n", "User ID",
				"Security", "Name", "Password", "Location");
		// load the list from the database.
		ArrayList<Users> uss = db.getAllUsers();
		for (int i = 0; i < uss.size(); i++) {
			Users u = uss.get(i);
			display += String.format(
					"|%1$-15s|%2$-15s|%3$-25s|%4$-20s|%5$-20s|\n",
					u.getUserId(), u.getSecurityLayer(), u.getName(),
					u.getPassword(), u.getLocation());
		}
		return display;
	}

	/**
	 * @param userId
	 *            ID number of the User we wish to add to the group.
	 * @return String of userId's in the group
	 */
	public String addUserToGroup(int userId) {
		String display = "";
		if (taskSolverGroup == null) {
			display = "Must create group first";
		} else {
			taskSolverGroup.getUsers().add(db.getUser(userId));
			display = taskSolverGroup.toString();
		}
		return display;
	}

	/**
	 * @return String that the group was created.
	 */
	public String newGroup() {
		taskSolverGroup = new TaskSolverGroup(new ArrayList<Users>(1));
		return "New group created,\nadd new user IDs.";
	}

	/**
	 * @param taskId
	 *            ID number of the Task for which we wish to select a TaskBid.
	 * @param taskBidId
	 *            ID number of the TaskBid that we wish to select.
	 * @return String describing the success of the save.
	 */
	public String selectTaskBidWinner(int taskId, int taskBidId) {
		String res = "";
		// get the TaskBid from database.
		TaskBid tb = db.getTaskBid(taskBidId);
		if (tb == null) // taskId was incorrect.
			res = "Could not find the TaskBid - selectTaskBidWinner";
		else if (taskId != tb.getTaskId()) // taskId and taskBidId do not match.
			res = "The TaskBid ID number does not belong to the "
					+ "specified Task ID number - selectTaskBidWinner";
		else if (tb.isTaskBidWon()) // the TaskBid has already won.
			res = "This TaskBid is already the winner - selectTaskBidWinner";
		else {
			// load Task from cache.
			Task t = taskAuction.getAvailableTasks().get(taskId);
			if (t == null) // not in the cache.
				t = db.getTask(taskId); // so load from the database.
			if (t == null) // not in the database either.
				res = "Could not find the task or it is not available - selectTaskBidWinner";
			else if (!currentUser.equals(t.getTaskOwner())) { // currentUser
																// does not have
																// access
																// rights.
				res = "You are not the Owner of this Task - selectTaskBidWinner";
			} else {
				ArrayList<TaskBid> taskbids = new ArrayList<TaskBid>(t
						.getTaskBids().values());
				boolean ended = false;
				for (int i = 0; i < taskbids.size(); i++) {
					if (taskbids.get(i).isTaskBidWon()) { // a different TaskBid
															// has won.
						ended = true;
					}
				}
				if (ended == true) {
					res = "Task already has a winner!";
				} else if (db.selectWinningTaskBid(tb, taskId)) { // save
																	// successful.
					String won = "";
					if (tb.isTaskBidWon())
						won = "Yes";
					else
						won = "No";
					Users u = tb.getTaskManager();
					// enter the result
					res = "Successfully selected Task Bid " + taskBidId
							+ " as the winning bid for Task " + taskId + "!"
							+ "\nTask ID: " + taskId + "\nTaskBid ID: "
							+ taskBidId + "\nHours: " + tb.getTaskBidHour()
							+ "\nWon: " + won + "\nUser:\tID: " + u.getUserId()
							+ "\n\t\tName: " + u.getName() + "\n\t\tLocation: "
							+ u.getLocation();
					tb.setTaskBidWon(true);
					t.setTaskAuctionEnded(true);
				} else { // save unsuccessful
					res = "Something went wrong - selectTaskBidWinner";
				}
			}
		}
		return res;
	}

	/**
	 * @param subtaskId
	 *            ID number of the Subtask for which we wish to select a
	 *            SubtaskBid.
	 * @param subtaskBidId
	 *            ID number of the SubtaskBid which we wish to select.
	 * @return String representing the success of the save.
	 */
	public String selectsubtaskBidWinner(int subtaskId, int subtaskBidId) {
		String res = "";
		// load the SubtaskBid from the database
		SubtaskBid sb = db.getSubtaskBid(subtaskBidId);
		if (sb == null) // subtaskBidId was incorrect
			res = "Could not find the SubtaskBid - selectSubtaskBidWinner";
		else if (subtaskId != sb.getSubtaskId()) // subtaskId and subtaskBidId
													// do not match
			res = "The SubaskBid ID number does not belong to the "
					+ "specified Subtask ID number - selectSubtaskBidWinner";
		else if (sb.isSubtaskBidWon()) // is already the winner.
			res = "This SubtaskBid is already the winner - selectSubtaskBidWinner";
		else {
			// load Subtask from cache
			Subtask s = subtaskAuction.getAvailableSubtasks().get(subtaskId);
			if (s == null) // not in the cache
				s = db.getSubtask(subtaskId); // so load from the database.
			if (s == null) // not in the database either.
				res = "Could not find the task or it is not available - selectSubtaskBidWinner";
			else if (!currentUser.equals(s.getTaskManager())) { // currentUser
																// is not
																// allowed to
																// edit this
																// Subtask
				res = "You are not the Manager of this Subtask - selectSubtaskBidWinner";
			} else {
				ArrayList<SubtaskBid> subtaskbids = new ArrayList<SubtaskBid>(s
						.getSubtaskBids().values());
				boolean ended = false;
				for (int i = 0; i < subtaskbids.size(); i++) {
					if (subtaskbids.get(i).isSubtaskBidWon()) { // a SubtaskBid
																// is the winner
						ended = true;
					}
				}
				if (ended == true) { // already a winner
					res = "Subtask already has a winner!";
				} else if (db.selectWinningSubtaskBid(sb, subtaskId)) { // save successful
					String won = "";
					if (sb.isSubtaskBidWon())
						won = "Yes";
					else
						won = "No";
					ArrayList<Users> gr = sb.getTaskSolverGroup().getUsers();
					// enter the String to return.
					res = "Successfully selected Subtask Bid " + subtaskBidId
							+ " as the winning bid for Subtask " + subtaskId
							+ "!" + "\nSubtask ID: " + subtaskId
							+ "\nSubtaskBid ID: " + subtaskBidId + "\nHours: "
							+ sb.getSubtaskBidHours() + "\nWon: " + won
							+ "\nUsers:\t";
					for (int i = 0; i < gr.size(); i++) {
						Users u = gr.get(i);
						res += "ID: " + u.getUserId() + "\n\t\tName: "
								+ u.getName() + "\n\t\tLocation: "
								+ u.getLocation() + "\n\t\t";
					}
					// update the cache
					sb.setSubtaskBidWon(true);
					s.setSubtaskAuctionEnded(true);
				} else { // save unsuccessful
					res = "Something went wrong - selectSubtaskBidWinner";
				}
			}
		}
		return res;
	}

	/**
	 * @return String with Task information belonging to the currentUser
	 */
	public String showMyTasks() {
		String display = String.format(
				"|%1$-10s|%2$-10s|%3$-20s|%4$-10s|%5$-10s|%6$-20s|%6$-15s|\n",
				"Task ID", "Completed", "Task Name", "Bids", "Assigned",
				"Deadline", "Price");
		ArrayList<Task> tasks = db.getMyTasks(currentUser.getUserId());
		// user has no Tasks
		if (tasks == null || tasks.size() < 0)
			display = "You have no tasks.";
		else {
			for (int i = 0; i < tasks.size(); i++) {
				Task t = tasks.get(i);
				String assigned;
				if (t.isTaskAuctionEnded())
					assigned = "Yes";
				else
					assigned = "No";
				// ad the information to the returned String
				display += String
						.format("|%1$-10s|%2$-10s|%3$-20s|%4$-10s|%5$-10s|%6$-20s|%6$-15s|\n",
								t.getTaskId(), t.getCompleted(),
								t.getTaskName(), t.getTaskBids().size(),
								assigned, t.getDeadlineBid(), t.getPrice());
			}
		}
		return display;
	}
}
