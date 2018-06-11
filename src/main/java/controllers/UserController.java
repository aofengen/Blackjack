package controllers;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import database.UserDatabase;
import models.*;

@CrossOrigin(origins = "*")
@RestController
public class UserController {
	
	@PostMapping("/signup")
    public String signup(@RequestBody Signup signup) throws Exception {
	   try {
		   UserDatabase.createUserTable();
	   } catch (Exception e) {
		   System.out.println(e);
	   }
	   
	   JSONObject obj = new JSONObject();

	   try {
    		String name = signup.getName();
    		String email = signup.getEmail();
    		String username = signup.getUsername();
    		String password = signup.getPassword();
    		
    		obj = UserDatabase.signup(name, email, username, password);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	
	   return obj.toString();
    }
    
    @PostMapping("/login")
    public String login(@RequestBody Login login) throws Exception {
    	try {
 		   UserDatabase.createUserTable();
 	   } catch (Exception e) {
 		   System.out.println(e);
 	   }
    	
 	   JSONObject obj = new JSONObject();
    	
    	try {
    		String email = login.getEmail();
    		String password = login.getPassword();
    		
    		obj = UserDatabase.login(email, password);
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	return obj.toString();
    }
    
    @PostMapping("/changeInfo")
    public String changeInfo(@RequestBody Change change) throws Exception {
    	JSONObject obj = new JSONObject();
    	try {
  		    UserDatabase.createUserTable();
  	    } catch (Exception e) {
  		    System.out.println(e);
  	    }
    	
    	try {
    		String newName = change.getNewName();
    		String newEmail = change.getNewEmail();
    		String newUsername = change.getNewUsername();
    		String password = change.getPassword();
    		String token = change.getToken();
    		int id = change.getId();
    		obj = UserDatabase.changeInfo(newName, newEmail, newUsername, password, token, id);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	return obj.toString();
    }
    
    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ChangePass pass) throws Exception {
    	JSONObject obj = new JSONObject();
    	try {
  		    UserDatabase.createUserTable();
  	    } catch (Exception e) {
  		    System.out.println(e);
  	    }
    	
    	try {
    		String oldPass = pass.getOldPass();
    		String newPass = pass.getNewPass();
    		String token = pass.getToken();
    		int id = pass.getId();

    		obj = UserDatabase.changePassword(oldPass, newPass, token, id);
    	}
    	catch (Exception e) {
    		System.out.println(e);
    	}
    	
    	return obj.toString();
    }


}
