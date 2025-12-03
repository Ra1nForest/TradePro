package com.linn.tradepro.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linn.tradepro.common.entity.LoginRequest;
import com.linn.tradepro.common.entity.PageRequest;
import com.linn.tradepro.common.entity.PageResult;
import com.linn.tradepro.common.entity.PasswdModify;
import com.linn.tradepro.entity.Accounts;
import com.linn.tradepro.service.AccountsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
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
 * 子账号表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "子账号表 前端控制器")
@RequestMapping("/accounts")
public class AccountsController {

    @Resource
    private AccountsService accountsService;

    @RequiresRoles("superadmin")
    @ApiOperation("新增子账户")
    @PostMapping("/add")
    public ResponseEntity<?> add(@ApiParam("子账号请求体") @RequestBody Accounts account)  {
        if (accountsService.getByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名已存在");
        }
        if (accountsService.save(account)){
            return ResponseEntity.ok("添加成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("添加失败");
        }
    }

    @RequiresRoles("superadmin")
    @ApiOperation("删除子账户")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@ApiParam("子账号请求体") @RequestBody List<Accounts> accounts)  {
        for (Accounts account : accounts) {
            if (account.getRole().equals("superadmin")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("不能删除超级管理员");
            }
        }
        if (accountsService.delete(accounts)){
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("删除失败");
        }
    }

    @RequiresRoles("superadmin")
    @ApiOperation("修改子账户")
    @PutMapping ("/modify")
    public ResponseEntity<?> modify(@ApiParam("子账号请求体") @RequestBody Accounts account)  {
        if (accountsService.getByUsername(account.getUsername()) != null &&
                !accountsService.getByUsername(account.getUsername()).getId().equals(account.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("用户名已存在");
        }
        if (accountsService.save(account)){
            return ResponseEntity.ok("修改成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("修改失败");
        }
    }

    @RequiresRoles("superadmin")
    @ApiOperation("修改子账户密码")
    @PutMapping ("/modifyPwd")
    public ResponseEntity<?> modifyPwd(@ApiParam("密码更改请求体") @RequestBody PasswdModify passwdModify)  {
        if (!Objects.equals(passwdModify.getNewPasswd(), passwdModify.getConfirmPasswd())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("两次密码不一致");
        }
        if (accountsService.modifyPasswd(passwdModify)){
            return ResponseEntity.ok("修改成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("修改失败");
        }
    }

    @ApiOperation("分页查询子账户")
    @PostMapping("/search")
    public ResponseEntity<?> search(@ApiParam("分页查询请求体") @RequestBody PageRequest pageRequest)  {
        List<Accounts> accounts = accountsService.search(pageRequest.getKeyword());
        if (accounts != null){
            IPage<Accounts> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
            page.setRecords(accounts);
            page.setPages(accounts.size() / pageRequest.getPageSize() + 1);
            page.setTotal(accounts.size());
            PageResult<Accounts> pageResult = new PageResult<>(page);
            return ResponseEntity.ok(pageResult);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查询失败");
        }
    }

    @ApiOperation("通过ID查询子账户")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> search(@ApiParam("账户ID") @PathVariable Integer id)  {
        Accounts account = accountsService.getById(id);
        if (account != null){
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查询失败");
        }
    }

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseEntity<?> login(@ApiParam("登录请求体") @RequestBody LoginRequest loginRequest) {
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(loginRequest.getUsername(), loginRequest.getPassword(), loginRequest.getRememberMe());
        try {
            subject.login(token);

            return ResponseEntity.ok("登录成功");
        } catch (UnknownAccountException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("用户不存在");
        } catch (IncorrectCredentialsException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密码错误");
        }
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        System.out.println("登出成功");
        return ResponseEntity.ok("登出成功");
    }
}
