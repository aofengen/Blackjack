package models;

public class Change {
	
	private String newName = "";
	private String newEmail = "";
	private String newUsername = "";
	private String password = "";
	private String token = "";
	private int id;
	
	public Change() {
		this.newName = "";
		this.newEmail = "";
		this.newUsername = "";
		this.password = "";
		this.token = "";
		this.id = 0;
	}
	
	public String getNewName() {
		return this.newName;
	}
	
	public void setNewName(String n) {
		this.newName = n;
	}
	
	public String getNewEmail() {
		return this.newEmail;
	}
	
	public void setNewEmail (String nE) {
		this.newEmail = nE;
	}
	
	public String getNewUsername() {
		return this.newUsername;
	}
	
	public void setNewUsername(String u) {
		this.newUsername = u;
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
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
