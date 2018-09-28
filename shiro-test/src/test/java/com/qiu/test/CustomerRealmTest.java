package com.qiu.test;

import com.qiu.shiro.realm.CustomerRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.junit.Test;

public class CustomerRealmTest {

    @Test
    public void testAuthentication(){

        CustomerRealm customerRealm = new CustomerRealm();

        //1构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customerRealm);

        //加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customerRealm.setCredentialsMatcher(matcher);

        //2主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jack","123");
        subject.login(token);

        System.out.println("iaAuthenticated:"+ subject.isAuthenticated());
       // subject.checkRole("admin");
       // subject.checkPermission("user:delete");
        subject.checkRole("admin");
        subject.checkPermissions("user:delete","user:add");
    }

}
