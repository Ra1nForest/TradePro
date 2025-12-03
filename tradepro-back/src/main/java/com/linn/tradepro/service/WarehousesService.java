package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.entity.Warehouses;

import java.util.List;

/**
 * <p>
 * 仓库表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface WarehousesService extends IService<Warehouses> {
    boolean save(Warehouses warehouses);
    boolean delete(Warehouses warehouses);
    List<Warehouses> searchAll();
}
