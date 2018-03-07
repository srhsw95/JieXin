package cn.itcast.jk.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.Export;
import cn.itcast.jk.domain.ExportProduct;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.ContractService;
import cn.itcast.jk.service.ExportProductService;
import cn.itcast.jk.service.ExportService;
import cn.itcast.util.UtilFuns;

import com.opensymphony.xwork2.ModelDriven;

public class ExportAction extends BaseAction implements ModelDriven<Export>{
	
	private static final long serialVersionUID = 1L;
	//注入service层
	private ExportService exportService;
	
	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	
	private ContractService contractService;
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}
	private ExportProductService exportProductService;
	public void setExportProductService(ExportProductService exportProductService) {
		this.exportProductService = exportProductService;
	}

	private Page<Contract> page;
	
	public void setPage(Page<Contract> page) {
		this.page = page;
	}
	
	private Page<Export> page2;
	
	public void setPage2(Page<Export> page2) {
		this.page2 = page2;
	}
	
	
	private Export model=new Export();
	public Export getModel() {
		return model;
	}
	
	/*
	 * 列出已上报的购销合同列表
	 */
	public String contractList() throws Exception {
		//查找已上报的合同
		if(page==null){
			page=new Page<Contract>();
		}
		Page<Contract> findPage = contractService.findPage("from Contract c where c.state=1", page, null);
		findPage.setUrl(ServletActionContext.getRequest().getContextPath()+"/cargo/exportAction_contractList");
		this.set("page", findPage);
		List<Contract> results = findPage.getResults();
		this.set("dataList", results);
		return "contractList";
	}
	
	/*
	 * 前往创建出口报运单页面
	 */
	public String toinsert() throws Exception {
		Contract contract=new Contract();
		contract.setId(model.getId());
		this.put("contract", contract);
		return "toinsert";
	}
	/*
	 * 添加报运单
	 */
	public String insert() throws Exception{
		exportService.save(model);
		return contractList();
	}
	
	/*
	 * 跳转显示报运单页面
	 */
	public String list() throws Exception {
		if(page2==null){
			page2=new Page<Export>();
		}
		Page<Export> findPage = exportService.findPage("from Export", page2, null);
		findPage.setUrl(ServletActionContext.getRequest().getContextPath()+"/cargo/exportAction_list");
		this.set("page2", findPage);
		List<Export> results = findPage.getResults();
		this.set("dataList", results);
		return "list";
	}
	
	/*
	 * 查看报运表
	 */
	public String toview() throws Exception {
		Export export = exportService.get(model.getId());
		this.push(export);
		return "toview";
	}
	
	/*
	 * 修改报运表
	 */
	public String toupdate() throws Exception {
		//压入找到的报运表
		Export export = exportService.get(model.getId());
		this.push(export);
		Set<ExportProduct> exportProducts = export.getExportProducts();
		//拼接js方法字符串
		StringBuilder sb =new StringBuilder();
		for (ExportProduct exportProduct : exportProducts) {
			sb.append("addTRRecord(")
			.append("'mRecordTable'")
			.append(" , '"+UtilFuns.convertNull(exportProduct.getId())+"'")
			.append(" , '"+UtilFuns.convertNull(exportProduct.getProductNo())+"'")
			.append(" , '"+UtilFuns.convertNull(exportProduct.getCnumber())+"'")
			.append(" , '"+UtilFuns.convertNull(exportProduct.getGrossWeight())+"'")
			.append(" , '"+UtilFuns.convertNull(exportProduct.getNetWeight())+"'")
			.append(" , '"+UtilFuns.convertNull(exportProduct.getSizeLength())+"'")
			.append(" , '"+UtilFuns.convertNull(exportProduct.getSizeWidth())+"'")
			.append(" , '"+UtilFuns.convertNull(exportProduct.getSizeHeight())+"'")
			.append(" , '"+UtilFuns.convertNull(exportProduct.getExPrice())+"'")
			.append(" , '"+UtilFuns.convertNull(exportProduct.getTax())+"'")
			.append(") ;");
		}
		System.out.println(sb.toString());
		this.put("mRecordData", sb.toString());
		return "toupdate";
	}
	
	/*
	 * 删除报运单
	 */
	public String delete() throws Exception {
		exportService.delete(model);
		return "relist";
	}
	
	public String submit() throws Exception {
		String[] split = model.getId().split(", ");
		for (String id : split) {
			Export export = exportService.get(id);
			export.setState(1);
			exportService.saveOrUpdate(export);
		}
		return "relist";
	}
	
	public String cancel() throws Exception {
		String[] split = model.getId().split(", ");
		for (String id : split) {
			Export export = exportService.get(id);
			export.setState(0);
			exportService.saveOrUpdate(export);
		}
		return "relist";
	}
	
	/*
	 * 更新报运单
	 */
	public String update() throws Exception {
		String[] mr_id2 = this.getMr_id();
		Export export = exportService.get(model.getId());
		export.setLcno(model.getLcno());
		export.setConsignee(model.getConsignee());
		export.setInputDate(model.getInputDate());
		export.setShipmentPort(model.getShipmentPort());
		export.setDestinationPort(model.getDestinationPort());
		export.setTransportMode(model.getTransportMode());
		export.setPriceCondition(model.getPriceCondition());
		export.setMarks(model.getMarks());
		export.setRemark(model.getRemark());
		Set<ExportProduct> exportProducts = new HashSet<ExportProduct>();
		for (int i = 0; i < mr_id.length; i++) {
			ExportProduct exportProduct = exportProductService.get(mr_id[i]);
			if("1".equals(mr_changed[i])){
				exportProduct.setOrderNo(mr_orderNo[i]);
				exportProduct.setCnumber(mr_cnumber[i]);
				exportProduct.setGrossWeight(mr_grossWeight[i]);
				exportProduct.setNetWeight(mr_netWeight[i]);
				exportProduct.setSizeLength(mr_sizeLength[i]);
				exportProduct.setSizeWidth(mr_sizeWidth[i]);
				exportProduct.setSizeHeight(mr_sizeHeight[i]);
				exportProduct.setExPrice(mr_exPrice[i]);
				exportProduct.setTax(mr_tax[i]);
			}
			exportProducts.add(exportProduct);
		}
		export.setExportProducts(exportProducts);
		exportService.saveOrUpdate(export);
		return "relist";
	}
	//利用属性注入
	private String[] mr_id;
	private String[] mr_changed;
	private Integer[] mr_orderNo;
	private Integer[] mr_cnumber;
	private Double[] mr_grossWeight;
	private Double[] mr_netWeight;
	private Double[] mr_sizeLength;
	private Double[] mr_sizeWidth;
	private Double[] mr_sizeHeight;
	private Double[] mr_exPrice;
	private Double[] mr_tax;

	public String[] getMr_id() {
		return mr_id;
	}

	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}

	public String[] getMr_changed() {
		return mr_changed;
	}

	public void setMr_changed(String[] mr_changed) {
		this.mr_changed = mr_changed;
	}

	public Integer[] getMr_orderNo() {
		return mr_orderNo;
	}

	public void setMr_orderNo(Integer[] mr_orderNo) {
		this.mr_orderNo = mr_orderNo;
	}

	public Integer[] getMr_cnumber() {
		return mr_cnumber;
	}

	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}

	public Double[] getMr_grossWeight() {
		return mr_grossWeight;
	}

	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}

	public Double[] getMr_netWeight() {
		return mr_netWeight;
	}

	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}

	public Double[] getMr_sizeLength() {
		return mr_sizeLength;
	}

	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}

	public Double[] getMr_sizeWidth() {
		return mr_sizeWidth;
	}

	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}

	public Double[] getMr_sizeHeight() {
		return mr_sizeHeight;
	}

	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}

	public Double[] getMr_exPrice() {
		return mr_exPrice;
	}

	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}

	public Double[] getMr_tax() {
		return mr_tax;
	}

	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}
	

}
