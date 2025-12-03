package com.linn.tradepro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.entity.TemporaryPartners;
import com.linn.tradepro.mapper.TemporaryPartnersMapper;
import com.linn.tradepro.service.TemporaryPartnersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 临时合作伙伴表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class TemporaryPartnersServiceImpl extends ServiceImpl<TemporaryPartnersMapper, TemporaryPartners> implements TemporaryPartnersService {

    @Resource
    private TemporaryPartnersMapper temporaryPartnersMapper;

    @Override
    public boolean save(TemporaryPartners temporaryPartners) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            if (temporaryPartnersMapper.selectById(temporaryPartners.getId()) == null) {
                temporaryPartners.setMerchantId(merchants.getId());
                return temporaryPartnersMapper.insert(temporaryPartners) > 0;
            } else {
                if (temporaryPartners.getMerchantId().equals(merchants.getId())) {
                    return temporaryPartnersMapper.updateById(temporaryPartners) > 0;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(List<TemporaryPartners> temporaryPartners) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            for (TemporaryPartners tp : temporaryPartners) {
                if (!tp.getMerchantId().equals(merchants.getId())) {
                    return false;
                }
                tp.setStatus("deleted");
            }
            return this.updateBatchById(temporaryPartners);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<TemporaryPartners> search(String keyword) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");

            QueryWrapper<TemporaryPartners> wrapper = new QueryWrapper<>();
            wrapper.or().like("name", keyword)
                    .or().like("contact_name", keyword)
                    .or().like("phone_number", keyword)
                    .or().like("address", keyword);
            wrapper.eq("status", "normal");
            wrapper.eq("merchant_id", merchants.getId());
            return temporaryPartnersMapper.selectList(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
