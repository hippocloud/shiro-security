package com.qiu.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.junit.Test;

public class JdbcRealmTest {

    DruidDataSource dataSource =new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }

    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);//默认权限是关闭的，只有打开才可以

        String sql = "select password from test_user where user_name=?";
        jdbcRealm.setAuthenticationQuery(sql);

        //不知道为什么数据表test_user_role里的数据匹配不了，难道是druid和mysql版本的冲突
        String roleSql = "select role_name from test_user_role where user_name=?";
        jdbcRealm.setAuthenticationQuery(roleSql);

        //构建securityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jack","123");
        subject.login(token);

        System.out.println("iaAuthenticated:"+ subject.isAuthenticated());


        //subject.checkRole("admin");
        //subject.checkPermission("user:select");
        subject.checkRole("user");


    }

}
