package cn.itcast.jk.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import cn.itcast.common.SysConstant;
import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.exception.SysException;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.DeptService;
import cn.itcast.jk.service.RoleService;
import cn.itcast.jk.service.UserService;
import cn.itcast.util.Encrypt;
import cn.itcast.util.UtilFuns;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends BaseAction implements ModelDriven<User>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//注入service层
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	private DeptService deptService;
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	private RoleService roleService;
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private Page<User> page;
	
	public void setPage(Page<User> page) {
		this.page = page;
	}
	private User model=new User();
	public User getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	private String roleIds;
	
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	/*
	 * 查看用户列表
	 */
	public String list() throws Exception {
		/*List<User> find = userService.find("from User where state=1", null);
		this.set("dataList", find);*/
		if(page==null){
			page=new Page<User>();
		}
		Page<User> findPage = userService.findPage("from User where state=1", page, null);
		findPage.setUrl(ServletActionContext.getRequest().getContextPath()+"/sysadmin/userAction_list");
		this.set("page", findPage);
		List<User> results = findPage.getResults();
		this.set("dataList", results);
		return "list";
	}
	
	/*
	 * 查看用户
	 */
	public String toview() throws Exception {
		User user = userService.get(model.getId());
		this.push(user);
		return "toview";
	}
	
	/*
	 * 
	 * 跳向新增页面
	 */
	public String tocreate() throws Exception {
		//压两个值，deptList，userList
		List<Dept> findDeptList = deptService.findDeptList();
		this.set("deptList", findDeptList);
		List<User> find = userService.find("from User where state=1", null);
		this.set("userList", find);
		return "tocreate";
	}
	
	/*
	 * 执行新增用户操作
	 */
	public String insert() throws Exception {
		String id=UUID.randomUUID().toString();
		if(UtilFuns.isEmpty(model.getUserinfo().getManager().getId())){
			model.getUserinfo().setManager(null);
		}
		model.setId(id);
		model.getUserinfo().setId(id);
		model.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS, model.getUserName()));//创建用户时，密码进行加密。
		userService.saveOrUpdate(model);
		return "relist";
	}
	
	/**
	 * 异常框架测试
	 * @return
	 * @throws SysException
	 */
	/*public String insert() throws SysException  {
		try {
			int i=1/0;
			userService.saveOrUpdate(model);
		} catch (Exception e) {
			throw new SysException("故意报错！");
		}
		return "relist";
	}*/
	
	/*
	 * 修改用户
	 */
	public String toupdate() throws Exception {
		List<Dept> findDeptList = deptService.findDeptList();
		this.set("deptList", findDeptList);
		User user = userService.get(model.getId());
		this.push(user);
		return "toupdate";
	}
	
	/*
	 * 执行更新操作……
	 */
	public String update() throws Exception {
		User user = userService.get(model.getId());
		user.getDept().setId(model.getDept().getId());
		user.setUserName(model.getUserName());
		user.setState(model.getState());
		user.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS, model.getUserName()));//修改用户时，同样需要修改密码。
		userService.saveOrUpdate(user);
		return "relist";
	}
	/*
	 * 这边进去之前，压一个字符串进去
	 */
	public String torole() throws Exception {
		List<Role> find = roleService.find("from Role", null);
		this.set("roleList", find);
		User user = userService.get(model.getId());
		Set<Role> roles = user.getRoles();
		StringBuffer sb =new StringBuffer();
		for (Role role : roles) {
			sb.append(role.getId()+", ");
		}
		this.set("userRoleStr", sb.toString());
		this.push(user);
		return "torole";
	}
	
	public String role() throws Exception {
		String[] split = roleIds.split(", ");
		User user = userService.get(model.getId());
		Set<Role> roles =new HashSet<Role>();
		for (String id : split) {
			roles.add(roleService.get(id));
		}
		user.setRoles(roles);
		userService.saveOrUpdate(user);
		return "relist";
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
				userService.delete(split);
			}else{
				//单个删除
				userService.deleteById(split[0]);
			}
		}
		return "relist";
	}
	
}
