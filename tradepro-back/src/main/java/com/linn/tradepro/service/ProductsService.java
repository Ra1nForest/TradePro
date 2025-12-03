package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.entity.Products;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface ProductsService extends IService<Products> {
    boolean save(Products products);
    boolean delete(Products products);
    List<Products> searchByCategory(String keyword, Integer categoryId);
    List<Products> searchByArea(String keyword, Integer areaId);
    List<Products> getByWarehouseLocationId(Integer id);
}
