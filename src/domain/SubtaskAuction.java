package domain;

import java.util.HashMap;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class contains a HashMap of all the Subtasks that are available on the auction.
 */
public class SubtaskAuction {
	private HashMap<Integer, Subtask> availableSubtasks;
	
	public SubtaskAuction(HashMap<Integer, Subtask> availableSubtasks) {
		this.availableSubtasks = availableSubtasks;
	}

	public HashMap<Integer, Subtask> getAvailableSubtasks() {
		return availableSubtasks;
	}

	public void setAvailableSubtasks(HashMap<Integer, Subtask> availableSubtasks) {
		this.availableSubtasks = availableSubtasks;
	}
	
	

}
