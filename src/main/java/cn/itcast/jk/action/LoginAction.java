package cn.itcast.jk.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import cn.itcast.common.SysConstant;
import cn.itcast.jk.domain.Module;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.domain.User;
import cn.itcast.util.UtilFuns;

/**
 * @Description: 登录和退出类
 * @Author:		传智播客 java学院	传智.宋江
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014年10月31日
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;



	//SSH传统登录方式
	public String login() throws Exception {
		
		if (UtilFuns.isEmpty(this.username)) {
		      return "login";
		    }
		    try
		    {
		      //获取subject对象
		      Subject subject = SecurityUtils.getSubject();
		      //UsernamePasswordToken为shiro框架中的一个对象，用于接收username,password;其中password为char[]类型，这边的构造，在接收到password后转换为char数组
		      UsernamePasswordToken token = new UsernamePasswordToken(this.username, this.password);
		      //进行认证？
		      subject.login(token);
		      
		      User user = (User)subject.getPrincipal();
		      
		      System.out.println(user.getDept().getDeptName());
		      
		      Set<Role> roles = user.getRoles();
		      for (Role role : roles) {
		        Set<Module> modules = role.getModules();
		        for (Module m : modules) {
		          System.out.println(m.getCpermission());
		        }
		        
		      }
		      //将用户信息存入session中 此时session中的数据是经过加载过的；
		      this.session.put(SysConstant.CURRENT_USER_INFO, user);
		      
		      //修改，将登录的用户，遍历其模块，获取模块的
		      Set<Role> roles2 = user.getRoles();
		      //申明一个集合，存放扫描过的模块，
		      List<Module> moduleList=new ArrayList<Module>();
		      //申明一个集合存放Module的id
		      List<String> moduleIds=new ArrayList<String>();
		      for (Role role : roles2) {
		    	  Set<Module> modules = role.getModules();
		    	  for (Module module : modules) {
					//保证所有的模块名字不相同
		    		  if(moduleList.size()==0){
		    			  moduleList.add(module);
		    		  }
		    		  
		    		  for(Module module2: moduleList){
		    			  moduleIds.add(module2.getId());
		    		  }
		    		  if(!moduleIds.toString().contains(module.getId())){
		    			  moduleList.add(module);
		    		  }
				}
			}
		      //针对模块进行排序操作
		      Collections.sort(moduleList, new Comparator<Module>(){  
		    	  
		            /*  
		             * int compare(Student o1, Student o2) 返回一个基本类型的整型，  
		             * 返回负数表示：o1 小于o2，  
		             * 返回0 表示：o1和o2相等，  
		             * 返回正数表示：o1大于o2。  
		             */  
		            public int compare(Module m1, Module m2) {  
		              
		                //排序
		                if(new Integer(m1.getId()) > new Integer(m2.getId())){  
		                    return 1;  
		                }  
		                if(new Integer(m1.getId()) == new Integer(m2.getId())){  
		                    return 0;  
		                }  
		                return -1;  
		            }  
		        });
		      
		  //  this.set("filteredModules", moduleList);
		    this.session.put("filteredModules", moduleList);
		    } catch (Exception e) {
		      super.put("errorInfo", "用户名或密码错误，请重新登录");
		      e.printStackTrace();
		      return "login";
		    }
		    return "success";
	}
	
	
	//退出
	public String logout(){
		session.remove(SysConstant.CURRENT_USER_INFO);		//删除session
		
		return "logout";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

