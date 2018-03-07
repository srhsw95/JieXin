package cn.itcast.jk.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import cn.itcast.util.Encrypt;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{
	 public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
	  {
		//获取页面输入的密码 
	    UsernamePasswordToken upToken = (UsernamePasswordToken)token;
	    
	    // 获取页面的密码，以及用户名（作为md5加密的盐）,将此密码进行MD5转换
	    // 注意token.getPassword()拿到的是一个char[]，不能直接用toString()，它底层实现不是我们想的直接字符串，只能强转
	    String inputPwdMd5 = Encrypt.md5(new String(upToken.getPassword()), upToken.getUsername());
	    
	    //获取数据库中找到的对象的的md5类型的密码
	    String dbPwd = (String)info.getCredentials();
	    
	    //两者进行比较
	    return equals(inputPwdMd5, dbPwd);
	  }
}
