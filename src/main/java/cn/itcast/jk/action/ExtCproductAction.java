package cn.itcast.jk.action;

import java.util.List;






import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.domain.ExtCproduct;
import cn.itcast.jk.domain.Factory;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractProductService;
import cn.itcast.jk.service.ExtCproductService;
import cn.itcast.jk.service.ContractService;
import cn.itcast.jk.service.FactoryService;

public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//注入service层
	private ExtCproductService extCproductService;
	
	public void setExtCproductService(ExtCproductService extCproductService) {
		this.extCproductService = extCproductService;
	}
	
	private ContractProductService contractProductService;
	
	public void setContractProductService(ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}
	
	private FactoryService factoryService;
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}
	

	private Page<ExtCproduct> page;
	
	public void setPage(Page<ExtCproduct> page) {
		this.page = page;
	}
	private ExtCproduct model=new ExtCproduct();
	public ExtCproduct getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	/*
	 * 查看货物列表
	 */
	public String list() throws Exception {
		/*List<ExtCproduct> find = contractService.find("from ExtCproduct ", null);
		this.set("dataList", find);*/
		if(page==null){
			page=new Page<ExtCproduct>();
		}
		Page<ExtCproduct> findPage = extCproductService.findPage("from ExtCproduct ", page, null);
		findPage.setUrl(ServletActionContext.getRequest().getContextPath()+"/sysadmin/contractAction_list");
		this.set("page", findPage);
		List<ExtCproduct> results = findPage.getResults();
		this.set("dataList", results);
		return "list";
	}
	
	/*
	 * 查看货物
	 */
	public String toview() throws Exception {
		ExtCproduct contract = extCproductService.get(model.getId());
		this.push(contract);
		return "toview";
	}
	
	/*
	 * 
	 * 跳向新增页面
	 * 压入生产厂家列表
	 */
	public String tocreate() throws Exception {
		//压入生产厂商列表
		List<Factory> factoryList = factoryService.find("from Factory f where f.ctype='附件'", null);
		this.put("factoryList", factoryList);
		//压入货物下的附件
		ContractProduct contractProduct = contractProductService.get(model.getContractProduct().getId());
		Contract contract = contractProduct.getContract();
		contract.getState();
		this.push(contract);
		Set<ExtCproduct> extCproducts = contractProduct.getExtCproducts();
		this.put("dataList", extCproducts);
		//跳转货物页面
		return "tocreate";
	}
	
	
	public String insert() throws Exception {
		extCproductService.save(model);
		return tocreate();
	}
	
	/*
	 * 修改货物
	 */
	public String toupdate() throws Exception {
		//压入生产厂商列表
		List<Factory> factoryList = factoryService.find("from Factory f where f.ctype='附件'", null);
		this.put("factoryList", factoryList);
		//附件对象
		ExtCproduct contract = extCproductService.get(model.getId());
		this.push(contract);
		return "toupdate";
	}
	
	/*
	 * 执行更新操作……
	 */
	public String update() throws Exception {
		extCproductService.update(model);
		return tocreate();
	}
	
	
	
	
	
	public String tocontract() throws Exception {
		
		return "tocontract";
	}
	
	
	/**
	 * 删除操作，删除货物，同时需要删除货物下的附件，同时需要变总值
	 * @return
	 * @throws Exception 
	 */
	public String delete() throws Exception{
		extCproductService.delete(model);
		return tocreate();
	}
}
