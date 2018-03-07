package cn.itcast.jk.action;

import java.util.Date;
import java.util.List;





import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.action.Executable;

import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.jk.domain.Module;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.print.ContractPrint;
import cn.itcast.jk.service.ContractService;
import cn.itcast.util.UtilFuns;

public class ContractAction extends BaseAction implements ModelDriven<Contract>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//注入service层
	private ContractService contractService;
	
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	

	private Page<Contract> page;
	
	public void setPage(Page<Contract> page) {
		this.page = page;
	}
	private Contract model=new Contract();
	public Contract getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	/*
	 * 查看角色列表
	 */
	public String list() throws Exception {
		/*List<Contract> find = contractService.find("from Contract ", null);
		this.set("dataList", find);*/
		//先拿到用户
		String sql="from Contract c where 1=1";
		User user=(User)session.get("_CURRENT_USER");
		Integer degree = user.getUserinfo().getDegree();
		if(degree==4){
			sql+=" and c.createBy='"+user.getId()+"'";
		}
		if(degree==3){
			sql+=" and c.createDept='"+user.getDept().getId()+"'";
		}
		if(page==null){
			page=new Page<Contract>();
		}
		Page<Contract> findPage = contractService.findPage(sql, page, null);
		findPage.setUrl(ServletActionContext.getRequest().getContextPath()+"/cargo/contractAction_list");
		this.set("page", findPage);
		List<Contract> results = findPage.getResults();
		this.set("dataList", results);
		return "list";
	}
	
	/*
	 * 查看角色
	 */
	public String toview() throws Exception {
		Contract contract = contractService.get(model.getId());
		this.push(contract);
		return "toview";
	}
	
	/*
	 * 
	 * 跳向新增页面
	 */
	public String tocreate() throws Exception {
		List<Contract> find = contractService.find("from Contract ", null);
		this.set("contractList", find);
		return "tocreate";
	}
	
	/*
	 * 执行新增角色操作
	 */
	public String insert() throws Exception {
		User user=(User)session.get("_CURRENT_USER");
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		model.setCreateTime(new Date(System.currentTimeMillis()));
		//需要将合同的总金额由null变为0,这样才能进行后续的分散计算
		model.setTotalAmount(0.0);
		model.setState(0);
		contractService.saveOrUpdate(model);
		return "relist";
	}
	
	/*
	 * 修改角色
	 */
	public String toupdate() throws Exception {
		Contract contract = contractService.get(model.getId());
		this.push(contract);
		return "toupdate";
	}
	
	/*
	 * 执行更新操作……
	 */
	public String update() throws Exception {
		//得到原数数据
		Contract contract = contractService.get(model.getId());
		update(contract);
		contractService.saveOrUpdate(contract);
		return "relist";
	}
	
	/**
	 * 具体的更新操作
	 * @param contract
	 */
	private void update(Contract contract) {
		User user=(User)session.get("_CURRENT_USER");
		contract.setCustomerName(model.getCustomerName());
		contract.setPrintStyle(model.getPrintStyle());
		contract.setContractNo(model.getContractNo());
		contract.setOfferor(model.getOfferor());
		contract.setInputBy(model.getInputBy());
		contract.setCheckBy(model.getCheckBy());
		contract.setInspector(model.getInspector());
		contract.setSigningDate(model.getSigningDate());
		contract.setImportNum(model.getImportNum());
		contract.setShipTime(model.getShipTime());
		contract.setTradeTerms(model.getTradeTerms());
		contract.setDeliveryPeriod(model.getDeliveryPeriod());
		contract.setCrequest(model.getCrequest());
		contract.setRemark(model.getRemark());
		contract.setUpdateBy(user.getId());
		contract.setUpdateTime(new Date(System.currentTimeMillis()));
	}

	public String tocontract() throws Exception {
		
		return "tocontract";
	}
	
	
	/*
	 * 执行删除操作
	 */
	/**
	 * 1、这边的删除实际上是一个update的更新操作，即将获取到的dept中的state由1改为0
	 * 2、如果部门有子部门，其下的所有的子部门递归，需要全部将状态变为0
	 * @return
	 */
	public String delete(){
		if(model.getId()!=null){
			String[] split = model.getId().split(", ");
			if(split!=null&&split.length>1){
				//使用批量删除
				contractService.delete(split);
			}else{
				//单个删除
				contractService.deleteById(split[0]);
			}
		}
		return "relist";
	}
	
	//提交合同，一个或多个合同的提交
	//只有在草稿状态下，才可以提交，其他均不进行操作   相关提交取消合同的代码可以进行抽取
	public String submit() throws Exception {
		changeState(0,1);
		return "relist";
	}
	
	//取消合同
		//需要先拿到合同的原有的状态，如果为已提交状态，就可以取消，位其他状态，就不可以再提交了
	public String cancel() throws Exception {
		changeState(1,0);
		return "relist";
	}
	
	//合同变更代码的抽取
	public void changeState(Integer oldState,Integer newState) {
		String ids = model.getId();
		String[] split = ids.split(", ");
		if(split!=null&&split.length>1){
			for (String id : split) {
				Contract contract = contractService.get(id);
				if(contract.getState()==oldState){
					contract.setState(newState);
					contractService.saveOrUpdate(contract);
				}
			}
		}else{
			Contract contract = contractService.get(ids);
			if(contract.getState()==oldState){
				contract.setState(newState);
				contractService.saveOrUpdate(contract);
			}
		}
	}
	
	/**
	 * 调用打印方法
	 */
	public String print() throws Exception {
		ContractPrint contractPrint = new ContractPrint();
		Contract contract = contractService.get(model.getId());
		String path = ServletActionContext.getServletContext().getRealPath("/");
		HttpServletResponse response = ServletActionContext.getResponse();
		contractPrint.print(contract, path, response);
		return NONE;
	}
}
