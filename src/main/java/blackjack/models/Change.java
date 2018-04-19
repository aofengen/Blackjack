package blackjack.models;

public class Change {
	
	private String name = "";
	private String newEmail = "";
	private String oldEmail = "";
	private String username = "";
	private String password = "";
	private String token = "";
	private int userIdNumber;
	
	public Change() {
		this.name = "";
		this.newEmail = "";
		this.oldEmail = "";
		this.username = "";
		this.password = "";
		this.token = "";
		this.userIdNumber = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getNewEmail() {
		return this.newEmail;
	}
	
	public void setNewEmail (String nE) {
		this.newEmail = nE;
	}
	
	public String getOldEmail() {
		return this.oldEmail;
	}
	
	public void setOldEmail(String e) {
		this.oldEmail = e;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String u) {
		this.username = u;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String p) {
		this.password = p;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setToken(String t) {
		this.token = t;
	}
	
	public int getUserIdNumber() {
		return this.userIdNumber;
	}
	
	public void setUserIdNumber(int id) {
		this.userIdNumber = id;
	}
}
