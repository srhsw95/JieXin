package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.domain.User;
import cn.itcast.jk.pagination.Page;

public interface UserService {
	//查询所有，带条件查询
	public List<User> find(String hql, Object[] params);
	//获取一条记录
	public User get(Serializable id);
	//分页查询，将数据封装到一个page分页工具类对象
	public Page<User> findPage(String hql, Page<User> page, Object[] params);
	
	//新增和修改保存
	public void saveOrUpdate(User entity);
	//批量新增和修改保存
	public void saveOrUpdateAll(Collection<User> entitys);
	
	//单条删除，按id
	public void deleteById(Serializable id);
	//批量删除
	public void delete(Serializable[] ids);
	//根据用户名查找用户信息
	public User findUserByUsername(String username);
}
