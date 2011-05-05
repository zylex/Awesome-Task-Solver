package domain;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class represents a Bid for a Subtask.
 */
public class SubtaskBid {
	private int subtaskBidId, subtaskBidHours, subtaskId;
	private boolean subtaskBidWon;
	private TaskSolverGroup taskSolverGroup;

	public SubtaskBid() {

	}

	public int getSubtaskBidId() {
		return subtaskBidId;
	}

	public void setSubtaskBidId(int subtaskBidId) {
		this.subtaskBidId = subtaskBidId;
	}

	public int getSubtaskBidHours() {
		return subtaskBidHours;
	}

	public void setSubtaskBidHours(int subtaskBidHours) {
		this.subtaskBidHours = subtaskBidHours;
	}

	public boolean isSubtaskBidWon() {
		return subtaskBidWon;
	}

	public void setSubtaskBidWon(boolean subtaskBidWon) {
		this.subtaskBidWon = subtaskBidWon;
	}

	public TaskSolverGroup getTaskSolverGroup() {
		return taskSolverGroup;
	}

	public void setTaskSolverGroup(TaskSolverGroup taskSolverGroup) {
		this.taskSolverGroup = taskSolverGroup;
	}

	/**
	 * @return the subtaskId
	 */
	public int getSubtaskId() {
		return subtaskId;
	}

	/**
	 * @param subtaskId the subtaskId to set
	 */
	public void setSubtaskId(int subtaskId) {
		this.subtaskId = subtaskId;
	}

}
