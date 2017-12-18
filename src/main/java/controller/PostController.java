package controller;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import model.Post;
import model.User;
import model.Image;

@Stateless
public class PostController {

	@PersistenceContext
	private EntityManager entityManager;

	public void createPost(String title, String content, Image image, User user, List<User> likes) {
		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		post.setDate(new Date());
		post.setImage(image);
		post.setUser(user);
		post.setLikes(likes);
		entityManager.persist(post);
	}

	public List<Post> getUserPosts(int userId) {
		User u = entityManager.find(User.class, userId);

		TypedQuery<Post> q = entityManager
				.createQuery("select p from Post p where p.user = :u order by p.date desc, p.id desc", Post.class);
		q.setParameter("u", u);
		return q.getResultList();

	}

	public List<Post> fromFollowing(User u, int from, int max) {
		TypedQuery<Post> q;
		if (u.getFollowing().size() == 0) {
			q = entityManager.createQuery("select p from Post p where p.user = :user order by p.date desc, p.id desc", Post.class);
		} else {
			q = entityManager.createQuery("select p from Post p where p.user in :following or p.user = :user order by p.date desc, p.id desc", Post.class);
			q.setParameter("following", u.getFollowing());
		}
		q.setParameter("user", u);
		q.setFirstResult(from);
		q.setMaxResults(max);
		return q.getResultList();
	}

	public List<Post> all(int from, int max) {
		TypedQuery<Post> q = entityManager.createQuery("Select p from Post p order by p.date desc, p.id desc",
				Post.class);
		q.setFirstResult(from);
		q.setMaxResults(max);
		return q.getResultList();
	}

	// agregar Like
	public void addLike(User u, Post p) {
		u = entityManager.merge(u);
		p = entityManager.merge(p);
		p.addLikes(u);
	}

	public int getLikeCount(Post p) {
		return p.getLikes().size();
	}

}
