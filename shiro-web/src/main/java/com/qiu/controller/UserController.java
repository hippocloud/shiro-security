package com.qiu.controller;

import com.qiu.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class UserController {


    @RequestMapping(value = "subLogin",method = RequestMethod.POST,
    produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user){

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),
                user.getPassword());

        try {
            token.setRememberMe(user.isRememberMe());
            subject.login(token);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }

        if(subject.hasRole("admin")){
            return "有admin权限";
        }
        return "无admin权限";
    }

    @RequiresRoles("admin")//只有admin权限才可以掌握这个链接
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){
        return "testRole success";
    }

    @RequiresRoles("admin1")//只有admin1权限才可以掌握这个链接
    @RequestMapping(value = "/testRole1",method = RequestMethod.GET)
    @ResponseBody
    public String testRole1(){
        return "testRole success";
    }

    @RequestMapping(value = "/testRoles",method = RequestMethod.GET)
    @ResponseBody
    public String testRoles(){
        return "testRole success";
    }
    @RequestMapping(value = "/testRoles1",method = RequestMethod.GET)
    @ResponseBody
    public String testRoles1(){
        return "testRole success";
    }

    @RequestMapping(value = "/testPerms",method = RequestMethod.GET)
    @ResponseBody
    public String testPerms(){
        return "testRole success";
    }
    @RequestMapping(value = "/testPerms1",method = RequestMethod.GET)
    @ResponseBody
    public String testPerms1(){
        return "testRole success";
    }

}
