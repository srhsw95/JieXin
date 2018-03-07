/**
 * 
 */
package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.pagination.Page;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public interface DeptService {
    
	/**
	 * 添加一个部门
	 */
	public void saveOrUpdate(Dept dept);
	/**
	 * 查找分页page
	 * @param page
	 * @return
	 */
	public Page findPage(Page<Dept> page);
	/**
	 * 查找部门
	 * @param model
	 * @return
	 */
	public Dept getDept(Dept model);
	/**
	 * 查找所有部门
	 * @return
	 */
	public List<Dept> findDeptList();
	/**
	 * 保存或更新所有
	 * @param dept
	 */
	public void saveOrUpdateAll(Collection<Dept> dept);
	/**
	 * 删除单个部门
	 * @param id
	 */
	public void deleteById(String id);
	/**
	 * 批量删除部门
	 * @param ids
	 */
	public void delete(String[] ids);
	
		
}
