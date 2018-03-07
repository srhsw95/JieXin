/**
 * 
 */
package cn.itcast.jk.domain;

import java.util.Set;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public class Dept {
   private String id;
   private String deptName;
   private Dept parentDept;//代表父部门
   private Integer state;
   private Set<User> users;
   
   
public Set<User> getUsers() {
	return users;
}

public void setUsers(Set<User> users) {
	this.users = users;
}

public Dept() {
}

public Dept(String id, String deptName, Dept parentDept, Integer state) {
	super();
	this.id = id;
	this.deptName = deptName;
	this.parentDept = parentDept;
	this.state = state;
}
public Dept( String deptName, Dept parentDept, Integer state) {
	super();

	this.deptName = deptName;
	this.parentDept = parentDept;
	this.state = state;
}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Dept getParentDept() {
		return parentDept;
	}
	public void setParentDept(Dept parentDept) {
		this.parentDept = parentDept;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	 
   
}
