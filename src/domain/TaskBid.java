package domain;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class represents a Bid for a Task.
 */
public class TaskBid {
	private int taskBidId, taskId;
	private Users taskManager;
	private float taskBidHour;
	private boolean taskBidWon;

	public TaskBid() {

	}

	public int getTaskBidId() {
		return taskBidId;
	}

	public void setTaskBidId(int taskBidId) {
		this.taskBidId = taskBidId;
	}

	public float getTaskBidHour() {
		return taskBidHour;
	}

	public void setTaskBidHour(float taskBidHour) {
		this.taskBidHour = taskBidHour;
	}

	public boolean isTaskBidWon() {
		return taskBidWon;
	}

	public void setTaskBidWon(boolean taskBidWon) {
		this.taskBidWon = taskBidWon;
	}

	public Users getTaskManager() {
		return taskManager;
	}

	public void setTaskManager(Users taskManager) {
		this.taskManager = taskManager;
	}

	/**
	 * @return the taskId
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId the taskId to set
	 */
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}