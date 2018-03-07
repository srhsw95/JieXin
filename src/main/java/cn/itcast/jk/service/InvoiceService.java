package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.domain.Invoice;
import cn.itcast.jk.pagination.Page;

/**
 * @Description:	InvoiceService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:24:40
 */

public interface InvoiceService {

	public List<Invoice> find(String hql, Class<Invoice> entityClass, Object[] params);
	public Invoice get(Class<Invoice> entityClass, Serializable id);
	public Page<Invoice> findPage(String hql, Page<Invoice> page, Class<Invoice> entityClass, Object[] params);
	
	public void save(Invoice entity);
	public void saveOrUpdate(Invoice entity);
	public void saveOrUpdateAll(Collection<Invoice> entitys);
	
	public void deleteById(Class<Invoice> entityClass, Serializable id);
	public void delete(Class<Invoice> entityClass, Serializable[] ids);
}
