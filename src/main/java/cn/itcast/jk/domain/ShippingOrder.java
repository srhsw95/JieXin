package cn.itcast.jk.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description:	ShippingOrderService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:23:17
 */

public class ShippingOrder extends Base implements Serializable{

	private String id;	  	
	private String orderType;			//SEA海运AIR空运
	private String shipper;			
	private String consignee;			
	private String notifyParty;			
	private String lcNo;			
	private String portOfLoading;			
	private String portOfTrans;			
	private String portOfDischarge;			
	private Date loadingDate;			
	private Date limitDate;			
	private String isBatch;			//1是0否
	private String isTrans;			//1是0否
	private String copyNum;			
	private String remark;			
	private String specialCondition;			
	private String freight;			
	private String checkBy;			
	private Integer state;			//0草稿 1已上报
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getShipper() {
		return shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getNotifyParty() {
		return notifyParty;
	}
	public void setNotifyParty(String notifyParty) {
		this.notifyParty = notifyParty;
	}
	public String getLcNo() {
		return lcNo;
	}
	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}
	public String getPortOfLoading() {
		return portOfLoading;
	}
	public void setPortOfLoading(String portOfLoading) {
		this.portOfLoading = portOfLoading;
	}
	public String getPortOfTrans() {
		return portOfTrans;
	}
	public void setPortOfTrans(String portOfTrans) {
		this.portOfTrans = portOfTrans;
	}
	public String getPortOfDischarge() {
		return portOfDischarge;
	}
	public void setPortOfDischarge(String portOfDischarge) {
		this.portOfDischarge = portOfDischarge;
	}
	public Date getLoadingDate() {
		return loadingDate;
	}
	public void setLoadingDate(Date loadingDate) {
		this.loadingDate = loadingDate;
	}
	public Date getLimitDate() {
		return limitDate;
	}
	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}
	public String getIsBatch() {
		return isBatch;
	}
	public void setIsBatch(String isBatch) {
		this.isBatch = isBatch;
	}
	public String getIsTrans() {
		return isTrans;
	}
	public void setIsTrans(String isTrans) {
		this.isTrans = isTrans;
	}
	public String getCopyNum() {
		return copyNum;
	}
	public void setCopyNum(String copyNum) {
		this.copyNum = copyNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSpecialCondition() {
		return specialCondition;
	}
	public void setSpecialCondition(String specialCondition) {
		this.specialCondition = specialCondition;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
		

	
	
}
