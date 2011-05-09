package domain;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class represents a Bid for a Task.
 */
public class TaskBid implements Comparable<TaskBid> {
	private int taskBidId, taskId, version;
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

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int compareTo(TaskBid tb) {
		return taskBidId - tb.getTaskBidId();
	}

}