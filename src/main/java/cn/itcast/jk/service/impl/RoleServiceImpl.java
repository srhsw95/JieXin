package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.RoleService;

public class RoleServiceImpl implements RoleService{
	//注入到层
		private BaseDao baseDao;
		
		public void setBaseDao(BaseDao baseDao) {
			this.baseDao = baseDao;
		}
		//同用find方法，Action层传入定义好的sql语句即可
		public List<Role> find(String hql, Object[] params) {
			List<Role> find = baseDao.find(hql, Role.class, params);
			return find;
		}

		public Role get(Serializable id) {
			Role role = baseDao.get(Role.class, id);
			return role;
		}

		public Page<Role> findPage(String hql, Page<Role> page, Object[] params) {
			Page<Role> findPage = baseDao.findPage(hql, page, Role.class, params);
			return findPage;
		}

		public void saveOrUpdate(Role entity) {
			baseDao.saveOrUpdate(entity);
		}

		public void saveOrUpdateAll(Collection<Role> entitys) {
			baseDao.saveOrUpdateAll(entitys);
		}

		
		 public void deleteById(Serializable id) {
				
				//Role role = baseDao.get(Role.class, id);
				baseDao.deleteById(Role.class, id);
			}

			public void delete(Serializable[] ids) {
				baseDao.delete(Role.class, ids);
			}
}
