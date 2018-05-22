package models;

public class Login {
	private String email = "";
	private String password = "";
	
	public Login() {
		this.email = "";
		this.password = "";
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String e) {
		this.email = e;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String p) {
		this.password = p;
	}
}
