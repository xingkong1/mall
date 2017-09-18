package mall.shiro;

import javax.annotation.Resource;

import mall.dao.UserDAO;
import mall.entity.User;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomRealm extends AuthorizingRealm {

	@Resource
	UserDAO userDAO;
	
   Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 设置realm的名称
	 */
	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String username = (String)principals.getPrimaryPrincipal();  
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();  
        authorizationInfo.setRoles(null);  /*userService.findRoles(username)*/
        authorizationInfo.setStringPermissions(null);  /*userService.findPermissions(username)*/
        return authorizationInfo;  
	}

	/**
	 * realm的认证方法，从数据库查询用户信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		// token是用户输入的用户名和密码 
	    // 第一步从token中取出用户名
		String username=(String)token.getPrincipal();
		
		// 第二步：根据用户输入的username从数据库查询
		User user=null;
		try {
			user=userDAO.getByName(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(user==null)
			return null;
		SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(user.getName(), user.getPassword(), this.getName());
		return simpleAuthenticationInfo;
	}



}
