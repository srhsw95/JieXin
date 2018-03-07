package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.PackingList;
import cn.itcast.jk.domain.ShippingOrder;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ShippingOrderService;
import cn.itcast.util.UtilFuns;

/**
 * @Description:	ShippingOrderService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:23:17
 */

public class ShippingOrderServiceImpl implements ShippingOrderService {
	//spring注入dao
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<ShippingOrder> find(String hql, Class<ShippingOrder> entityClass, Object[] params) {
		return baseDao.find(hql, ShippingOrder.class, params);
	}

	public ShippingOrder get(Class<ShippingOrder> entityClass, Serializable id) {
		return baseDao.get(ShippingOrder.class, id);
	}

	public Page<ShippingOrder> findPage(String hql, Page<ShippingOrder> page, Class<ShippingOrder> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, ShippingOrder.class, params);
	}

	public void saveOrUpdate(ShippingOrder entity) {
		if(entity.getId()==null){								//代表新增
			entity.setState(1);									//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}

	public void save(Collection<ShippingOrder> entitys) {
		//baseDao.save(entitys);
	}

	public void saveOrUpdateAll(Collection<ShippingOrder> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<ShippingOrder> entityClass, Serializable id) {
		baseDao.deleteById(ShippingOrder.class, id);
	}

	public void delete(Class<ShippingOrder> entityClass, String[] ids) {
		String joinInStr = UtilFuns.joinInStr(ids);
		List<PackingList> find = baseDao.find("from PackingList p where p.id in ("+joinInStr +")", PackingList.class, null);
		for (PackingList packingList : find) {
			packingList.setState(1);
			baseDao.saveOrUpdate(packingList);
		}
		baseDao.delete(ShippingOrder.class, ids);
	}
	
	/**
	 * 保存装箱，修改状态
	 */
	public void save(ShippingOrder model) {
		ShippingOrder shippingOrder=new ShippingOrder();
		BeanUtils.copyProperties(model, shippingOrder);
		shippingOrder.setState(0);
		baseDao.saveOrUpdate(shippingOrder);
		PackingList packingList = baseDao.get(PackingList.class, model.getId());
		packingList.setState(2);
		baseDao.saveOrUpdate(packingList);
	}

}
