package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.ExportProduct;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ExportProductService;

public class ExportProductServiceImpl implements ExportProductService {
		//注入到层
		private BaseDao baseDao;
		
		public void setBaseDao(BaseDao baseDao) {
			this.baseDao = baseDao;
		}
		//同用find方法，Action层传入定义好的sql语句即可
		public List<ExportProduct> find(String hql, Object[] params) {
			List<ExportProduct> find = baseDao.find(hql, ExportProduct.class, params);
			return find;
		}
	
		public ExportProduct get(Serializable id) {
			ExportProduct module = baseDao.get(ExportProduct.class, id);
			return module;
		}
	
		public Page<ExportProduct> findPage(String hql, Page<ExportProduct> page, Object[] params) {
			Page<ExportProduct> findPage = baseDao.findPage(hql, page, ExportProduct.class, params);
			return findPage;
		}
	
		public void saveOrUpdate(ExportProduct entity) {
			baseDao.saveOrUpdate(entity);
		}
	
		public void saveOrUpdateAll(Collection<ExportProduct> entitys) {
			baseDao.saveOrUpdateAll(entitys);
		}
	
		
		 public void deleteById(Serializable id) {
				
			//ExportProduct module = baseDao.get(ExportProduct.class, id);
			baseDao.deleteById(ExportProduct.class, id);
		}
	
		public void delete(Serializable[] ids) {
			baseDao.delete(ExportProduct.class, ids);
		}
}
