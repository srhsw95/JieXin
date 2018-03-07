package cn.itcast.jk.action;

import java.util.Date;
import java.util.List;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Finance;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.FinanceService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:	FinanceService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:23:55
 */

public class FinanceAction extends BaseAction implements ModelDriven<Finance> {
	//注入service
	private FinanceService financeService;
	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}
	
	//model驱动
	private Finance model = new Finance();
	public Finance getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//部门列表
	public String list(){
		String hql = "from Finance o";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("financeAction_list");		//配置分页按钮的转向链接
		page = financeService.findPage(hql, page, Finance.class, null);
		super.put("page", page);
		
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		Finance finance=new Finance();
		finance.setId(model.getId());
		this.push(finance);
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		User user = (User)session.get("_CURRENT_USER");
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		model.setCreateTime(new Date(System.currentTimeMillis()));
		
		financeService.save(model);
		
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//准备部门的下拉列表
		/*List<Finance> financeList = financeService.financeList();
		super.put("financeList", financeList);		//页面就可以访问financeList
			*/	
		//准备修改的数据
		Finance obj = financeService.get(Finance.class, model.getId());
		super.push(obj);
		
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		Finance finance = financeService.get(Finance.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		/*finance.setFinanceId(model.getFinanceId());
		finance.setInputDate(model.getInputDate());
		finance.setInputBy(model.getInputBy());
		finance.setState(model.getState());
		finance.setCreateBy(model.getCreateBy());
		finance.setCreateDept(model.getCreateDept());
		finance.setCreateTime(model.getCreateTime());*/
		
		financeService.saveOrUpdate(finance);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		financeService.deleteById(Finance.class, model.getId());
		
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		financeService.delete(Finance.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		Finance obj = financeService.get(Finance.class, model.getId());
		super.push(obj);
		
		return "pview";			//转向查看页面
	}
}
