package cn.itcast.jk.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.jk.domain.Contract;
import cn.itcast.jk.domain.ContractProduct;
import cn.itcast.jk.pagination.Page;

public interface ContractProductService {
	//查询所有，带条件查询
	public List<ContractProduct> find(String hql, Object[] params);
	//获取一条记录
	public ContractProduct get(Serializable id);
	//分页查询，将数据封装到一个page分页工具类对象
	public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page, Object[] params);
	
	//新增和修改保存
	public void saveOrUpdate(ContractProduct entity);
	//批量新增和修改保存
	public void saveOrUpdateAll(Collection<ContractProduct> entitys);
	
	//单条删除，按id
	public void deleteById(Serializable id);
	//批量删除
	public void delete(Serializable[] ids);
	//保存货物
	public void save(ContractProduct model);
	//删除货物
	public void delete(ContractProduct model);
	//更新货物
	public void update(ContractProduct model);
}
