package model;

import java.util.ArrayList;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@Size(min=2, max=255)
	private String name;

	@NotNull
	@Size(min=2, max=255)
	private String lname;

	@NotNull (message = "Usted debe ser mayor de 18 anios, valor maximo permitido 150")  
	@Min(value = 18)
	@Max(value = 150)
	private int age;
	
	@NotNull
	@Size(min=2, max=255)
	private String pwd;

	@NotNull
	@Size(min=2, max=255)
	private String email;

	@ManyToOne(fetch = FetchType.EAGER)
	private Image avatar;
	
	
	@ManyToMany(mappedBy = "likes")
	private List<Post> liked = new ArrayList<>();
	
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinTable(name="user_following", joinColumns= {@JoinColumn(name="follower", referencedColumnName="id", nullable=false)}, inverseJoinColumns= {@JoinColumn(name="following", referencedColumnName="id", nullable=false)})	
	private List<User> following = new ArrayList<>();
	
	public Image getAvatar() {
		return avatar;
	}

	public void setAvatar(Image avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public List<User> getFollowing() {
		return following;
	}
	
	public void setFollowing(List<User> following) {
		this.following = following;
	}

	public void follow(User u) {
		if (this.getFollowing().contains(u)) {
			this.getFollowing().remove(u);
		} else {
			this.getFollowing().add(u);
		}
	}

	//constructor vacio
	public User(){}
	
	public User(String name, String lname, String pwd, String email, int age,Image avatar, List<User> followers){
		this.name = name;
		this.lname = lname;
		this.age = age;
		this.email = email;
		this.pwd = pwd;
		this.avatar = avatar;
		this.following = followers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
	public List<Post> getLiked() {
		return liked;
	}

	public void setLiked(List<Post> liked) {
		this.liked = liked;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lname=" + lname + ", age=" + age + " avatar=" + avatar + "]";
	}

}