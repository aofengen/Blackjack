package models;

public class ChangePass {
	private String oldPass = "";
	private String newPass = "";
//	private String confirmPass = "";
	private String token = "";
	private int id;
	
	public ChangePass() {
		this.oldPass = "";
		this.newPass = "";
//		this.confirmPass = "";
		this.token = "";
		this.id = 0;
	}
	
	public String getOldPass() {
		return this.oldPass;
	}
	
	public void setOldPass(String oP) {
		this.oldPass = oP;
	}
	
	public String getNewPass() {
		return this.newPass;
	}
	
	public void setNewPass(String nP) {
		this.newPass = nP;
	}
	
//	public String getConfirmPass() {
//		return this.confirmPass;
//	}
//	
//	public void setConfirmPass(String cP) {
//		this.confirmPass = cP;
//	}

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
