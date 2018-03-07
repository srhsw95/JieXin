package cn.itcast.jk.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import cn.itcast.jk.dao.BaseDao;
import cn.itcast.jk.domain.Export;
import cn.itcast.jk.domain.PackingList;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.PackingListService;
import cn.itcast.util.UtilFuns;

/**
 * @Description:	PackingListService接口
 * @Author:			若止绝尘
 * @Company:		www.shironghua.com
 * @CreateDate:		2015-9-21 21:12:45
 */

public class PackingListServiceImpl implements PackingListService {
	
	private static final Object[][] String = null;
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public List<PackingList> find(String hql, Class<PackingList> entityClass, Object[] params) {
		return baseDao.find(hql, PackingList.class, params);
	}

	public PackingList get(Class<PackingList> entityClass, Serializable id) {
		return baseDao.get(PackingList.class, id);
	}

	public Page<PackingList> findPage(String hql, Page<PackingList> page, Class<PackingList> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, PackingList.class, params);
	}

	public void saveOrUpdate(PackingList entity) {
		if(entity.getId()==null){								//代表新增
			entity.setState(1);									//状态：0停用1启用 默认启用
		}
		baseDao.saveOrUpdate(entity);
	}

	public void save(Collection<PackingList> entitys) {
		baseDao.saveOrUpdate(entitys);
	}

	public void saveOrUpdateAll(Collection<PackingList> entitys) {
		baseDao.saveOrUpdateAll(entitys);
	}

	public void deleteById(Class<PackingList> entityClass, Serializable id) {
		PackingList packingList = baseDao.get(PackingList.class, id);
		String[] split = packingList.getExportIds().split(",");
		String joinInStr = UtilFuns.joinInStr(split);
		List<Export> find = baseDao.find("from Export e where e.id in("+joinInStr+")", Export.class, null);
		for (Export export : find) {
			export.setState(1);
			baseDao.saveOrUpdate(export);
		}
		baseDao.deleteById(PackingList.class, id);
	}

	public void delete(Class<PackingList> entityClass, Serializable[] ids) {
		//直接调用上面的方法，
		for (Serializable id : ids) {
			deleteById(PackingList.class,id);
		}
	}

	public void save(PackingList model) {
		PackingList packingList=new PackingList();
		packingList.setSeller(model.getSeller());
		packingList.setBuyer(model.getBuyer());
		packingList.setInvoiceNo(model.getInvoiceNo());
		packingList.setInvoiceDate(model.getInvoiceDate());
		packingList.setMarks(model.getMarks());
		packingList.setDescriptions(model.getDescriptions());
		packingList.setId(UUID.randomUUID().toString());
		String[] split = model.getId().split(", ");
		String joinInStr = UtilFuns.joinInStr(split);
		StringBuilder stringBuilder1=new StringBuilder();
		StringBuilder stringBuilder2=new StringBuilder();
		List<Export> find = baseDao.find("from Export e where e.id in ("+joinInStr+")", Export.class, null);
		for (Export export : find) {
			export.setState(2);
			baseDao.saveOrUpdate(export);
			stringBuilder1.append(export.getId()+",");
			stringBuilder2.append(export.getLcno()+" ");
		}
		String substring=null;
		String substring2=null;
		if(find!=null&&find.size()>0){
			substring = stringBuilder1.substring(0, stringBuilder1.length()-1);
			substring2 = stringBuilder2.substring(0, stringBuilder2.length()-1);
		}
		packingList.setExportIds(substring);
		packingList.setExportNos(substring2);
		packingList.setCreateBy(model.getCreateBy());
		packingList.setCreateDept(model.getCreateDept());
		packingList.setCreateTime(model.getCreateTime());
		packingList.setState(0);
		baseDao.saveOrUpdate(packingList);
	}

}
