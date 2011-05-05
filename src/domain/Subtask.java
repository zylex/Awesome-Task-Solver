package domain;

import java.sql.Date;
import java.util.HashMap;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class represents a Subtask which is a partition of the a task
 */
public class Subtask {
	private int subtaskId, subtaskHour;
	private Users taskManager;
	private String subtaskName, subtaskDescription;
	private boolean subtaskAuctionEnded, subtaskCompleted;
	private Date subtaskCreated, subtaskDeadline;
	private HashMap<Integer, SubtaskBid> subtaskBids;

	public Subtask() {
	}

	public int getSubtaskId() {
		return subtaskId;
	}

	public void setSubtaskId(int subtaskId) {
		this.subtaskId = subtaskId;
	}

	public int getSubtaskHour() {
		return subtaskHour;
	}

	public void setSubtaskHour(int subtaskHour) {
		this.subtaskHour = subtaskHour;
	}

	public String getSubtaskName() {
		return subtaskName;
	}

	public void setSubtaskName(String subtaskName) {
		this.subtaskName = subtaskName;
	}

	public String getSubtaskDescription() {
		return subtaskDescription;
	}

	public void setSubtaskDescription(String subtaskDescription) {
		this.subtaskDescription = subtaskDescription;
	}

	public boolean isSubtaskAuctionEnded() {
		return subtaskAuctionEnded;
	}

	public void setSubtaskAuctionEnded(boolean subtaskAuctionEnded) {
		this.subtaskAuctionEnded = subtaskAuctionEnded;
	}

	public boolean isSubtaskCompleted() {
		return subtaskCompleted;
	}

	public void setSubtaskCompleted(boolean subtaskCompleted) {
		this.subtaskCompleted = subtaskCompleted;
	}

	public Date getSubtaskCreated() {
		return subtaskCreated;
	}

	public void setSubtaskCreated(Date subtaskCreated) {
		this.subtaskCreated = subtaskCreated;
	}

	public Date getSubtaskDeadline() {
		return subtaskDeadline;
	}

	public void setSubtaskDeadline(Date subtaskDeadline) {
		this.subtaskDeadline = subtaskDeadline;
	}

	public HashMap<Integer, SubtaskBid> getSubtaskBids() {
		return subtaskBids;
	}

	public void setSubtaskBids(HashMap<Integer, SubtaskBid> subtaskBids) {
		this.subtaskBids = subtaskBids;
	}

	/**
	 * @return the taskManager
	 */
	public Users getTaskManager() {
		return taskManager;
	}

	/**
	 * @param taskManager
	 *            the taskManager to set
	 */
	public void setTaskManager(Users taskManager) {
		this.taskManager = taskManager;
	}

	public boolean equals(Subtask s) {
		return subtaskId == s.getSubtaskId()
				&& subtaskHour == s.getSubtaskHour()
				&& subtaskDescription.equals(s.getSubtaskDescription())
				&& subtaskName.equals(s.getSubtaskName())
				&& subtaskCreated.equals(s.getSubtaskCreated())
				&& subtaskDeadline.equals(s.getSubtaskDeadline());
	}

}
