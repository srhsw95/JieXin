package cn.itcast.jk.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.junit.internal.matchers.SubstringMatcher;

import cn.itcast.jk.action.BaseAction;
import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.Export;
import cn.itcast.jk.domain.PackingList;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ExportService;
import cn.itcast.jk.service.PackingListService;
import cn.itcast.util.UtilFuns;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;


/**
 * @Description:	PackingListService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:12:45
 */

public class PackingListAction extends BaseAction implements ModelDriven<PackingList> {
	private static final String User = null;
	//注入service
	private PackingListService packingListService;
	public void setPackingListService(PackingListService packingListService) {
		this.packingListService = packingListService;
	}
	private ExportService exportService;
	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	//model驱动
	private PackingList model = new PackingList();
	public PackingList getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page<Export> page = new Page<Export>();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}
	
	private Page<PackingList> page2 = new Page<PackingList>();			//封装页面的参数，主要当前页参数
	public void setPage2(Page page2) {
		this.page2 = page2;
	}


	//部门列表
	public String list(){
		if(page2==null){
			page2=new Page<PackingList>();
		}
		/*packingListService.findPage("from Export e where e.state=1", page2, null);*/
		Page<PackingList> findPage = packingListService.findPage("from PackingList", page2, PackingList.class, null);
		
		findPage.setUrl(ServletActionContext.getRequest().getContextPath()+"/cargo/packingListAction_list");
		
		this.set("page2", findPage);
		List<PackingList> results = findPage.getResults();
		this.set("dataList", results);
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		if(page==null){
			page=new Page<Export>();
		}
		Page<Export> findPage = exportService.findPage("from Export e where e.state=1", page, null);
		findPage.setUrl(ServletActionContext.getRequest().getContextPath()+"/cargo/packingListAction_tocreate");
		this.set("page", findPage);
		List<Export> results = findPage.getResults();
		this.set("dataList", results);
		return "pcreate";
	}
	
	//装箱业务
	public String insert(){
		User user = (User)session.get("_CURRENT_USER");
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		model.setCreateTime(new Date(System.currentTimeMillis()));
		packingListService.save(model);
		return "alist";			//返回列表，重定向action_list
	}

	//转向修改页面
	public String toupdate(){
		PackingList packingList = packingListService.get(PackingList.class, model.getId());
		this.push(packingList);
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		PackingList packingList = packingListService.get(PackingList.class, model.getId());
		
		
		packingListService.saveOrUpdate(packingList);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		packingListService.deleteById(PackingList.class, model.getId());
		
		return "alist";
	}
	
	
	//删除多条
	public String delete(){
		packingListService.delete(PackingList.class, model.getId().split(", "));
		
		return "alist";
	}
	
	//查看
	public String toview(){
		PackingList obj = packingListService.get(PackingList.class, model.getId());
		super.push(obj);
		
		return "pview";			//转向查看页
	}
	
	public String submit() throws Exception {
		String[] split = model.getId().split(", ");
		for (String id : split) {
			PackingList packingList = packingListService.get(PackingList.class, id);
			packingList.setState(1);
			packingListService.saveOrUpdate(packingList);
		}
		return "alist";
	}
	
	public String cancel() throws Exception {
		String[] split = model.getId().split(", ");
		for (String id : split) {
			PackingList packingList = packingListService.get(PackingList.class, id);
			packingList.setState(0);
			packingListService.saveOrUpdate(packingList);
		}
		return "alist";
	}
	
}
