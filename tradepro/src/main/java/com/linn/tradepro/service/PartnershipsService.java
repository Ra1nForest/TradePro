package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.entity.Partnerships;

import java.util.List;

/**
 * <p>
 * 正式合作伙伴表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface PartnershipsService extends IService<Partnerships> {
boolean apply(Integer id);
boolean delete(Integer id);
boolean accept(Integer id);
boolean reject(Integer id);
List<Merchants> search(String keyword);
}
