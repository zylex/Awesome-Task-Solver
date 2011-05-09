package domain;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class represents a Bid for a Subtask.
 */
public class SubtaskBid implements Comparable<SubtaskBid> {
	private int subtaskBidId, subtaskBidHours, subtaskId, version;
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
	public int compareTo(SubtaskBid sb) {
		return subtaskBidId - sb.getSubtaskBidId();
	}

}
