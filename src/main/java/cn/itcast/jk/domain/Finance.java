package cn.itcast.jk.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * @Description:	FinanceService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:23:55
 */

public class Finance extends Base implements Serializable{

	private String id;	  	
	private Date inputDate;			
	private String inputBy;			
	private Integer state;			//0草稿 1已上报
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
	public String getInputBy() {
		return inputBy;
	}
	public void setInputBy(String inputBy) {
		this.inputBy = inputBy;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
			
}
