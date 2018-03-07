package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Invoice;
import cn.itcast.jk.domain.PackingList;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.InvoiceService;
import cn.itcast.util.UtilFuns;

/**
 * @Description:	InvoiceService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:24:40
 */

public class InvoiceServiceImpl implements InvoiceService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<Invoice> find(String hql, Class<Invoice> entityClass, Object[] params) {
		return baseDao.find(hql, Invoice.class, params);
	}

	public Invoice get(Class<Invoice> entityClass, Serializable id) {
		return baseDao.get(Invoice.class, id);
	}

	public Page<Invoice> findPage(String hql, Page<Invoice> page, Class<Invoice> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, Invoice.class, params);
	}

	public void saveOrUpdate(Invoice entity) {
		if(entity.getId()==null){								//代表新增
			entity.setState(1);									//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}

	public void save(Collection<Invoice> entitys) {
		/*baseDao.save(entitys);*/
	}

	public void saveOrUpdateAll(Collection<Invoice> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<Invoice> entityClass, Serializable id) {
		baseDao.deleteById(Invoice.class, id);
	}

	public void delete(Class<Invoice> entityClass, Serializable[] ids) {
		baseDao.delete(Invoice.class, ids);
	}

	public void save(Invoice model) {
		PackingList packingList = baseDao.get(PackingList.class, model.getId());
		packingList.setState(3);
		baseDao.saveOrUpdate(packingList);
		baseDao.saveOrUpdate(model);
	}

}
