package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.common.entity.PasswdModify;
import com.linn.tradepro.entity.Accounts;
import com.linn.tradepro.entity.Merchants;

import java.util.List;

/**
 * <p>
 * 商户表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface MerchantsService extends IService<Merchants> {
    boolean save(Merchants merchants,Accounts accounts);
    boolean modifyPwd(PasswdModify passwdModify);
    boolean modify(Merchants merchants);
    boolean delete(Integer id);
    List<Merchants> search(String keyword);
    boolean transfer(Integer id);
    boolean verify(String passwd);
}
