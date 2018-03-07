package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ExtCproductService;

public class ExtCproductServiceImpl implements ExtCproductService {
		//注入到层
		private BaseDao baseDao;
		
		public void setBaseDao(BaseDao baseDao) {
			this.baseDao = baseDao;
		}
		//同用find方法，Action层传入定义好的sql语句即可
		public List<ExtCproduct> find(String hql, Object[] params) {
			List<ExtCproduct> find = baseDao.find(hql, ExtCproduct.class, params);
			return find;
		}
	
		public ExtCproduct get(Serializable id) {
			ExtCproduct module = baseDao.get(ExtCproduct.class, id);
			return module;
		}
	
		public Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page, Object[] params) {
			Page<ExtCproduct> findPage = baseDao.findPage(hql, page, ExtCproduct.class, params);
			return findPage;
		}
	
		public void saveOrUpdate(ExtCproduct entity) {
			baseDao.saveOrUpdate(entity);
		}
	
		public void saveOrUpdateAll(Collection<ExtCproduct> entitys) {
			baseDao.saveOrUpdateAll(entitys);
		}
	
		
		 public void deleteById(Serializable id) {
				
			//ExtCproduct module = baseDao.get(ExtCproduct.class, id);
			baseDao.deleteById(ExtCproduct.class, id);
		}
	
		public void delete(Serializable[] ids) {
			baseDao.delete(ExtCproduct.class, ids);
		}
		public void save(ExtCproduct model) {
			// TODO Auto-generated method stub
			//获取订单
			Contract contract = baseDao.get(Contract.class, model.getContractProduct().getContract().getId());
			//获取总价
			Double totalAmount = contract.getTotalAmount();
			//计算附件总价
			Double extCproductAmount =model.getCnumber()*model.getPrice();
			//设置附件总价
			model.setAmount(extCproductAmount);
			//更新合同总价此时可以使用快照更新
			contract.setTotalAmount(totalAmount+extCproductAmount);
			//更新合同与附件
			baseDao.saveOrUpdate(model);
			//contractService.saveOrUpdate(contract);	
		}
		public void update(ExtCproduct model) {
			// TODO Auto-generated method stub
			//获取合同对象
			Contract contract = baseDao.get(Contract.class, model.getContractProduct().getContract().getId());
			//货物货物对象
			ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, model.getId());
			//原有货物价格
			Double oldAmount = extCproduct.getAmount();
			//对应的合同对象
			//得到合同总价
			Double totalAmount = contract.getTotalAmount();
			//更新货物
			extCproduct.setFactoryName(model.getFactoryName());
			extCproduct.setFactory(baseDao.get(Factory.class, model.getFactory().getId()));
			extCproduct.setProductNo(model.getProductNo());
			extCproduct.setProductImage(model.getProductImage());
			extCproduct.setCnumber(model.getCnumber());
			extCproduct.setPackingUnit(model.getPackingUnit());
			extCproduct.setPrice(model.getPrice());
			extCproduct.setOrderNo(model.getOrderNo());
			extCproduct.setProductDesc(model.getProductDesc());
			extCproduct.setProductRequest(model.getProductRequest());
			Double newAmount=model.getPrice()*model.getCnumber();
			//更新货物价格
			extCproduct.setAmount(newAmount);
			//2、更新总价（合同总价-原有的货物总价+现在的货物总价）
			totalAmount=totalAmount-oldAmount+newAmount;
			contract.setTotalAmount(totalAmount);
			baseDao.saveOrUpdate(extCproduct);
		}
		public void delete(ExtCproduct model) {
			Contract contract = baseDao.get(Contract.class, model.getContractProduct().getContract().getId());
			//获取总价
			Double totalAmount = contract.getTotalAmount();
			ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, model.getId());
			Double amount = extCproduct.getAmount();
			contract.setTotalAmount(totalAmount-amount);
			baseDao.deleteById(ExtCproduct.class, model.getId());
		}
}
