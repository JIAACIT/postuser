package view;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import controller.UserController;
import model.Image;
import model.User;

@Named
public class RegisterMb {

	@Inject
	private UserController userCntr;

	private String name;
	private String lname;
	private String password;
	private String email;
	private int age;
	private Image avatar;
	private List<User> followers;
	

	public String register(){
		User user = new User(name, lname, password, email, age, avatar, followers);
		userCntr.create(user);
		return "index";
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}
}
