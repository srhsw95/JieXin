package cn.itcast.jk.action;

import java.util.Date;
import java.util.List;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Invoice;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.InvoiceService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:	InvoiceService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:24:40
 */

public class InvoiceAction extends BaseAction implements ModelDriven<Invoice> {
	//注入service
	private InvoiceService invoiceService;
	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	//model驱动
	private Invoice model = new Invoice();
	public Invoice getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//部门列表
	public String list(){
		String hql = "from Invoice o";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("invoiceAction_list");		//配置分页按钮的转向链接
		page = invoiceService.findPage(hql, page, Invoice.class, null);
		super.put("page", page);
		
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		Invoice invoice =new Invoice();
		invoice.setId(model.getId());
		this.push(invoice);
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		User user = (User)session.get("_CURRENT_USER");
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		model.setCreateTime(new Date(System.currentTimeMillis()));
		invoiceService.save(model);
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//准备部门的下拉列表
	/*	List<Invoice> invoiceList = invoiceService.invoiceList();
		super.put("invoiceList", invoiceList);		*///页面就可以访问invoiceList
				
		//准备修改的数据
		Invoice obj = invoiceService.get(Invoice.class, model.getId());
		super.push(obj);
		
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		Invoice invoice = invoiceService.get(Invoice.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		/*invoice.setInvoiceId(model.getInvoiceId());
		invoice.setScNo(model.getScNo());
		invoice.setBlNo(model.getBlNo());
		invoice.setTradeTerms(model.getTradeTerms());
		invoice.setState(model.getState());
		invoice.setCreateBy(model.getCreateBy());
		invoice.setCreateDept(model.getCreateDept());
		invoice.setCreateTime(model.getCreateTime());
		*/
		invoiceService.saveOrUpdate(invoice);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		invoiceService.deleteById(Invoice.class, model.getId());
		
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		invoiceService.delete(Invoice.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		Invoice obj = invoiceService.get(Invoice.class, model.getId());
		super.push(obj);
		
		return "pview";			//转向查看页面
	}
}
