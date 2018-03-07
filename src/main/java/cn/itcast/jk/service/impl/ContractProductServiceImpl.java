package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractProductService;
import cn.itcast.jk.service.ContractService;

public class ContractProductServiceImpl implements ContractProductService {
		//注入到层
		private BaseDao baseDao;
		
		public void setBaseDao(BaseDao baseDao) {
			this.baseDao = baseDao;
		}

		//同用find方法，Action层传入定义好的sql语句即可
		public List<ContractProduct> find(String hql, Object[] params) {
			List<ContractProduct> find = baseDao.find(hql, ContractProduct.class, params);
			return find;
		}
	
		public ContractProduct get(Serializable id) {
			ContractProduct module = baseDao.get(ContractProduct.class, id);
			return module;
		}
	
		public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page, Object[] params) {
			Page<ContractProduct> findPage = baseDao.findPage(hql, page, ContractProduct.class, params);
			return findPage;
		}
	
		public void saveOrUpdate(ContractProduct entity) {
			baseDao.saveOrUpdate(entity);
		}
	
		public void saveOrUpdateAll(Collection<ContractProduct> entitys) {
			baseDao.saveOrUpdateAll(entitys);
		}
	
		
		 public void deleteById(Serializable id) {
				
			//ContractProduct module = baseDao.get(ContractProduct.class, id);
			baseDao.deleteById(ContractProduct.class, id);
		}
	
		public void delete(Serializable[] ids) {
			baseDao.delete(ContractProduct.class, ids);
		}
		//保存货物，更新合同
		public void save(ContractProduct model) {
			// TODO Auto-generated method stub
			//获取订单
			Contract contract = baseDao.get(Contract.class, model.getContract().getId());
			//获取总价
			Double totalAmount = contract.getTotalAmount();
			//计算货物总价
			Double productAmount =model.getCnumber()*model.getPrice();
			//设置货物总价
			model.setAmount(productAmount);
			//更新合同总价此时可以使用快照更新
			contract.setTotalAmount(totalAmount+productAmount);
			//更新合同与货物
			baseDao.saveOrUpdate(model);
			//contractService.saveOrUpdate(contract);
		}
		
		//删除货物,更新合同
		public void delete(ContractProduct model) {
			//得到具体货物
			ContractProduct contractProduct = baseDao.get(ContractProduct.class, model.getId());
					
			//根据货物编号得到合同
			Contract contract = baseDao.get(Contract.class, contractProduct.getContract().getId());
					
			//获得原有的总价
			Double totalAmount = contract.getTotalAmount();
			//总价减去附件价格
			Set<ExtCproduct> extCproducts = contractProduct.getExtCproducts();
			if(extCproducts!=null&&extCproducts.size()>0){
				for (ExtCproduct extCproduct : extCproducts) {
					totalAmount-=extCproduct.getAmount();
				}
			}
			//总价再减去货物价格
			totalAmount-=contractProduct.getAmount();
			//更新合同总价
			contract.setTotalAmount(totalAmount);
			//删除货物
			baseDao.deleteById(ContractProduct.class, contractProduct.getId());
			
		}
		
		//更新货物，更新合同
		public void update(ContractProduct model) {
			//获取合同对象
			Contract contract = baseDao.get(Contract.class, model.getContract().getId());
			//货物货物对象
			ContractProduct contractProduct = baseDao.get(ContractProduct.class, model.getId());
			//原有货物价格
			Double oldAmount = contractProduct.getAmount();
			//对应的合同对象
			//得到合同总价
			Double totalAmount = contract.getTotalAmount();
			//更新货物
			contractProduct.setFactoryName(model.getFactoryName());
			contractProduct.setFactory(baseDao.get(Factory.class, model.getFactory().getId()));
			contractProduct.setProductNo(model.getProductNo());
			contractProduct.setProductImage(model.getProductImage());
			contractProduct.setCnumber(model.getCnumber());
			contractProduct.setPackingUnit(model.getPackingUnit());
			contractProduct.setLoadingRate(model.getLoadingRate());
			contractProduct.setBoxNum(model.getBoxNum());
			contractProduct.setPrice(model.getPrice());
			contractProduct.setOrderNo(model.getOrderNo());
			contractProduct.setProductDesc(model.getProductDesc());
			contractProduct.setProductRequest(model.getProductRequest());
			Double newAmount=model.getPrice()*model.getCnumber();
			//更新货物价格
			contractProduct.setAmount(newAmount);
			//2、更新总价（合同总价-原有的货物总价+现在的货物总价）
			totalAmount=totalAmount-oldAmount+newAmount;
			contract.setTotalAmount(totalAmount);
			baseDao.saveOrUpdate(contractProduct);
		}
		
}
