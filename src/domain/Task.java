package domain;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class represents a Task.
 */
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class Task {

	private int taskId;
	private Users taskOwner;
	private String taskDescription, taskName;
	private Date taskCreated, deadlineBid;
	private double price;
	private boolean taskAuctionEnded;
	private HashMap<Integer, TaskBid> taskBids;
	private HashMap<Integer, Subtask> subtasks;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getTaskCreated() {
		return taskCreated;
	}

	public void setTaskCreated(Date i) {
		this.taskCreated = i;
	}

	public Date getDeadlineBid() {
		return deadlineBid;
	}

	public void setDeadlineBid(Date deadlineBid) {
		this.deadlineBid = deadlineBid;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isTaskAuctionEnded() {
		return taskAuctionEnded;
	}

	public void setTaskAuctionEnded(boolean taskAuctionEnded) {
		this.taskAuctionEnded = taskAuctionEnded;
	}

	public HashMap<Integer, TaskBid> getTaskBids() {
		return taskBids;
	}

	public void setTaskBids(HashMap<Integer, TaskBid> taskBids) {
		this.taskBids = taskBids;
	}

	/**
	 * @return the taskOwner
	 */
	public Users getTaskOwner() {
		return taskOwner;
	}

	/**
	 * @param taskOwner
	 *            the taskOwner to set
	 */
	public void setTaskOwner(Users taskOwner) {
		this.taskOwner = taskOwner;
	}

	/**
	 * @return the subtasks
	 */
	public HashMap<Integer, Subtask> getSubtasks() {
		return subtasks;
	}

	/**
	 * @param subtasks
	 *            the subtasks to set
	 */
	public void setSubtasks(HashMap<Integer, Subtask> subtasks) {
		this.subtasks = subtasks;
	}

	/**
	 * @return
	 */
	public double getCompleted() {
		if (subtasks == null || subtasks.size() == 0)
			return 0;
		ArrayList<Subtask> st = new ArrayList<Subtask>(subtasks.values());
		int size = st.size();
		int count = 0;
		for (int i = 0; i < size; i++) {
			if (st.get(i).isSubtaskCompleted())
				count++;
		}
		double completed = count / size * 100;
		return completed;
	}

}