package com.qiu.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.junit.Test;

public class IniRealmTest {

    @Test
    public void testAuthentication(){


        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jack","123");
        subject.login(token);

        System.out.println("iaAuthenticated:"+ subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user:delete");
    }

}
