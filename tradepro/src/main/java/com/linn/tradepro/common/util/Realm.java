package com.linn.tradepro.common.util;

import com.linn.tradepro.entity.Accounts;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.service.AccountsService;
import com.linn.tradepro.service.MerchantsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class Realm extends AuthorizingRealm {

    @Resource
    private AccountsService accountsService;

    @Resource
    private MerchantsService merchantsService;

    //自定义授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        String username = principalCollection.getPrimaryPrincipal().toString();

        Accounts account = accountsService.getByUsername(username);

        String[] role = account.getRole().split(",");

        info.addRoles(new HashSet<>(Arrays.asList(role)));

        return info;
    }

    //自定义认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = authenticationToken.getPrincipal().toString();
        Accounts accounts = accountsService.getByUsername(username);
        if (accounts != null){

            Merchants merchants = merchantsService.getById(accounts.getMerchantId());

            Subject subject = SecurityUtils.getSubject();
            subject.getSession().setAttribute("account", accounts);
            subject.getSession().setAttribute("merchant", merchants);

            return new SimpleAuthenticationInfo(
                    authenticationToken.getPrincipal(),
                    accounts.getPassword(),
                    ByteSource.Util.bytes("linn"),
                    username
            );
        }
        return null;
    }
}
