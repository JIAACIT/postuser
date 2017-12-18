package view;

import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import view.AuthMb;
import controller.CommentController;
import model.Comment;
import model.Post;
import model.User;

@Named
public class CommentMb {

	@Inject
	private CommentController commentCntrl;
	
	@Inject
	private AuthMb authMb;
	
	@NotNull
	@Size(min=2, max=255)
	private String comment;
	
	public void create(Post post){
		User user = authMb.getCurrentUser();
		System.out.println(user.getEmail() + " | " + post.getTitle() + " | " + comment);
		commentCntrl.create(user, post, comment);
	}

	public List<Comment> listByPost(Post post){
		return commentCntrl.byPost(post);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		System.out.println("Seteando el comment = " + comment);
		this.comment = comment;
	}
}
