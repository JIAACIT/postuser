package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import controller.ImageController;
import controller.UserController;
import exception.AuthenticationFailure;
import model.Image;
import model.User;

@Named
@SessionScoped
public class AuthMb implements Serializable {
	private static final long serialVersionUID = 791515424619865689L;

	@Inject
	private UserController userCntr;

	@Inject
	private ImageController imgController;

	private String username;
	private String password;

	private User currentUser;
	private User profileUser;
	private List<User> following = new ArrayList<>();
	private List<User> followers = new ArrayList<>();

	public User getProfileUser() {
		return profileUser;
	}

	public void setProfileUser(User profileUser) {
		this.profileUser = profileUser;
	}

	private Part file;

	private String filter;
	private List<User> result;

	public boolean isLogged() {
		return currentUser != null;
	}

	public String login() throws AuthenticationFailure {
		try {
			currentUser = userCntr.getAuthUser(username, password);
			username = null;
			password = null;

			if (isLogged())
				return "home?faces-redirect=true";
			else
				return null;
		} catch (Exception e) {
			throw new AuthenticationFailure(e);
		}
	}

	public List<User> all() {
		return userCntr.all();
	}

	public void search() {
		setResult(userCntr.searchUsers(filter));
	}

	public String logout() {
		currentUser = null;
		return "index?faces-redirect=true";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public int getCurrentUserId() {
		return currentUser.getId();
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public List<User> getResult() {
		return result;
	}

	public void setResult(List<User> result) {
		this.result = result;
	}

	public List<User> getFollowing() {
		return following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public void follow() {
		Image prflAvtr = profileUser.getAvatar();
		Image crntUsrAvtr = currentUser.getAvatar();
		User[] users = userCntr.followUser(profileUser, currentUser);
		this.setProfileUser(users[0]);
		this.setCurrentUser(users[1]);
		profileUser.setAvatar(prflAvtr);
		currentUser.setAvatar(crntUsrAvtr);
	}

	public String changePassword() {
		userCntr.changePassword(currentUser, password);
		return "home?faces-redirect=true";
	}

	public String updateAvatar() {
		try {
			Image img = null;
			if (getFile() != null && getFile().getSize() > 0 && getFile().getContentType().startsWith("image/")) {
				img = imgController.upload(getFile());
				userCntr.changeAvatar(getCurrentUser(), img);
				currentUser.setAvatar(img);
				return "home?faces-redirect=true";
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error interno", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;

		}
		return null;
	}

	public String profile(User u) {
		setProfileUser(u);
		setFollowing(userCntr.getFollowing(u));
		setFollowers(userCntr.getFollowers(u));
		return "profile?faces-redirect=true";
	}
	
}
