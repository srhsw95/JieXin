package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractService;

public class ContractServiceImpl implements ContractService {
		//注入到层
		private BaseDao baseDao;
		
		public void setBaseDao(BaseDao baseDao) {
			this.baseDao = baseDao;
		}
		//同用find方法，Action层传入定义好的sql语句即可
		public List<Contract> find(String hql, Object[] params) {
			List<Contract> find = baseDao.find(hql, Contract.class, params);
			return find;
		}
	
		public Contract get(Serializable id) {
			Contract module = baseDao.get(Contract.class, id);
			return module;
		}
	
		public Page<Contract> findPage(String hql, Page<Contract> page, Object[] params) {
			Page<Contract> findPage = baseDao.findPage(hql, page, Contract.class, params);
			return findPage;
		}
	
		public void saveOrUpdate(Contract entity) {
			baseDao.saveOrUpdate(entity);
		}
	
		public void saveOrUpdateAll(Collection<Contract> entitys) {
			baseDao.saveOrUpdateAll(entitys);
		}
	
		
		 public void deleteById(Serializable id) {
				
			//Contract module = baseDao.get(Contract.class, id);
			baseDao.deleteById(Contract.class, id);
		}
	
		public void delete(Serializable[] ids) {
			baseDao.delete(Contract.class, ids);
		}
}
