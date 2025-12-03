package com.linn.tradepro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.entity.OrderDetails;
import com.linn.tradepro.entity.Orders;
import com.linn.tradepro.entity.Products;
import com.linn.tradepro.mapper.OrderDetailsMapper;
import com.linn.tradepro.mapper.OrdersMapper;
import com.linn.tradepro.mapper.ProductsMapper;
import com.linn.tradepro.service.OrderDetailsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class OrderDetailsServiceImpl extends ServiceImpl<OrderDetailsMapper, OrderDetails> implements OrderDetailsService {

    @Resource
    private OrderDetailsMapper orderDetailsMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private ProductsMapper productsMapper;

    @Transactional
    @Override
    public boolean saveOrderDetails(List<OrderDetails> orderDetails) {
        try {
            if (orderDetails.isEmpty()) {
                return false;
            }
            QueryWrapper<OrderDetails> wrapper = new QueryWrapper<>();
            wrapper.eq("order_id", orderDetails.get(0).getOrderId());
            List<OrderDetails> oldOrderDetails = orderDetailsMapper.selectList(wrapper);
            for (OrderDetails oldOrderDetail : oldOrderDetails) {
                Products product = productsMapper.selectById(oldOrderDetail.getProductId());
                product.setQuantity(product.getQuantity() + oldOrderDetail.getQuantity());
                productsMapper.updateById(product);
            }
            orderDetailsMapper.delete(wrapper);
            for (OrderDetails orderDetail : orderDetails) {
                if (productsMapper.selectById(orderDetail.getProductId()) == null) {
                    return false;
                }
                if (productsMapper.selectById(orderDetail.getProductId()).getQuantity() - orderDetail.getQuantity() < 0) {
                    return false;
                } else {
                    Products product = productsMapper.selectById(orderDetail.getProductId());
                    product.setQuantity(product.getQuantity() - orderDetail.getQuantity());
                    productsMapper.updateById(product);
                }
            }
            return this.saveBatch(orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public boolean recoverOrderDetails(List<OrderDetails> orderDetails) {
        try {
            if (orderDetails.isEmpty()) {
                return false;
            }
            for (OrderDetails orderDetail : orderDetails) {
                if (productsMapper.selectById(orderDetail.getProductId()) == null) {
                    return false;
                }
                Products product = productsMapper.selectById(orderDetail.getProductId());
                product.setQuantity(product.getQuantity() + orderDetail.getQuantity());
                productsMapper.updateById(product);
            }
            return this.saveBatch(orderDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OrderDetails> getOrderDetailsByOrderId(Integer orderId) {
        try{
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            QueryWrapper<Orders> ordersWrapper = new QueryWrapper<>();
            ordersWrapper.eq("id", orderId);
            ordersWrapper.eq("merchants_id", merchants.getId());
            if (ordersMapper.selectOne(ordersWrapper) == null) {
                return null;
            }
            QueryWrapper<OrderDetails> orderDetailsWrapper = new QueryWrapper<>();
            orderDetailsWrapper.eq("order_id", orderId);
            return orderDetailsMapper.selectList(orderDetailsWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
