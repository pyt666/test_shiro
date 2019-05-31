package com.example.test_shiro.controller;

import com.example.test_shiro.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author pyt
 * @Date 2019/5/30 14:12
 * @Version
 */
@Controller
public class LoginController {
    @RequestMapping("/login")
    public ModelAndView login(Model model) {
        model.addAttribute("user", new User());
        return new ModelAndView("login", "userModel", model);
    }

    @PostMapping("/login")
    public ModelAndView add(User user) {
        //使用用户的登陆信息创建令牌
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        //获取当前用户
        Subject currentUser = SecurityUtils.getSubject();
//        Session session = currentUser.getSession();
//        session.setAttribute( "someKey", "aValue" );
        if ( !currentUser.isAuthenticated() ) {
            //是否记住用户密码
            token.setRememberMe(true);
            try {
                currentUser.login( token );
            } catch ( Exception e ) {
                System.out.println("登陆出错");
                System.out.println(e.getMessage());
            }
        }
        return new ModelAndView("redirect:/index");
    }
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
