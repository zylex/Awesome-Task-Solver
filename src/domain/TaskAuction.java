package domain;

import java.util.HashMap;
/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class contains a HashMap of all the Tasks that are available for bidding.
 */
public class TaskAuction {
	private HashMap<Integer, Task> availableTasks;

	public TaskAuction(HashMap<Integer, Task> availableTasks) {
		this.availableTasks = availableTasks;
	}

	public HashMap<Integer, Task> getAvailableTasks() {
		return availableTasks;
	}

	public void setAvailableTasks(HashMap<Integer, Task> availableTasks) {
		this.availableTasks = availableTasks;
	}
	
	public String toString() {
		return availableTasks.toString();
	}

}