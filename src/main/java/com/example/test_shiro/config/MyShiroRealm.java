package com.example.test_shiro.config;

import com.example.test_shiro.entity.Permission;
import com.example.test_shiro.entity.Role;
import com.example.test_shiro.entity.User;
import com.example.test_shiro.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName MyShiroRealm
 * @Description TODO
 * @Author pyt
 * @Date 2019/5/22 10:07
 * @Version
 */
//实现AuthorizingRealm接口用户验证
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private LoginService loginService;

    //角色权限和对应权限添加
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登陆用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //查询用户名
        User user = loginService.findByUsername(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission :
                    role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在post请求时先进行认证，然后再请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        System.out.println("用户名：" + name);
        User user = loginService.findByUsername(name);
        if (user == null) {
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthorizationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), getName());
            return simpleAuthenticationInfo;
        }
    }
//设置用户加密格式
//    @Override
//    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
//        super.setCredentialsMatcher(credentialsMatcher);
//    }
}
