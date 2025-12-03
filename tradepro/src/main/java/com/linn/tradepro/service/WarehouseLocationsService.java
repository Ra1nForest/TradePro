package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.entity.WarehouseLocations;

import java.util.List;

/**
 * <p>
 * 仓库位置表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface WarehouseLocationsService extends IService<WarehouseLocations> {
    List<WarehouseLocations> searchByWarehouseId(Integer id);
}
