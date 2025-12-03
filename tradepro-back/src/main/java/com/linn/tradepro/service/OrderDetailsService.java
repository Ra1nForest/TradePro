package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.entity.OrderDetails;

import java.util.List;

/**
 * <p>
 * 订单详情表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface OrderDetailsService extends IService<OrderDetails> {
    boolean saveOrderDetails(List<OrderDetails> orderDetails);
    boolean recoverOrderDetails(List<OrderDetails> orderDetails);
    List<OrderDetails> getOrderDetailsByOrderId(Integer orderId);
}
