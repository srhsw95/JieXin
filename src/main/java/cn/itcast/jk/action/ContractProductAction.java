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
import cn.itcast.jk.service.ContractService;
import cn.itcast.jk.service.FactoryService;

public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//注入service层
	private ContractProductService contractProductService;
	
	public void setContractProductService(ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}
	
	private FactoryService factoryService;
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}
	
	private ContractService contractService;
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	private Page<ContractProduct> page;
	
	public void setPage(Page<ContractProduct> page) {
		this.page = page;
	}
	private ContractProduct model=new ContractProduct();
	public ContractProduct getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	/*
	 * 查看货物列表
	 */
	public String list() throws Exception {
		/*List<ContractProduct> find = contractService.find("from ContractProduct ", null);
		this.set("dataList", find);*/
		if(page==null){
			page=new Page<ContractProduct>();
		}
		Page<ContractProduct> findPage = contractProductService.findPage("from ContractProduct ", page, null);
		findPage.setUrl(ServletActionContext.getRequest().getContextPath()+"/sysadmin/contractAction_list");
		this.set("page", findPage);
		List<ContractProduct> results = findPage.getResults();
		this.set("dataList", results);
		return "list";
	}
	
	/*
	 * 查看货物
	 */
	public String toview() throws Exception {
		ContractProduct contract = contractProductService.get(model.getId());
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
		List<Factory> factoryList = factoryService.find("from Factory f where f.ctype='货物'", null);
		this.put("factoryList", factoryList);
		//压入合同下的货物列表
		Contract contract = contractService.get(model.getContract().getId());
		this.push(contract);
		Set<ContractProduct> contractProducts = contract.getContractProducts();
		this.put("dataList", contractProducts);
		//跳转货物页面
		return "tocreate";
	}
	
	/*
	 * 执行新增货物操作
	 */
	/*public String insert() throws Exception {
		//获取订单
		Contract contract = contractService.get(model.getContract().getId());
		//获取总价
		Double totalAmount = contract.getTotalAmount();
		//计算货物总价
		Double productAmount =model.getCnumber()*model.getPrice();
		//设置货物总价
		model.setAmount(productAmount);
		//更新合同总价
		contract.setTotalAmount(totalAmount+productAmount);
		//更新合同与货物
		contractProductService.save(contract,model);
		return tocreate();
	}*/
	public String insert() throws Exception {
		contractProductService.save(model);
		return tocreate();
	}
	
	/*
	 * 修改货物
	 */
	public String toupdate() throws Exception {
		//压入生产厂商列表
		List<Factory> factoryList = factoryService.find("from Factory f where f.ctype='货物'", null);
		this.put("factoryList", factoryList);
		//货物货物对象
		ContractProduct contract = contractProductService.get(model.getId());
		this.push(contract);
		return "toupdate";
	}
	
	/*
	 * 执行更新操作……
	 */
	public String update() throws Exception {
		contractProductService.update(model);
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
		contractProductService.delete(model);
		return tocreate();
	}
}
