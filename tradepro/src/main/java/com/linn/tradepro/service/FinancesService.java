package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.entity.Finances;
import com.linn.tradepro.entity.Orders;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 * 财务表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface FinancesService extends IService<Finances> {
    boolean save(Finances finances);
    boolean saveOrder(Orders orders);
    boolean delete(List<Finances> finances);
    List<Finances> search(String keyword);
    Finances searchById(Integer id);
}
