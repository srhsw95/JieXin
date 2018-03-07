package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.FactoryService;

public class FactoryServiceImpl implements FactoryService {
		//注入到层
		private BaseDao baseDao;
		
		public void setBaseDao(BaseDao baseDao) {
			this.baseDao = baseDao;
		}
		//同用find方法，Action层传入定义好的sql语句即可
		public List<Factory> find(String hql, Object[] params) {
			List<Factory> find = baseDao.find(hql, Factory.class, params);
			return find;
		}
	
		public Factory get(Serializable id) {
			Factory module = baseDao.get(Factory.class, id);
			return module;
		}
	
		public Page<Factory> findPage(String hql, Page<Factory> page, Object[] params) {
			Page<Factory> findPage = baseDao.findPage(hql, page, Factory.class, params);
			return findPage;
		}
	
		public void saveOrUpdate(Factory entity) {
			baseDao.saveOrUpdate(entity);
		}
	
		public void saveOrUpdateAll(Collection<Factory> entitys) {
			baseDao.saveOrUpdateAll(entitys);
		}
	
		
		 public void deleteById(Serializable id) {
				
			//Factory module = baseDao.get(Factory.class, id);
			baseDao.deleteById(Factory.class, id);
		}
	
		public void delete(Serializable[] ids) {
			baseDao.delete(Factory.class, ids);
		}
}
