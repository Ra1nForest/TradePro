package com.linn.tradepro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linn.tradepro.common.util.DateTimeUtils;
import com.linn.tradepro.entity.Finances;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.entity.Orders;
import com.linn.tradepro.mapper.FinancesMapper;
import com.linn.tradepro.service.FinancesService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 财务表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class FinancesServiceImpl extends ServiceImpl<FinancesMapper, Finances> implements FinancesService {

    @Resource
    private FinancesMapper financesMapper;


    @Override
    public boolean save(Finances finances) {
        try {
            if (finances == null || finances.getAmount() == null || finances.getTransactionType() == null) {
                return false;
            }
            if (finances.getOrderId() == null) {
                return false;
            }
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            if (finances.getId() == null) {
                finances.setMerchantId(merchants.getId());
                finances.setCreatedAt(DateTimeUtils.getCurrentDateTime());
                return financesMapper.insert(finances) > 0;
            } else {
                if (finances.getCreatedAt() == null || !Objects.equals(finances.getMerchantId(), merchants.getId())) {
                    return false;
                }
                return financesMapper.updateById(finances) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean saveOrder(Orders orders) {
        try{
            Finances infinances = new Finances();
            infinances.setMerchantId(orders.getMerchantId());
            infinances.setOrderId(orders.getId());
            infinances.setAmount(orders.getAmount());
            infinances.setTransactionType("income");
            infinances.setDescription("订单" + orders.getId() + "收入");
            infinances.setCreatedAt(orders.getCompleteAt());
            Finances outfinances = new Finances();
            outfinances.setMerchantId(orders.getPartnerId());
            outfinances.setOrderId(orders.getId());
            outfinances.setAmount(orders.getAmount());
            outfinances.setTransactionType("expense");
            outfinances.setDescription("订单" + orders.getId() + "支出");
            outfinances.setCreatedAt(orders.getCompleteAt());
            return financesMapper.insert(infinances) > 0 && financesMapper.insert(outfinances) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delete(List<Finances> finances) {
        if (finances == null || finances.size() == 0) {
            return false;
        }
        Subject subject = SecurityUtils.getSubject();
        Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
        for (Finances finance : finances) {
            if (finance.getOrderId() != null || !Objects.equals(finance.getMerchantId(), merchants.getId())) {
                return false;
            }
        }
        return financesMapper.deleteBatchIds(finances) > 0;
    }

    @Override
    public List<Finances> search(String keyword) {
        Subject subject = SecurityUtils.getSubject();
        Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
        QueryWrapper<Finances> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchants.getId());
        wrapper.or().like("description", keyword);
        return null;
    }

    @Override
    public Finances searchById(Integer id) {
        Subject subject = SecurityUtils.getSubject();
        Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
        QueryWrapper<Finances> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id", merchants.getId());
        return financesMapper.selectOne(wrapper);
    }


}
