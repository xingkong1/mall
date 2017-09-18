package shiro;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class LoginTest {

	@Test
	public void test(){
		Factory<SecurityManager> factory=new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager=factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken("zhang", "123");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			System.out.println(e.getStackTrace());
		}
		Assert.assertEquals(true, subject.isAuthenticated());
		Assert.assertTrue(subject.hasRole("role1"));  
	    //判断拥有角色：role1 and role2  
	    Assert.assertTrue(subject.hasAllRoles(Arrays.asList("role1", "role2")));  
	    //判断拥有角色：role1 and role2 and !role3  
	    boolean[] result = subject.hasRoles(Arrays.asList("role1", "role2", "role3"));  
	    Assert.assertEquals(true, result[0]);  
	    Assert.assertEquals(true, result[1]);  
	    Assert.assertEquals(false, result[2]);  
	    
	    //判断拥有权限：user:create  
	    Assert.assertTrue(subject.isPermitted("user:create"));  
	    //判断拥有权限：user:update and user:delete  
	    Assert.assertTrue(subject.isPermittedAll("user:update", "user:create"));  
	    //判断没有权限：user:view  
	    Assert.assertFalse(subject.isPermitted("user:view"));  
	    
	    subject.checkPermission("user:create");  
	    //断言拥有权限：user:delete and user:update  
	    subject.checkPermissions("user:delete", "user:update");  
	    //断言拥有权限：user:view 失败抛出异常  
	    subject.checkPermissions("user:view");  
		
		subject.logout();
	}

}
