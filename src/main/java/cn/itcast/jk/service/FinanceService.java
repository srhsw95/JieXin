package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.domain.Finance;
import cn.itcast.jk.pagination.Page;

/**
 * @Description:	FinanceService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:23:55
 */

public interface FinanceService {

	public List<Finance> find(String hql, Class<Finance> entityClass, Object[] params);
	public Finance get(Class<Finance> entityClass, Serializable id);
	public Page<Finance> findPage(String hql, Page<Finance> page, Class<Finance> entityClass, Object[] params);
	
	public void save(Finance entity);
	public void saveOrUpdate(Finance entity);
	public void saveOrUpdateAll(Collection<Finance> entitys);
	
	public void deleteById(Class<Finance> entityClass, Serializable id);
	public void delete(Class<Finance> entityClass, Serializable[] ids);
}
