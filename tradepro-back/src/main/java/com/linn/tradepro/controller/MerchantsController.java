package com.linn.tradepro.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linn.tradepro.common.entity.PageRequest;
import com.linn.tradepro.common.entity.PageResult;
import com.linn.tradepro.common.entity.PasswdModify;
import com.linn.tradepro.common.entity.RegisterRequest;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.service.AccountsService;
import com.linn.tradepro.service.MerchantsService;
import com.linn.tradepro.service.OrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 商户表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "商户表 前端控制器")
@RequestMapping("/merchants")
public class MerchantsController {

    @Resource
    private MerchantsService merchantsService;

    @Resource
    private AccountsService accountsService;

    @Resource
    private OrdersService ordersService;

    @ApiOperation("注册商户")
    @PostMapping("/register")
    public ResponseEntity<?> register(@ApiParam("注册请求体") @RequestBody RegisterRequest registerRequest) {

        if(accountsService.getByUsername(registerRequest.getAccounts().getUsername()) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("用户名已存在");
        }

        if (merchantsService.save(registerRequest.getMerchants(), registerRequest.getAccounts())) {
            return ResponseEntity.ok("注册成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败");
        }
    }

    @RequiresRoles("superadmin")
    @ApiOperation("修改商户二级密码")
    @PutMapping("/modifyPwd")
    public ResponseEntity<?> modifyPwd(@ApiParam("修改密码请求体") @RequestBody PasswdModify passwdModify) {
        if (!Objects.equals(passwdModify.getNewPasswd(), passwdModify.getConfirmPasswd())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("两次密码不一致");
        }
        if (merchantsService.modifyPwd(passwdModify)) {
            return ResponseEntity.ok("修改成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("修改失败");
        }
    }

    @RequiresRoles("superadmin")
    @ApiOperation("修改商户")
    @PutMapping("/modify")
    public ResponseEntity<?> modify(@ApiParam("商户请求体") @RequestBody Merchants merchants) {
        if (merchantsService.modify(merchants)) {
            return ResponseEntity.ok("修改成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("修改失败");
        }
    }

    @RequiresRoles("superadmin")
    @ApiOperation("注销商户")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete() {
        Subject subject = SecurityUtils.getSubject();
        Merchants current = (Merchants) subject.getSession().getAttribute("merchants");

        if (!ordersService.getUnfinishedOrders(current.getId()).isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("您还有未完成的订单");
        }

        if (merchantsService.delete(current.getId())){
            return ResponseEntity.ok("注销成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注销失败");
        }
    }

    @ApiOperation("分页查询商户")
    @PostMapping("/search")
    public ResponseEntity<?> search(@ApiParam("分页查询请求体") @RequestBody PageRequest pageRequest) {
        List<Merchants> merchants = merchantsService.search(pageRequest.getKeyword());
        if (merchants != null){
            IPage<Merchants> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
            page.setRecords(merchants);
            page.setPages(merchants.size() / pageRequest.getPageSize() + 1);
            page.setTotal(merchants.size());
            PageResult<Merchants> result = new PageResult<>(page);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查询失败");
        }
    }

    @RequiresRoles("superadmin")
    @ApiOperation("转让商户")
    @GetMapping("/transfer/{id}")
    public ResponseEntity<?> transfer(@ApiParam("转让目标id") @PathVariable Integer id) {
        if (merchantsService.transfer(id)){
            return ResponseEntity.ok("转让成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("转让失败");
        }
    }

    @ApiOperation("验证二级密码")
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@ApiParam("二级密码") @RequestParam String passwd) {
        if (merchantsService.verify(passwd)){
            return ResponseEntity.ok("验证成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("验证失败");
        }
    }

    @ApiOperation("通过ID查询商户")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchById(@ApiParam("商户ID") @PathVariable Integer id) {
        return ResponseEntity.ok(merchantsService.getById(id));
    }

}
