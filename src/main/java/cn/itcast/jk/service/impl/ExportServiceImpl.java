package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.domain.Export;
import cn.itcast.jk.domain.ExportProduct;
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.domain.ExtEproduct;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ExportService;
import cn.itcast.util.UtilFuns;

public class ExportServiceImpl implements ExportService {
		//注入到层
		private BaseDao baseDao;
		
		public void setBaseDao(BaseDao baseDao) {
			this.baseDao = baseDao;
		}
		//同用find方法，Action层传入定义好的sql语句即可
		public List<Export> find(String hql, Object[] params) {
			List<Export> find = baseDao.find(hql, Export.class, params);
			return find;
		}
	
		public Export get(Serializable id) {
			Export module = baseDao.get(Export.class, id);
			return module;
		}
	
		public Page<Export> findPage(String hql, Page<Export> page, Object[] params) {
			Page<Export> findPage = baseDao.findPage(hql, page, Export.class, params);
			return findPage;
		}
	
		public void saveOrUpdate(Export entity) {
			baseDao.saveOrUpdate(entity);
		}
	
		public void saveOrUpdateAll(Collection<Export> entitys) {
			baseDao.saveOrUpdateAll(entitys);
		}
	
		
		 public void deleteById(Serializable id) {
				
			//Export module = baseDao.get(Export.class, id);
			baseDao.deleteById(Export.class, id);
		}
	
		public void delete(Serializable[] ids) {
			baseDao.delete(Export.class, ids);
		}
		
		//保存出口报运单操作
		public void save(Export model)   {
				//获取合同id 
				//原先的字符串为xxxx,xxxx,xxxx
				String contractIds = model.getContractIds();
				//切割
				String[] split = contractIds.split(", ");
				//转换为如下格式：
				//'xxx','xxx','xxx'
				String joinInStr = UtilFuns.joinInStr(split);
				//找到Contract对象集合
				List<Contract> find = baseDao.find("from Contract c where c.id in ("+joinInStr+")", Contract.class, null);
				StringBuilder sb =new StringBuilder();
				Set<ExportProduct> exportProducts=new HashSet<ExportProduct>();
				Export export=new Export();
				
				for (Contract contract : find) {
					contract.setState(2);
					sb.append(contract.getContractNo()+" ");
					Set<ContractProduct> contractProducts = contract.getContractProducts();
					//搬运商品信息
					for (ContractProduct contractProduct : contractProducts) {
						ExportProduct exportProduct =new ExportProduct();
						Set<ExtEproduct> extEproducts=new HashSet<ExtEproduct>();
						
						
						BeanUtils.copyProperties(contractProduct,exportProduct);
						Set<ExtCproduct> extCproducts = contractProduct.getExtCproducts();
						//搬运附件
						for (ExtCproduct extCproduct : extCproducts) {
							ExtEproduct extEproduct =new ExtEproduct();
							BeanUtils.copyProperties(extCproduct, extEproduct);
							extEproduct.setId(null);
							//多方设置外键
							extEproduct.setExportProduct(exportProduct);
							extEproducts.add(extEproduct);
						}
						exportProduct.setExtEproducts(extEproducts);
						exportProduct.setId(null);
						exportProduct.setExport(export);
						exportProducts.add(exportProduct);
					}
					baseDao.saveOrUpdate(contract);
				}
				//导入报运单属性
				BeanUtils.copyProperties(model, export);
				export.setId(null);
				export.setContractIds(contractIds);
				export.setCustomerContract(sb.toString());
				export.setState(0);
				
				User user = (User)ActionContext.getContext().getSession().get("_CURRENT_USER");
				export.setCreateDept(user.getDept().getId());
				export.setCreateBy(user.getId());
				export.setCreateTime(new Date(System.currentTimeMillis()));
				export.setExportProducts(exportProducts);
				baseDao.saveOrUpdate(export);
		}
		public void delete(Export model) {
			String[] split = model.getId().split(", ");
			
			List<Export> find =new ArrayList<Export>();
			for (String id : split) {
				find.add(baseDao.get(Export.class, id));
			}
			
			for (Export export : find) {
				String contractIds = export.getContractIds();
				String[] split2 = contractIds.split(", ");
				for (String id : split2) {
					Contract contract = baseDao.get(Contract.class, id);
					contract.setState(1);
					baseDao.saveOrUpdate(contract);
				}
			}
			if(split!=null&&split.length>1){
				baseDao.delete(Export.class, split);
			}else{
				baseDao.deleteById(Export.class, model.getId());
			}
		}
}
