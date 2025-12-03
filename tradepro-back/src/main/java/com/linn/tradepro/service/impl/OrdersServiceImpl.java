package com.linn.tradepro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linn.tradepro.common.util.DateTimeUtils;
import com.linn.tradepro.entity.*;
import com.linn.tradepro.mapper.OrdersMapper;
import com.linn.tradepro.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Resource
    private OrdersMapper orderMapper;

    @Resource
    private OrderDetailsService orderDetailsService;

    @Resource
    private ProductsService productsService;

    @Override
    public boolean save(Orders order, List<OrderDetails> details) {
        try {
            if (this.getById(order.getId()).equals(order)) {
                return false;
            }
            Subject subject = SecurityUtils.getSubject();
            Accounts account = (Accounts) subject.getSession().getAttribute("account");
            Merchants merchant = (Merchants) subject.getSession().getAttribute("merchant");
            if (order.getId() == null) {
                order.setMerchantId(merchant.getId());
                order.setCreatedAt(DateTimeUtils.getCurrentDateTime());
            } else {
                if (!Objects.equals(order.getMerchantId(), merchant.getId())) {
                    return false;
                }
            }
            order.setMerchantAccountId(account.getId());
            BigDecimal amount = new BigDecimal(0);
            for (OrderDetails detail : details) {
                Products product = productsService.getById(detail.getProductId());
                if (detail.getDiscount() != null) {
                    amount = amount.add(product.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())).multiply(BigDecimal.valueOf(detail.getDiscount() / 100)));
                } else {
                    amount = amount.add(product.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
                }
            }
            if (order.getOrderDiscount() != null) {
                amount = amount.multiply(BigDecimal.valueOf(order.getOrderDiscount() / 100));
            }
            order.setAmount(amount);
            return orderMapper.updateById(order) > 0 && orderDetailsService.saveOrderDetails(details);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean accept(Integer id, List<Products> products) {
        try{
            Subject subject = SecurityUtils.getSubject();
            Merchants merchant = (Merchants) subject.getSession().getAttribute("merchant");
            Accounts account = (Accounts) subject.getSession().getAttribute("account");
            Orders order = this.getById(id);
            if (!Objects.equals(order.getPartnerId(), merchant.getId())){
                return false;
            }
            order.setPartnerAccountId(account.getId());
            order.setCompleteAt(DateTimeUtils.getCurrentDateTime());
            List<OrderDetails> details = orderDetailsService.getOrderDetailsByOrderId(order.getId());
            if (details.size() != products.size()) {
                return false;
            }
            for (int i = 0 ; i < details.size() ; i++) {
                products.get(i).setQuantity(products.get(i).getQuantity() + details.get(i).getQuantity());
            }
            return orderMapper.updateById(order) > 0 && productsService.updateBatchById(products);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean reject(Integer id) {
        try{
            Subject subject = SecurityUtils.getSubject();
            Merchants merchant = (Merchants) subject.getSession().getAttribute("merchant");
            Accounts account = (Accounts) subject.getSession().getAttribute("account");
            Orders order = this.getById(id);
            if (!Objects.equals(order.getPartnerId(), merchant.getId())){
                return false;
            }
            order.setPartnerAccountId(account.getId());
            List<OrderDetails> details = orderDetailsService.getOrderDetailsByOrderId(order.getId());
            if (details != null) {
                orderDetailsService.recoverOrderDetails(details);
            }
            return orderMapper.updateById(order) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String judge(Integer id) {
        try{
            Orders order = this.getById(id);
            if (order.getPartnerId() == null || order.getTemporaryPartnersId() != null) {
                return null;
            }
            if (order.getPartnerAccountId() == null && order.getCompleteAt() == null) {
                return "pending";
            } else if (order.getPartnerAccountId() != null && order.getCompleteAt() == null) {
                return "rejected";
            } else if (order.getPartnerAccountId() != null){
                return "accepted";
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Orders> search(String keyword) {
        try {
            QueryWrapper<Orders> wrapper = new QueryWrapper<>();
            Subject subject = SecurityUtils.getSubject();
            Merchants merchant = (Merchants) subject.getSession().getAttribute("merchant");
            wrapper.eq("merchant_id", merchant.getId());
            wrapper.and(i -> i.like("details", keyword))
                    .or().exists("SELECT 1 FROM merchants WHERE orders.partner_id = merchants.id " +
                            "AND (merchants.name LIKE {0} OR merchants.address LIKE {0})", "%" + keyword + "%")
                    .or().exists("SELECT 1 FROM accounts WHERE orders.merchant_account_id = accounts.id " +
                            "AND accounts.username LIKE {0}", "%" + keyword + "%")
                    .or().exists("SELECT 1 FROM order_details " +
                            "LEFT JOIN products ON order_details.product_id = products.id " +
                            "WHERE orders.id = order_details.id AND products.name LIKE {0}", "%" + keyword + "%");
            return orderMapper.selectList(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Orders> getUnfinishedOrders(Integer merchantId) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("merchant_id",merchantId)
                        .or().eq("partner_id",merchantId);
        wrapper.eq("complete_at",null);
        return null;
    }
}
