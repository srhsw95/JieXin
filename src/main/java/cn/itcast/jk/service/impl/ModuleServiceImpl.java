package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Module;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ModuleService;

public class ModuleServiceImpl implements ModuleService {
		//注入到层
		private BaseDao baseDao;
		
		public void setBaseDao(BaseDao baseDao) {
			this.baseDao = baseDao;
		}
		//同用find方法，Action层传入定义好的sql语句即可
		public List<Module> find(String hql, Object[] params) {
			List<Module> find = baseDao.find(hql, Module.class, params);
			return find;
		}
	
		public Module get(Serializable id) {
			Module module = baseDao.get(Module.class, id);
			return module;
		}
	
		public Page<Module> findPage(String hql, Page<Module> page, Object[] params) {
			Page<Module> findPage = baseDao.findPage(hql, page, Module.class, params);
			return findPage;
		}
	
		public void saveOrUpdate(Module entity) {
			baseDao.saveOrUpdate(entity);
		}
	
		public void saveOrUpdateAll(Collection<Module> entitys) {
			baseDao.saveOrUpdateAll(entitys);
		}
	
		
		 public void deleteById(Serializable id) {
				
			//Module module = baseDao.get(Module.class, id);
			baseDao.deleteById(Module.class, id);
		}
	
		public void delete(Serializable[] ids) {
			baseDao.delete(Module.class, ids);
		}
}
