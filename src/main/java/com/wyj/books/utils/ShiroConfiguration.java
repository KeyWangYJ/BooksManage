package com.wyj.books.utils;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfiguration {

    //@Qualifier代表spring里面的

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);

        Map<String, Filter> filtersMap = new LinkedHashMap<>();

        filtersMap.put("roleOrFilter",new RoleOrFilter());//可以配置RoleOrFilter的Bean
        bean.setFilters(filtersMap);
        bean.setLoginUrl("/login");//提供登录到url
        bean.setSuccessUrl("/admin");//提供登陆成功的url
        bean.setUnauthorizedUrl("/login");

        /*
         * 可以看DefaultFilter,这是一个枚举类，定义了很多的拦截器authc,anon等分别有对应的拦截器
         * */
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/admin", "authc");//代表着前面的url路径，用后面指定的拦截器进行拦截
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/index", "anon");
        filterChainDefinitionMap.put("/bookDetail", "anon");
        filterChainDefinitionMap.put("/adminLogin", "anon");
        filterChainDefinitionMap.put("/userLogin", "anon");
        filterChainDefinitionMap.put("/phoneLogin", "anon");
        filterChainDefinitionMap.put("/editPassword", "anon");
        filterChainDefinitionMap.put("/getBooks", "anon");
        filterChainDefinitionMap.put("/getClassification", "anon");
        filterChainDefinitionMap.put("/selectBookById", "anon");
        filterChainDefinitionMap.put("/selectBooksLike", "anon");
        filterChainDefinitionMap.put("/deleteLend", "anon");
        filterChainDefinitionMap.put("/getUserLends", "anon");
//        filterChainDefinitionMap.put("/superAdmin/updateAdminRole", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/registerUser", "anon");
        filterChainDefinitionMap.put("/sendSMS", "anon");
        filterChainDefinitionMap.put("/checkPhone", "anon");
        filterChainDefinitionMap.put("/checkIdCode", "anon");
        filterChainDefinitionMap.put("/checkEmail", "anon");

        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/easyUI/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/UserAdmin/**", "roleOrFilter[userAdmin,superAdmin]");
        filterChainDefinitionMap.put("/UserAdmin/**", "perms[manageUser]");
        filterChainDefinitionMap.put("/BookAdmin/**", "roleOrFilter[booksAdmin,superAdmin]");
        filterChainDefinitionMap.put("/BookAdmin/**", "perms[manageBook]");
        filterChainDefinitionMap.put("/superAdmin/**","roleOrFilter[superAdmin]");
        filterChainDefinitionMap.put("/superAdmin/**", "perms[manageAdmin]");
        filterChainDefinitionMap.put("/**", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);//设置一个拦截器链

        return bean;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     *
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    /*
     * 注入一个securityManager
     * 原本以前我们是可以通过ini配置文件完成的，代码如下：
     *  1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
     * */
    @Bean("securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setAuthenticator(modularRealmAuthenticator());
        List<Realm> realms = new ArrayList<>();
        //添加多个Realm
        realms.add(adminRealm());
        realms.add(userRealm());
        realms.add(phoneRealm());
        securityManager.setRealms(realms);

        return securityManager;
    }

    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        //自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    //自定义的Realm
    @Bean("adminRealm")
    public AdminRealm adminRealm() {
        AdminRealm adminRealm = new AdminRealm();
        adminRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return adminRealm;
    }

    @Bean("userRealm")
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    @Bean("phoneRealm")
    public PhoneRealm phoneRealm() {
        PhoneRealm phoneRealm = new PhoneRealm();
        return phoneRealm;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

//    /*
//     * Realm在验证用户身份的时候，要进行密码匹配
//     * 最简单的情况就是明文直接匹配，然后就是加密匹配，这里的匹配工作则就是交给CredentialsMatcher来完成
//     * 支持任意数量的方案，包括纯文本比较、散列比较和其他方法。除非该方法重写，否则默认值为
//     * */
//    @Bean("credentialMatcher")
//    public CredentialMatcher credentialMatcher() {
//        return new CredentialMatcher();
//    }


    /*
     * 以下AuthorizationAttributeSourceAdvisor,DefaultAdvisorAutoProxyCreator两个类是为了支持shiro注解
     * */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
