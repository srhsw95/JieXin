package cn.itcast.jk.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Description:
 * @Author:		传智播客 java学院	传智宋江
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2015年1月9日
 */
public class Export implements Serializable {
	private String id;
	private Date inputDate;
	private String contractIds;//合同的id列表  a,x,y
	private String customerContract;  //客户的合同号,可选择多个合同
	private String lcno;//信用证号
	private String consignee;//收货人及地址
	private String marks;//唛头  
	private String shipmentPort;//装运港
	private String destinationPort;//目的港
	private String transportMode;//运输方式 
	private String priceCondition;//价格条件
	private String remark;//备注
	private Integer boxNums;//箱数
	private Double grossWeights;//毛重
	private Double measurements;//总体积
	private Integer state;//状态
	private String createBy;  //创建者
	private String createDept;  //创建部门编号 
	private Date createTime;  //创建时间
	private Set<ExportProduct> exportProducts;//货物集合
	
	public Set<ExportProduct> getExportProducts() {
		return exportProducts;
	}
	public void setExportProducts(Set<ExportProduct> exportProducts) {
		this.exportProducts = exportProducts;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getContractIds() {
		return contractIds;
	}
	public void setContractIds(String contractIds) {
		this.contractIds = contractIds;
	}
	public String getCustomerContract() {
		return customerContract;
	}
	public void setCustomerContract(String customerContract) {
		this.customerContract = customerContract;
	}
	public String getLcno() {
		return lcno;
	}
	public void setLcno(String lcno) {
		this.lcno = lcno;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getShipmentPort() {
		return shipmentPort;
	}
	public void setShipmentPort(String shipmentPort) {
		this.shipmentPort = shipmentPort;
	}
	public String getDestinationPort() {
		return destinationPort;
	}
	public void setDestinationPort(String destinationPort) {
		this.destinationPort = destinationPort;
	}
	public String getTransportMode() {
		return transportMode;
	}
	public void setTransportMode(String transportMode) {
		this.transportMode = transportMode;
	}
	public String getPriceCondition() {
		return priceCondition;
	}
	public void setPriceCondition(String priceCondition) {
		this.priceCondition = priceCondition;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getBoxNums() {
		return boxNums;
	}
	public void setBoxNums(Integer boxNums) {
		this.boxNums = boxNums;
	}
	public Double getGrossWeights() {
		return grossWeights;
	}
	public void setGrossWeights(Double grossWeights) {
		this.grossWeights = grossWeights;
	}
	 
	public Double getMeasurements() {
		return measurements;
	}
	public void setMeasurements(Double measurements) {
		this.measurements = measurements;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDept() {
		return createDept;
	}
	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
