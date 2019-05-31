package com.example.test_shiro.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * @ClassName TestService
 * @Description TODO
 * @Author pyt
 * @Date 2019/5/21 16:30
 * @Version
 */
@Service
public class TestService {
    public void test(){
        Subject currentUser = SecurityUtils.getSubject();
        if ( !currentUser.isAuthenticated() ) {
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);
            currentUser.login(token);
            try {
                currentUser.login( token );
            } catch ( Exception uae ) {
            }
        }
    }
}
