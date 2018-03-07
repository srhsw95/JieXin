package cn.itcast.jk.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.jk.domain.Module;
import cn.itcast.jk.domain.Role;
import cn.itcast.jk.domain.User;
import cn.itcast.jk.service.UserService;

public class AuthRealm  extends AuthorizingRealm{
	//注入userService
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权");
		//获取当前用户
		User user = (User)principals.fromRealm(getName()).iterator().next();
		//得到权限字符串//简单授权认证信息
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		Set<Role> roles = user.getRoles();
		//创建一个集合
		List<String> list = new ArrayList();
		for(Role role :roles){
			Set<Module> modules = role.getModules();
			for(Module m:modules){
				//添加主菜单
				if(m.getCtype()==0){
					//说明是主菜单
					list.add(m.getCpermission());
				}
			}
		}
		info.addStringPermissions(list);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证");
		//得到页面的录入信息
	    UsernamePasswordToken upToken = (UsernamePasswordToken)token;
	    //获取用户名
	    String username = upToken.getUsername();
	    //前往数据库查找是否存在此用户
	    User user = this.userService.findUserByUsername(username);
	    //如果不存在返回null;
	    if (user == null) {
	      return null;
	    }
	    //如果存在，进一步验证密码；
	    return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
	}
	
}
