package view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import controller.ImageController;
import controller.PostController;
import model.Image;
import model.Post;
import model.User;

@Named
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
		* 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class PostMb {

	@Inject
	private PostController postCntr;

	@Inject
	private ImageController imgCntr;

	private Part file;

	@Inject
	private AuthMb authMb;

	private String title;
	@NotNull
	@Size(min = 2, max = 255)
	private String content;

	public String create() {
		try {
			Image img = null;
			if (file != null && file.getSize() > 0 && file.getContentType().startsWith("image/")) {
				img = imgCntr.upload(file);
			}
			List<User> likes = new ArrayList<>();
			postCntr.createPost(title, content, img, authMb.getCurrentUser(), likes);
			content = null;
			return "home?faces-redirect=true";
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error interno", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public List<Post> listPostUser() {
		return postCntr.getUserPosts(authMb.getCurrentUserId());
	}

	public List<Post> getAllPost() {
		try {
			return postCntr.all(0, 10);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error interno", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}
	
	public List<Post> getPostsFromFollowing() {
		try {
			return postCntr.fromFollowing(authMb.getCurrentUser(), 0, 10);
		} catch (Exception e) {
			e.printStackTrace();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error interno", null);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}

	public void like(Post p) {
		postCntr.addLike(authMb.getCurrentUser(), p);
	}
	
	public String getLikeCount(Post p) {
		return Integer.toString(postCntr.getLikeCount(p));
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}
