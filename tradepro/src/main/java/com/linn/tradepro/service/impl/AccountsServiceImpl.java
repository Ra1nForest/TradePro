package com.linn.tradepro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linn.tradepro.common.entity.PasswdModify;
import com.linn.tradepro.common.util.PasswordHash;
import com.linn.tradepro.entity.Accounts;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.mapper.AccountsMapper;
import com.linn.tradepro.service.AccountsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 子账号表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class AccountsServiceImpl extends ServiceImpl<AccountsMapper, Accounts> implements AccountsService {

    @Resource
    private AccountsMapper accountsMapper;

    @Override
    public Accounts getByUsername(String username) {
        try {
            QueryWrapper<Accounts> wrapper = new QueryWrapper<>();
            wrapper.eq("username", username);
            return accountsMapper.selectOne(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(Accounts account) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Merchants merchant = (Merchants) subject.getSession().getAttribute("merchant");
            account.setMerchantId(merchant.getId());
            account.setPassword(PasswordHash.hash(account.getPassword()));
            if (account.getId() == null) {
                return accountsMapper.insert(account) > 0;
            } else {
                Accounts oldAccount = accountsMapper.selectById(account.getId());
                if (account.getPassword() == null){
                    account.setPassword(oldAccount.getPassword());
                }
                return accountsMapper.updateById(account) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(List<Accounts> accounts) {
        try {
            List<Integer> accountIds = new ArrayList<>();
            for (Accounts account : accounts) {
                accountIds.add(account.getId());
            }
            QueryWrapper<Accounts> wrapper = new QueryWrapper<>();
            wrapper.in("id", accountIds);
            int res = accountsMapper.delete(wrapper);
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Accounts> search(String keyword) {
        try {
            QueryWrapper<Accounts> wrapper = new QueryWrapper<>();
            Subject subject = SecurityUtils.getSubject();
            Merchants merchant = (Merchants) subject.getSession().getAttribute("merchant");
            wrapper.eq("merchant_id", merchant.getId());

            if (StringUtils.isNotBlank(keyword)) {
                wrapper.and(qw -> qw.like("username", keyword)
                        .or().like("name", keyword)
                        .or().like("phone", keyword)
                        .or().like("role", keyword)
                );
            }

            return accountsMapper.selectList(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean modifyPasswd(PasswdModify passwdModify) {
        try {
            Accounts account = accountsMapper.selectById(passwdModify.getId());
            Subject subject = SecurityUtils.getSubject();
            Merchants merchant = (Merchants) subject.getSession().getAttribute("merchant");
            if (account == null) {
                return false;
            }
            if (!Objects.equals(merchant.getId(), account.getMerchantId())) {
                return false;
            }
            if (!PasswordHash.hash(passwdModify.getOldPasswd()).equals(account.getPassword())) {
                return false;
            }
            account.setPassword(PasswordHash.hash(passwdModify.getNewPasswd()));
            return accountsMapper.updateById(account) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Accounts getById(Integer id) {
        try {
            Accounts account = accountsMapper.selectById(id);
            Subject subject = SecurityUtils.getSubject();
            Merchants merchant = (Merchants) subject.getSession().getAttribute("merchant");
            if (account != null && Objects.equals(merchant.getId(), account.getMerchantId())) {
                return account;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
