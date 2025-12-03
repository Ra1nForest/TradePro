package com.linn.tradepro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.entity.Partnerships;
import com.linn.tradepro.mapper.PartnershipsMapper;
import com.linn.tradepro.service.MerchantsService;
import com.linn.tradepro.service.PartnershipsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 正式合作伙伴表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class PartnershipsServiceImpl extends ServiceImpl<PartnershipsMapper, Partnerships> implements PartnershipsService {

    @Resource
    private PartnershipsMapper partnershipsMapper;

    @Resource
    private MerchantsService merchantsService;

    @Override
    public boolean apply(Integer id) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            if (id == null || merchants.getId() == null || id.equals(merchants.getId())) {
                return false;
            }
            QueryWrapper<Partnerships> wrapper = new QueryWrapper<>();
            wrapper.and(i -> i.eq("merchants_id", merchants.getId()).eq("partner_id", id))
                    .or(i -> i.eq("merchants_id", id).eq("partner_id", merchants.getId()));
            Partnerships partnerships = partnershipsMapper.selectOne(wrapper);
            if (partnerships == null){
                partnerships = new Partnerships();
                partnerships.setMerchantId(merchants.getId());
                partnerships.setPartnershipId(id);
                partnerships.setStatus("pending");
                return partnershipsMapper.insert(partnerships) > 0;
            }

            if (partnerships.getStatus().equals("deleted")){
                partnerships.setMerchantId(merchants.getId());
                partnerships.setPartnershipId(id);
                partnerships.setStatus("pending");
                return partnershipsMapper.insert(partnerships) > 0;
            }

            if (partnerships.getStatus().equals("pending")){
                return false;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            Partnerships partnerships = partnershipsMapper.selectById(id);
            if (partnerships == null
                    || (!Objects.equals(partnerships.getPartnershipId(), merchants.getId())
                    && !Objects.equals(partnerships.getMerchantId(), merchants.getId()))){
                return false;
            }
            partnerships.setStatus("deleted");
            return partnershipsMapper.updateById(partnerships) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean accept(Integer id) {
        try{
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            Partnerships partnerships = partnershipsMapper.selectById(id);
            if (partnerships == null
                    || (!Objects.equals(partnerships.getPartnershipId(), merchants.getId())
                    && !Objects.equals(partnerships.getMerchantId(), merchants.getId()))){
                return false;
            }
            partnerships.setStatus("normal");
            return partnershipsMapper.updateById(partnerships) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean reject(Integer id) {
        try{
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            Partnerships partnerships = partnershipsMapper.selectById(id);
            if (partnerships == null
                    || (!Objects.equals(partnerships.getPartnershipId(), merchants.getId())
                    && !Objects.equals(partnerships.getMerchantId(), merchants.getId()))){
                return false;
            }
            return partnershipsMapper.deleteById(partnerships) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Merchants> search(String keyword) {
        try{
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            QueryWrapper<Partnerships> wrapper = new QueryWrapper<>();
            if (keyword.equals("pending")) {
                wrapper.eq("merchants_id", merchants.getId()).eq("status", "pending");
                List<Partnerships> partnerships = partnershipsMapper.selectList(wrapper);
                List<Integer> ids = partnerships.stream().map(Partnerships::getPartnershipId).toList();
                return merchantsService.listByIds(ids);
            }
            if (keyword.equals("accepting")){
                wrapper.eq("partnership_id", merchants.getId()).eq("status", "pending");
                List<Partnerships> partnerships = partnershipsMapper.selectList(wrapper);
                List<Integer> ids = partnerships.stream().map(Partnerships::getPartnershipId).toList();
                return merchantsService.listByIds(ids);
            }
            if (keyword.equals("normal")){
                wrapper.eq("merchants_id", merchants.getId()).eq("status", keyword)
                        .or().eq("partnership_id", merchants.getId()).eq("status", keyword);
                List<Partnerships> partnerships = partnershipsMapper.selectList(wrapper);
                List<Integer> ids = partnerships.stream().map(Partnerships::getPartnershipId).toList();
                return merchantsService.listByIds(ids);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
