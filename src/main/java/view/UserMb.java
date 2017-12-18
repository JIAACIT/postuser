package view;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import controller.UserController;
import model.User;

@Named
public class UserMb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UserController userController;

	private User profileUser;

	public User getProfileUser() {
		return profileUser;
	}

	public void setProfileUser(User profileUser) {
		this.profileUser = profileUser;
	}

	public List<User> all() {
		return userController.all();
	}

	public List<User> followers() {

		return profileUser.getFollowing();
	}

	public String profile(User u) {
		setProfileUser (u);
		
		return "profile?faces-redirect=true";
	}
}