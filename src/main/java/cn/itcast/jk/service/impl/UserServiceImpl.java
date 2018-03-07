package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.UserService;

public class UserServiceImpl implements UserService{
	//注入到层
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	//同用find方法，Action层传入定义好的sql语句即可
	public List<User> find(String hql, Object[] params) {
		List<User> find = baseDao.find(hql, User.class, params);
		return find;
	}

	public User get(Serializable id) {
		User user = baseDao.get(User.class, id);
		return user;
	}

	public Page<User> findPage(String hql, Page<User> page, Object[] params) {
		Page<User> findPage = baseDao.findPage(hql, page, User.class, params);
		return findPage;
	}

	public void saveOrUpdate(User entity) {
		baseDao.saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(Collection<User> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	/*
	 * 
	 * 用户级联停用
	 * public void deleteById(Serializable id) {
		List<User> find = baseDao.find("from User u where u.userinfo.manager.id=?", User.class, new Object[]{id});
		if(find.size()>0){
			for (User user : find) {
				deleteById(user.getId());
				user.setState(0);
				baseDao.saveOrUpdate(user);
			}
		}
		User user = baseDao.get(User.class, id);
		user.setState(0);
	}

	public void delete(Serializable[] ids) {
		for (Serializable id : ids) {
			deleteById(id);
		}
	}*/
	
	 public void deleteById(Serializable id) {
			/*List<User> find = baseDao.find("from User u where u.userinfo.manager.id=?", User.class, new Object[]{id});
			if(find.size()>0){
				for (User user : find) {
					deleteById(user.getId());
					user.setState(0);
					baseDao.saveOrUpdate(user);
				}
			}*/
			User user = baseDao.get(User.class, id);
			user.setState(0);
		}

		public void delete(Serializable[] ids) {
			for (Serializable id : ids) {
				deleteById(id);
			}
		}
		public User findUserByUsername(String username) {
			List<User> list = baseDao.find("from User u where u.userName=? ", User.class, new Object[]{username});
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
			return null;
		}
}
