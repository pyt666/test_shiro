package com.example.test_shiro;

import com.example.test_shiro.entity.User;
import com.example.test_shiro.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestShiroApplicationTests {
    @Autowired
    private LoginService loginService;
    @Test
    public void test(){
        User user = loginService.findByUsername("test1");
        System.out.println(user);
    }
    @Test
    public void contextLoads() {
        // 读取 shiro.ini 文件内容
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
//        SecurityUtils.setSecurityManager(securityManager);

        Subject currentUser = SecurityUtils.getSubject();

        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            System.out.println("someKey 的值：" + value);
        }

        // 登陆
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "zhangsan");
        token.setRememberMe(true);
        try {
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            System.out.println("用户名不存在:" + token.getPrincipal());
        } catch (IncorrectCredentialsException ice) {
            System.out.println("账户密码 " + token.getPrincipal()  + " 不正确!");
        } catch (LockedAccountException lae) {
            System.out.println("用户名 " + token.getPrincipal() + " 被锁定 !");
        }

        // 认证成功后
        if (currentUser.isAuthenticated()) {

            System.out.println("用户 " + currentUser.getPrincipal() + " 登陆成功！");

            //测试角色
            System.out.println("是否拥有 manager 角色：" + currentUser.hasRole("manager"));

            //测试权限
            System.out.println("是否拥有 user:create 权限" + currentUser.isPermitted("user:create"));

            //退出
            currentUser.logout();
        }
    }

}
