package domain;

/**
 * @author Anthony Arena, Jonathan Anastasiou, John Frederiksen, Daniel Spitzer
 */
/**
 * Class represents a User of the program.
 */
public class Users implements Comparable<Users>{
	private int userId, securityLayer;
	private String name, password, location;
	
	public Users() { 
	}

	public Users(int userId, int securityLayer, String name, String password,
			String location) {
		this.userId = userId;
		this.securityLayer = securityLayer;
		this.name = name;
		this.password = password;
		this.location = location;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSecurityLayer() {
		return securityLayer;
	}

	public void setSecurityLayer(int securityLayer) {
		this.securityLayer = securityLayer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	public int compareTo(Users u) {
		return userId - u.getUserId();
		} 
}
