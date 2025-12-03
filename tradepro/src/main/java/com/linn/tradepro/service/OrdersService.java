package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.entity.OrderDetails;
import com.linn.tradepro.entity.Orders;
import com.linn.tradepro.entity.Products;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface OrdersService extends IService<Orders> {
    boolean save(Orders order, List<OrderDetails> details);
    boolean accept(Integer id, List<Products> products);
    boolean reject(Integer id);
    String judge(Integer id);
    List<Orders> search(String keyword);
    List<Orders> getUnfinishedOrders(Integer merchantId);

}
