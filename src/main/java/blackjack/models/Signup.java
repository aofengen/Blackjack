package blackjack.models;

public class Signup {
	private String name = "";
	private String email = "";
	private String username = "";
	private String password = "";
	
	public Signup() {
		this.name = "";
		this.email = "";
		this.username = "";
		this.password = "";
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String e) {
		this.email = e;
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
}
