package cn.itcast.jk.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description:	PackingListService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:12:45
 */

public class PackingList extends Base implements Serializable{

	private String id;	  	
	private String seller;			
	private String buyer;			
	private String invoiceNo;			//选择
	private Date invoiceDate;			
	private String marks;			
	private String descriptions;			
	private String exportIds;			//报运ID集合
	private String exportNos;			//报运NO集合x,y|z,h
	private Integer state;			//0草稿 1已上报
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public String getExportIds() {
		return exportIds;
	}
	public void setExportIds(String exportIds) {
		this.exportIds = exportIds;
	}
	public String getExportNos() {
		return exportNos;
	}
	public void setExportNos(String exportNos) {
		this.exportNos = exportNos;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
		

	
	
	
}
