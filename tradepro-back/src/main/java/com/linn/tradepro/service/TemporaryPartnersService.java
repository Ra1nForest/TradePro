package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.entity.TemporaryPartners;

import java.util.List;

/**
 * <p>
 * 临时合作伙伴表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface TemporaryPartnersService extends IService<TemporaryPartners> {
    boolean save(TemporaryPartners temporaryPartners);
    boolean delete(List<TemporaryPartners> temporaryPartners);
    List<TemporaryPartners> search(String keyword);
}
