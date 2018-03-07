package cn.itcast.jk.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jk.domain.Module;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ModuleService;

import com.opensymphony.xwork2.ModelDriven;

public class ModuleAction extends BaseAction implements ModelDriven<Module>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//注入service层
	private ModuleService moduleService;
	
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	
	/*private DeptService deptService;
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}*/

	private Page<Module> page;
	
	public void setPage(Page<Module> page) {
		this.page = page;
	}
	private Module model=new Module();
	public Module getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	/*
	 * 查看模块列表
	 */
	public String list() throws Exception {
		/*List<Module> find = moduleService.find("from Module where state=1", null);
		this.set("dataList", find);*/
		
		if(page==null){
			page=new Page<Module>();
		}
		Page<Module> findPage = moduleService.findPage("from Module where state=1", page, null);
		findPage.setUrl(ServletActionContext.getRequest().getContextPath()+"/sysadmin/moduleAction_list");
		this.set("page", findPage);
		List<Module> results = findPage.getResults();
		this.set("dataList", results);
		return "list";
	}
	
	/*
	 * 查看模块
	 */
	public String toview() throws Exception {
		Module module = moduleService.get(model.getId());
		this.push(module);
		return "toview";
	}
	
	/*
	 * 
	 * 跳向新增页面
	 */
	public String tocreate() throws Exception {
		List<Module> find = moduleService.find("from Module where state=1", null);
		this.set("moduleList", find);
		return "tocreate";
	}
	
	/*
	 * 执行新增模块操作
	 */
	public String insert() throws Exception {
		moduleService.saveOrUpdate(model);
		return "relist";
	}
	
	/*
	 * 修改模块
	 */
	public String toupdate() throws Exception {
		Module module = moduleService.get(model.getId());
		this.push(module);
		return "toupdate";
	}
	
	/*
	 * 执行更新操作……
	 */
	public String update() throws Exception {
		Module module = moduleService.get(model.getId());
		module.setName(model.getName());
		module.setRemark(model.getRemark());
		moduleService.saveOrUpdate(module);
		return "relist";
	}
	
	public String tomodule() throws Exception {
		
		return "tomodule";
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
				moduleService.delete(split);
			}else{
				//单个删除
				moduleService.deleteById(split[0]);
			}
		}
		return "relist";
	}
}
