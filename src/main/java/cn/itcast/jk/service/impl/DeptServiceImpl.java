/**
 * 
 */
package cn.itcast.jk.service.impl;


import java.util.Collection;
import java.util.List;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.DeptService;

/**
 * @description
 * @date 2015年9月8日
 * @version 1.0
 */
public class DeptServiceImpl implements DeptService {
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void saveOrUpdate(Dept dept) {
		baseDao.saveOrUpdate(dept);

	}

	public Page<Dept> findPage(Page<Dept> page) {
		// TODO Auto-generated method stub
		String hql="from Dept d where d.state=1";
		Page<Dept> findPage = baseDao.findPage(hql, page, Dept.class, null);
		return findPage;
	}

	public Dept getDept(Dept model) {
		Dept dept = baseDao.get(Dept.class, model.getId());
		return dept;
	}

	public List<Dept> findDeptList() {
		String hql="from Dept d where d.state=1";
		List<Dept> find = baseDao.find(hql, Dept.class, null);
		return find;
	}

	public void saveOrUpdateAll(Collection<Dept> dept) {
		baseDao.saveOrUpdateAll(dept);
	}

	public void deleteById(String id) {
		//baseDao.deleteById(Dept.class, id);
		//1.要得到当前这个父部门的所有子部门
		List<Dept> find = baseDao.find("from Dept d where d.parentDept.id=?", Dept.class, new Object[]{id});
		if(find.size()>0){
			for (Dept dept : find) {
				deleteById(dept.getId());
				dept.setState(0);
				baseDao.saveOrUpdate(dept);
			}
		}
		Dept dept = baseDao.get(Dept.class, id);
		dept.setState(0);
		//baseDao.saveOrUpdate(dept);
	}

	public void delete(String[] ids) {
		//baseDao.delete(Dept.class, ids);
		for (String id : ids) {
			deleteById(id);
		}
	}
}
