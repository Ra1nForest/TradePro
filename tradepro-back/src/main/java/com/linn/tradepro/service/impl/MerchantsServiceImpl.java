package com.linn.tradepro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linn.tradepro.common.entity.PasswdModify;
import com.linn.tradepro.common.util.DateTimeUtils;
import com.linn.tradepro.common.util.PasswordHash;
import com.linn.tradepro.entity.Accounts;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.mapper.AccountsMapper;
import com.linn.tradepro.mapper.MerchantsMapper;
import com.linn.tradepro.service.MerchantsService;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 商户表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class MerchantsServiceImpl extends ServiceImpl<MerchantsMapper, Merchants> implements MerchantsService {

    @Resource
    MerchantsMapper merchantsMapper;

    @Resource
    AccountsMapper accountsMapper;

    @Override
    @Transactional
    public boolean save(Merchants merchants, Accounts accounts) {
        try {
            merchants.setCreatedAt(DateTimeUtils.getCurrentDateTime());
            merchants.setPassword(PasswordHash.hash(merchants.getPassword()));
            merchantsMapper.insert(merchants);
            accounts.setMerchantId(merchants.getId());
            accounts.setRole("superadmin");
            accounts.setPassword(PasswordHash.hash(accounts.getPassword()));
            accountsMapper.insert(accounts);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modifyPwd(PasswdModify passwdModify) {
        try {
            Subject subject = org.apache.shiro.SecurityUtils.getSubject();
            Merchants current = (Merchants) subject.getSession().getAttribute("merchants");
            if (!Objects.equals(passwdModify.getId(), current.getId())) {
                return false;
            }
            if (!PasswordHash.hash(passwdModify.getOldPasswd()).equals(current.getPassword())) {
                return false;
            }
            current.setPassword(PasswordHash.hash(passwdModify.getNewPasswd()));
            return merchantsMapper.updateById(current) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modify(Merchants merchants) {
        try{
            Subject subject = org.apache.shiro.SecurityUtils.getSubject();
            Merchants current = (Merchants) subject.getSession().getAttribute("merchants");
            if (!Objects.equals(merchants.getId(), current.getId())) {
                return false;
            }
            merchants.setPassword(current.getPassword());
            merchants.setCreatedAt(current.getCreatedAt());
            return merchantsMapper.updateById(merchants) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        try {
            QueryWrapper<Accounts> wrapper = new QueryWrapper<>();
            wrapper.eq("merchant_id", id);
            Merchants merchants = new Merchants();
            merchants.setId(id);
            merchants.setName("该商户已注销");
            return accountsMapper.delete(wrapper)!=0 && merchantsMapper.updateById(merchants)!=0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Merchants> search(String keyword) {
        try{
            QueryWrapper<Merchants> wrapper = new QueryWrapper<>();
            wrapper.like("name", keyword)
                    .or().like("contact_person",keyword)
                    .or().like("phone",keyword)
                    .or().like("email",keyword)
                    .or().like("address",keyword);
            List<Merchants> merchants = merchantsMapper.selectList(wrapper);

            Subject subject = org.apache.shiro.SecurityUtils.getSubject();
            Merchants current = (Merchants) subject.getSession().getAttribute("merchants");
            merchants.remove(current);
            merchants.removeIf(merchant -> merchant.getName().equals("该商户已注销"));
            return merchants;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public boolean transfer(Integer id) {
        try {
            Accounts accounts = accountsMapper.selectById(id);
            Subject subject = org.apache.shiro.SecurityUtils.getSubject();
            Merchants current = (Merchants) subject.getSession().getAttribute("merchants");
            Accounts currentAccount = (Accounts) subject.getSession().getAttribute("accounts");
            if (!Objects.equals(accounts.getMerchantId(), currentAccount.getMerchantId())) {
                return false;
            }
            currentAccount.setRole("admin");
            accounts.setRole("superadmin");
            current.setContactPerson(accounts.getName());
            current.setPhone(accounts.getPhone());
            return accountsMapper.updateById(currentAccount) >0
                    && accountsMapper.updateById(accounts) > 0
                    && merchantsMapper.updateById(current) >0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean verify(String passwd) {
        try {
            Subject subject = org.apache.shiro.SecurityUtils.getSubject();
            Merchants current = (Merchants) subject.getSession().getAttribute("merchants");
            return PasswordHash.hash(passwd).equals(current.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
