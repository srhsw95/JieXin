package cn.itcast.jk.action;

import java.util.List;

import javassist.expr.NewArray;

import org.apache.struts2.ServletActionContext;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.PortableInterceptor.SUCCESSFUL;
import org.springframework.dao.support.DaoSupport;







import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.DeptService;

public class DeptAction extends BaseAction implements ModelDriven<Dept>{
	//获取页面传递的对象
	private Dept model =new Dept();
	public Dept getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	//注入dao层
	private DeptService deptService;
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	
	//获取page分页对象
	private Page<Dept> page;
	public void setPage(Page<Dept> page) {
		this.page = page;
	}
	/**
	 * 现实所有部门
	 * @return
	 */
	public String list(){
		//先获取页面传递过来的，经过创建后的page
		if(page==null){
			page=new Page<Dept>();
		}
		Page<Dept> findPage = deptService.findPage(page);
		page.setUrl(ServletActionContext.getRequest().getContextPath()+"/sysadmin/deptAction_list");
		//System.out.println(findPage);
		//将得到的page压入值栈
		this.set("page", findPage);
		return "list";
	}
	
	/**
	 * 接收页面请求，跳向创建部门的页面
	 * @return
	 */
	public String tocreate(){
		List<Dept> findDeptList = deptService.findDeptList();
		this.set("deptList",findDeptList);
		return "create";
	}
	
	/**
	 * 更新部门信息
	 * @return
	 */
	public String update(){
		model.setState(1);
		if(model.getParentDept().getId().equals("")||model.getParentDept().getId().equals("null")){
			model.setParentDept(null);
		}
		deptService.saveOrUpdate(model);
		return "relist";
	}
	
	/**
	 * 接收页面请求，眺望更新部门的页面
	 * @return
	 */
	public String toupdate(){
		if(model.getId()!=null){
			List<Dept> deptList = deptService.findDeptList();
			this.set("deptList", deptList);
			Dept dept = deptService.getDept(model);
			this.push(dept);
			return "update";
		}
		return "relist";
	}
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
				deptService.delete(split);
			}else{
				//单个删除
				deptService.deleteById(split[0]);
			}
		}
		return "relist";
	}
	
	
	/**
	 * 接收页面请求，跳向查看部门信息的页面
	 * @return
	 */
	public String toview(){
		/*String id = (String)ServletActionContext.getRequest().getParameter("id");
		model=new Dept();
		model.setId(id);*/
		if(model.getId()!=null){
			Dept dept = deptService.getDept(model);
			this.push(dept);
			return "view";
		}
		return "relist";
	}
	
	/**
	 * 新增部门信息
	 * @return
	 */
	public String insert(){
		model.setState(1);
		if(model.getParentDept().getId().equals("")||model.getParentDept().getId().equals("null")){
			model.setParentDept(null);
		}
		deptService.saveOrUpdate(model);
		return "relist";
	}
}
