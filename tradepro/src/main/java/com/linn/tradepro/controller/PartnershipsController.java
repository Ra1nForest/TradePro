package com.linn.tradepro.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linn.tradepro.common.entity.PageRequest;
import com.linn.tradepro.common.entity.PageResult;
import com.linn.tradepro.entity.Merchants;
import com.linn.tradepro.service.PartnershipsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 正式合作伙伴表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "正式合作伙伴表 前端控制器")
@RequestMapping("/partnerships")
public class PartnershipsController {

    @Resource
    private PartnershipsService partnershipsService;

    @RequiresRoles(value = {"superadmin","admin","partner"},logical = Logical.OR)
    @ApiOperation("申请添加正式合作伙伴")
    @GetMapping("/apply/{id}")
    public ResponseEntity<?> apply(@ApiParam("对方商户ID") @PathVariable Integer id)  {
        if (partnershipsService.apply(id)){
            return ResponseEntity.ok("申请成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("申请失败");
        }
    }


    @RequiresRoles(value = {"superadmin","admin","partner"},logical = Logical.OR)
    @ApiOperation("删除正式合作伙伴")
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<?> delete(@ApiParam("合作关系ID") @PathVariable Integer id)  {
        if (partnershipsService.delete(id)){
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("删除失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","partner"},logical = Logical.OR)
    @ApiOperation("接受正式合作伙伴申请")
    @GetMapping ("/accept/{id}")
    public ResponseEntity<?> accept(@ApiParam("合作关系ID") @PathVariable Integer id)  {
        if (partnershipsService.accept(id)){
            return ResponseEntity.ok("接受成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("接受失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","partner"},logical = Logical.OR)
    @ApiOperation("拒绝正式合作伙伴申请")
    @GetMapping ("/reject/{id}")
    public ResponseEntity<?> reject(@ApiParam("合作关系ID") @PathVariable Integer id)  {
        if (partnershipsService.reject(id)){
            return ResponseEntity.ok("拒绝成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("拒绝失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","partner"},logical = Logical.OR)
    @ApiOperation("分页查询正式合作伙伴")
    @GetMapping ("/search")
    public ResponseEntity<?> search(@ApiParam("分页查询请求体") @RequestBody PageRequest pageRequest)  {
        List<Merchants> partnerships = partnershipsService.search(pageRequest.getKeyword());
        if (partnerships != null){
            IPage<Merchants> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
            page.setRecords(partnerships);
            page.setTotal(partnerships.size());
            page.setPages(partnerships.size()/pageRequest.getPageSize()+1);
            PageResult<Merchants> result = new PageResult<>(page);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查询失败");
        }
    }
}
