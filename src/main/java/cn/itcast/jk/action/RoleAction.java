package cn.itcast.jk.action;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.jk.domain.Module;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ModuleService;
import cn.itcast.jk.service.RoleService;
import cn.itcast.util.UtilFuns;

public class RoleAction extends BaseAction implements ModelDriven<Role>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//注入service层
	private RoleService roleService;
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	private ModuleService moduleService;
	
	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	private Page<Role> page;
	
	public void setPage(Page<Role> page) {
		this.page = page;
	}
	private Role model=new Role();
	public Role getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	
	private String moduleIds;
	
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	/*
	 * 查看角色列表
	 */
	public String list() throws Exception {
		/*List<Role> find = roleService.find("from Role ", null);
		this.set("dataList", find);*/
		if(page==null){
			page=new Page<Role>();
		}
		Page<Role> findPage = roleService.findPage("from Role ", page, null);
		findPage.setUrl(ServletActionContext.getRequest().getContextPath()+"/sysadmin/roleAction_list");
		this.set("page", findPage);
		List<Role> results = findPage.getResults();
		this.set("dataList", results);
		return "list";
	}
	
	/*
	 * 查看角色
	 */
	public String toview() throws Exception {
		Role role = roleService.get(model.getId());
		this.push(role);
		return "toview";
	}
	
	/*
	 * 
	 * 跳向新增页面
	 */
	public String tocreate() throws Exception {
		List<Role> find = roleService.find("from Role ", null);
		this.set("roleList", find);
		return "tocreate";
	}
	
	/*
	 * 执行新增角色操作
	 */
	public String insert() throws Exception {
		roleService.saveOrUpdate(model);
		return "relist";
	}
	
	/*
	 * 修改角色
	 */
	public String toupdate() throws Exception {
		Role role = roleService.get(model.getId());
		this.push(role);
		return "toupdate";
	}
	
	/*
	 * 执行更新操作……
	 */
	public String update() throws Exception {
		Role role = roleService.get(model.getId());
		role.setName(model.getName());
		role.setRemark(model.getRemark());
		roleService.saveOrUpdate(role);
		return "relist";
	}
	
	public String torole() throws Exception {
		
		return "torole";
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
				roleService.delete(split);
			}else{
				//单个删除
				roleService.deleteById(split[0]);
			}
		}
		return "relist";
	}
	
	//将模块id转换为String字符串  不使用ztree
	public String tomodule() throws Exception {
		List<Module> find = moduleService.find("from Module where state=1", null);
		this.set("moduleList", find);
		Role role = roleService.get(model.getId());
		StringBuffer sb =new StringBuffer();
		Set<Module> modules = role.getModules();
		for (Module module : modules) {
			sb.append(module.getId()+", ");
		}
		this.set("roleModuleStr", sb.toString());
		this.push(role);
		return "tomodule";
	}
	
	public String module() throws Exception {
		Role role = roleService.get(model.getId());
		System.out.println(moduleIds);
		String[] split = moduleIds.split(",");
		Set<Module> modules = new HashSet<Module>();
		for (String id : split) {
			if(UtilFuns.isNotEmpty(id)){
				Module module = moduleService.get(id);
				modules.add(module);
			}
		}
		role.setModules(modules);
		roleService.saveOrUpdate(role);
		return "relist";
	}
	
	//使用zTree进行处理
	public void roleModuleJsonStr() throws Exception {
		//获取角色已有的模块
		Role role = roleService.get(model.getId());
		Set<Module> curmodules = role.getModules();
		//所有的模块
		List<Module> modules = moduleService.find("from Module", null);
		
		//拼接成zTree所需要的字符串
		//返回json字符串，为zTree提供数据  [{id:"",pId:"",name:"",checked:"true|false"},{id:"",pId:"",name:"",checked:"true|false"}]
		StringBuilder sb=new StringBuilder();
		int size=modules.size();
		sb.append("[");
		for (Module module : modules) {
			size--;
			sb.append("{id:\"");
			sb.append(module.getId());
			sb.append("\",pId:\"");
			sb.append(module.getParentId());
			sb.append("\",name:\"");
			sb.append(module.getName());
			sb.append("\",checked:\"");
			if(curmodules.contains(module)){
				sb.append(true);
			}else{
				sb.append(false);
			}
			sb.append("\"}");
			if(size>0){
				sb.append(",");
			}
		}
		sb.append("]");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/json;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = response.getWriter();
		writer.write(sb.toString());
		writer.flush();
		writer.close();
	}
}
