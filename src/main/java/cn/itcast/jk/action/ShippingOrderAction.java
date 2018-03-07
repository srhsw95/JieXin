package cn.itcast.jk.action;

import java.util.Date;
import java.util.List;


import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.PackingList;
import cn.itcast.jk.domain.ShippingOrder;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.PackingListService;
import cn.itcast.jk.service.ShippingOrderService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:	ShippingOrderService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:23:17
 */

public class ShippingOrderAction extends BaseAction implements ModelDriven<ShippingOrder> {
	//注入service
	private ShippingOrderService shippingOrderService;
	public void setShippingOrderService(ShippingOrderService shippingOrderService) {
		this.shippingOrderService = shippingOrderService;
	}
	private PackingListService packingListService;
	public void setPackingListService(PackingListService packingListService) {
		this.packingListService = packingListService;
	}
	//model驱动
	private ShippingOrder model = new ShippingOrder();
	public ShippingOrder getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//部门列表
	public String list(){
		String hql = "from ShippingOrder o";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("shippingOrderAction_list");		//配置分页按钮的转向链接
		page = shippingOrderService.findPage(hql, page, ShippingOrder.class, null);
		super.put("page", page);
		
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		List<PackingList> find = packingListService.find("from PackingList p where p.state=1", PackingList.class, null);
		this.put("dataList", find);
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		User user = (User)session.get("_CURRENT_USER");
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		model.setCreateTime(new Date(System.currentTimeMillis()));
		shippingOrderService.save(model);
		return "alist";	 //返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		//准备部门的下拉列表
		/*List<ShippingOrder> shippingOrderList = shippingOrderService.shippingOrderList();
		super.put("shippingOrderList", shippingOrderList);		//页面就可以访问shippingOrderList
		*/		
		//准备修改的数据
		ShippingOrder obj = shippingOrderService.get(ShippingOrder.class, model.getId());
		super.push(obj);
		
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		ShippingOrder shippingOrder = shippingOrderService.get(ShippingOrder.class, model.getId());
		
		/*//设置修改的属性，根据业务去掉自动生成多余的属性
		shippingOrder.setShippingOrderId(model.getShippingOrderId());
		shippingOrder.setOrderType(model.getOrderType());
		shippingOrder.setShipper(model.getShipper());
		shippingOrder.setConsignee(model.getConsignee());
		shippingOrder.setNotifyParty(model.getNotifyParty());
		shippingOrder.setLcNo(model.getLcNo());
		shippingOrder.setPortOfLoading(model.getPortOfLoading());
		shippingOrder.setPortOfTrans(model.getPortOfTrans());
		shippingOrder.setPortOfDischarge(model.getPortOfDischarge());
		shippingOrder.setLoadingDate(model.getLoadingDate());
		shippingOrder.setLimitDate(model.getLimitDate());
		shippingOrder.setIsBatch(model.getIsBatch());
		shippingOrder.setIsTrans(model.getIsTrans());
		shippingOrder.setCopyNum(model.getCopyNum());
		shippingOrder.setRemark(model.getRemark());
		shippingOrder.setSpecialCondition(model.getSpecialCondition());
		shippingOrder.setFreight(model.getFreight());
		shippingOrder.setCheckBy(model.getCheckBy());
		shippingOrder.setState(model.getState());
		shippingOrder.setCreateBy(model.getCreateBy());
		shippingOrder.setCreateDept(model.getCreateDept());
		shippingOrder.setCreateTime(model.getCreateTime());
		*/
		shippingOrderService.saveOrUpdate(shippingOrder);
		
		return "alist";
	}

	
	
	//删除多条
	public String delete(){
		
		shippingOrderService.delete(ShippingOrder.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		ShippingOrder obj = shippingOrderService.get(ShippingOrder.class, model.getId());
		super.push(obj);
		
		return "pview";			//转向查看页面
	}
}
