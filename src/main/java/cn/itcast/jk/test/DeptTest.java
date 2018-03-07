/**
 * 
 */
package cn.itcast.jk.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.jk.domain.Dept;
import cn.itcast.jk.pagination.Page;
import cn.itcast.jk.service.DeptService;

/**
 * @description:
 * @author 传智.宋江
 * @date 2015年9月8日
 * @version 1.0
 */
public class DeptTest {

	@Test
	public void testAddDept(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		DeptService deptService = (DeptService) ac.getBean("deptService");
		
		Page<Dept> page=new Page<Dept>();
		
		
		Page findPage = deptService.findPage(page);
		
		System.out.println(findPage);
	}
	
	@Test
	public void testAddDept2(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		DeptService deptService = (DeptService) ac.getBean("deptService");
		
		Dept dept=new Dept();
		dept.setDeptName("111");
		deptService.saveOrUpdate(dept);
	}
}
