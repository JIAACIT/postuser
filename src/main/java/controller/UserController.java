package controller;

import java.util.List;

//import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import model.Image;
import model.User;

@Stateless
public class UserController {

	@PersistenceContext
	private EntityManager entityMgr;

	public void create(User user) {
		entityMgr.persist(user);
	}

	// Lista de Usuarios
	public List<User> all() {
		CriteriaQuery<User> cq = entityMgr.getCriteriaBuilder().createQuery(User.class);
		cq.select(cq.from(User.class));
		return entityMgr.createQuery(cq).getResultList();
	}

	// Retorna Usuario x ID
	public User byId(int id) {
		return entityMgr.find(User.class, id);
	}

	public User getAuthUser(String username, String password) {
		for (User user : all()) {
			if (user.getEmail().equals(username) && (user.getPwd().equals(password))) {
				return entityMgr.merge(user);
			}
		}
		return null;
	}
	
	
	public void changePassword(User user, String password) {
		user = entityMgr.merge(user);
		user.setPwd(password);
	}
	
	public void changeAvatar(User u, Image img) {
		u = entityMgr.merge(u);
		u.setAvatar(img);
	}

	public List<User> searchUsers(String filter) {
		String hql = "Select u from User u where u.name Like :filter";
		TypedQuery<User> q = entityMgr.createQuery(hql, User.class);
		q.setParameter("filter", "%" + filter + "%");
		return q.getResultList();
	}

	public User[] followUser(User f, User authUser) {
		f = entityMgr.merge(f);
		authUser = entityMgr.merge(authUser);
		authUser.follow(f);
		return new User[] {f, authUser};
	}
	
	public List<User> getFollowing(User u) {
		return u.getFollowing();
	}

	public List<User> getFollowers(User u) {
		String hql = "Select u from User u where :usr member of u.following";
		TypedQuery<User> q = entityMgr.createQuery(hql, User.class);
		q.setParameter("usr", u);
		return q.getResultList();
	}

}
