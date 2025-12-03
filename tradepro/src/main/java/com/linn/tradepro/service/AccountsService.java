package com.linn.tradepro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linn.tradepro.common.entity.PasswdModify;
import com.linn.tradepro.entity.Accounts;

import java.util.List;

/**
 * <p>
 * 子账号表 服务类
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
public interface AccountsService extends IService<Accounts> {
    Accounts getByUsername(String username);
    boolean save(Accounts account);
    boolean delete(List<Accounts> accounts);
    List<Accounts> search(String keyword);
    boolean modifyPasswd(PasswdModify passwdModify);
    Accounts getById(Integer id);
}
