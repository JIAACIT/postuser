package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotNull
	@Size(min = 2, max = 255)
	private String title;

	@NotNull
	@Size(min = 2, max = 255)
	private String content;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@ManyToOne(fetch = FetchType.EAGER)
	private Image image;

	@NotNull
	@ManyToOne
	private User user;
	
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinTable(name="post_likes", joinColumns= {@JoinColumn(name="post_id", referencedColumnName="id", nullable=false)}, inverseJoinColumns= {@JoinColumn(name="user_id", referencedColumnName="id", nullable=false)})
	private List<User> likes = new ArrayList<>();


	public Post() {
		likes = new ArrayList<>();
	}

	public Post(String title, String content, Date date, Image image, User user, List<User> likes) {
		this.title = title;
		this.content = content;
		this.date = date;
		this.image = image;
		this.user = user;
		this.likes = likes == null ? new ArrayList<>() : likes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public List<User> getLikes() {
		return likes;
	}
	//agregar un like
	public void addLikes(User u) {
		if (!this.getLikes().contains(u)) {
			this.getLikes().add(u);
		} else {
			this.getLikes().remove(u);
		}
	}


	public void setLikes(List<User> likes) {
		this.likes = likes == null ? new ArrayList<>() : likes;
	}

	@Override
	public String toString() {
		return "#" + id + ", " + user.getLname() + " \"" + title + "\" " + content + "\" " + date;
	}
}
