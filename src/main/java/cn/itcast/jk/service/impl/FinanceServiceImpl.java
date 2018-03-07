package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Finance;
import cn.itcast.jk.domain.PackingList;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.FinanceService;
import cn.itcast.util.UtilFuns;

/**
 * @Description:	FinanceService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:23:55
 */

public class FinanceServiceImpl implements FinanceService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<Finance> find(String hql, Class<Finance> entityClass, Object[] params) {
		return baseDao.find(hql, Finance.class, params);
	}

	public Finance get(Class<Finance> entityClass, Serializable id) {
		return baseDao.get(Finance.class, id);
	}

	public Page<Finance> findPage(String hql, Page<Finance> page, Class<Finance> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, Finance.class, params);
	}

	public void saveOrUpdate(Finance entity) {
		if(entity.getId()==null){								//代表新增
			entity.setState(1);									//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}

	public void save(Collection<Finance> entitys) {
		/*baseDao.save(entitys);*/
	}

	public void saveOrUpdateAll(Collection<Finance> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<Finance> entityClass, Serializable id) {
		baseDao.deleteById(Finance.class, id);
	}

	public void delete(Class<Finance> entityClass, Serializable[] ids) {
		baseDao.delete(Finance.class, ids);
	}

	public void save(Finance model) {
		PackingList packingList = baseDao.get(PackingList.class, model.getId());
		packingList.setState(4);
		baseDao.saveOrUpdate(packingList);
		baseDao.saveOrUpdate(model);
	}

}
