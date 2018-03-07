package cn.itcast.jk.domain;

import java.io.Serializable;


/**
 * @Description:	InvoiceService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:24:40
 */

public class Invoice extends Base implements Serializable{

	private String id;	  	
	private String scNo;			//packing.getExportNos S/C就是报运的合同号
	private String blNo;			
	private String tradeTerms;			
	private Integer state;			//0草稿 1已上报
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScNo() {
		return scNo;
	}
	public void setScNo(String scNo) {
		this.scNo = scNo;
	}
	public String getBlNo() {
		return blNo;
	}
	public void setBlNo(String blNo) {
		this.blNo = blNo;
	}
	public String getTradeTerms() {
		return tradeTerms;
	}
	public void setTradeTerms(String tradeTerms) {
		this.tradeTerms = tradeTerms;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
		

	
	
	
}
