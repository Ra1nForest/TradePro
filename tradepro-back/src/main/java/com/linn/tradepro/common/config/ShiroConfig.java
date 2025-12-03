package com.linn.tradepro.common.config;

import com.linn.tradepro.common.util.Realm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class ShiroConfig {

    @Resource
    private Realm realm;

    //配置SecurityManager
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();

        matcher.setHashAlgorithmName("sha-256");

        realm.setCredentialsMatcher(matcher);

        defaultWebSecurityManager.setRealm(realm);

        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());

        return defaultWebSecurityManager;
    }

    //配置Shiro过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();

        definition.addPathDefinition("/swagger-ui/**","anon");

        definition.addPathDefinition("/accounts/logout", "logout");

        definition.addPathDefinition("/accounts/login","anon");

        definition.addPathDefinition("/accounts/register","anon");

//        definition.addPathDefinition(("/**"),"authc");
//
//        definition.addPathDefinition(("/**"),"user");

        return definition;
    }

    //cookie属性设置
    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");

        simpleCookie.setPath("/");

        simpleCookie.setHttpOnly(true);

        simpleCookie.setMaxAge(60*60*24*7);

        return simpleCookie;
    }

    //创建Shiro的Cookie管理对象
    @Bean
    public CookieRememberMeManager rememberMeManager(){

        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();

        rememberMeManager.setCookie(rememberMeCookie());

        return rememberMeManager;
    }
}
