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
import com.linn.tradepro.service.ProductsService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements ProductsService {

    @Resource
    private ProductsMapper productsMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private OrderDetailsService orderDetailsService;

    @Override
    public boolean save(Products products) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            if (products.getId() == null) {
                products.setMerchantId(merchants.getId());
                return productsMapper.insert(products) > 0;
            } else {
                if (!Objects.equals(products.getMerchantId(), merchants.getId())) {
                    return false;
                }
                return productsMapper.updateById(products) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Products products) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            if (!Objects.equals(products.getMerchantId(), merchants.getId())){
                return false;
            }
            QueryWrapper<Orders> wrapper = new QueryWrapper<>();
            wrapper.eq("merchant_id", merchants.getId())
                    .isNull("partner_id")
                    .isNull("completed_at");
            List<Orders> ordersList = ordersMapper.selectList(wrapper);
            for (Orders orders : ordersList) {
                List<OrderDetails> orderDetails = orderDetailsService.getOrderDetailsByOrderId(orders.getId());
                for (OrderDetails orderDetail : orderDetails) {
                    if (orderDetail.getProductId().equals(products.getId())) {
                        return false;
                    }
                }
            }
            products.setQuantity(-1);
            return productsMapper.updateById(products) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Products> searchByCategory(String keyword, Integer categoryId) {
        try{
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            QueryWrapper<Products> wrapper = new QueryWrapper<>();
            wrapper.eq("merchant_id", merchants.getId());
            wrapper.eq("category_id", categoryId);
            wrapper.or().like("name", keyword)
                    .or().like("description", keyword);
            List<Products> productsList = productsMapper.selectList(wrapper);
            productsList.removeIf(products -> products.getQuantity() <= 0);
            return productsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Products> searchByArea(String keyword, Integer areaId) {
        try{
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            QueryWrapper<Products> wrapper = new QueryWrapper<>();
            wrapper.eq("merchant_id", merchants.getId());
            wrapper.eq("warehouse_location_id", areaId);
            wrapper.or().like("name", keyword)
                    .or().like("description", keyword);
            List<Products> productsList = productsMapper.selectList(wrapper);
            productsList.removeIf(products -> products.getQuantity() <= 0);
            return productsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Products> getByWarehouseLocationId(Integer id) {
        try{
            Subject subject = SecurityUtils.getSubject();
            Merchants merchants = (Merchants) subject.getSession().getAttribute("merchants");
            QueryWrapper<Products> wrapper = new QueryWrapper<>();
            wrapper.eq("warehouse_location_id", id);
            wrapper.eq("merchant_id", merchants.getId());
            return productsMapper.selectList(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
