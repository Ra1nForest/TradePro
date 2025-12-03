package com.linn.tradepro.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linn.tradepro.common.entity.PageRequest;
import com.linn.tradepro.common.entity.PageResult;
import com.linn.tradepro.entity.TemporaryPartners;
import com.linn.tradepro.service.TemporaryPartnersService;
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
 * 临时合作伙伴表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "临时合作伙伴表 前端控制器")
@RequestMapping("/temporaryPartners")
public class TemporaryPartnersController {

    @Resource
    private TemporaryPartnersService temporaryPartnersService;

    @RequiresRoles(value = {"superadmin","admin","user"},logical = Logical.OR)
    @ApiOperation("新增或修改临时合作伙伴")
    @PostMapping("/save")
    public ResponseEntity<?> save(@ApiParam("临时合作伙伴请求体") @RequestBody TemporaryPartners temporaryPartners)  {
        if (temporaryPartnersService.save(temporaryPartners)){
            return ResponseEntity.ok("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("操作失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","user"},logical = Logical.OR)
    @ApiOperation("删除临时合作伙伴")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@ApiParam("临时合作伙伴请求体") @RequestBody List<TemporaryPartners> temporaryPartners)  {
        if (temporaryPartnersService.delete(temporaryPartners)){
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("删除失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","user"},logical = Logical.OR)
    @ApiOperation("分页查询临时合作伙伴")
    @PostMapping("/search")
    public ResponseEntity<?> search(@ApiParam("分页查询请求体") @RequestBody PageRequest pageRequest)  {
        List<TemporaryPartners> temporaryPartners = temporaryPartnersService.search(pageRequest.getKeyword());
        if (temporaryPartners != null){
            IPage<TemporaryPartners> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
            page.setRecords(temporaryPartners);
            page.setPages(temporaryPartners.size() / pageRequest.getPageSize() + 1);
            page.setTotal(temporaryPartners.size());
            PageResult<TemporaryPartners> pageResult = new PageResult<>(page);
            return ResponseEntity.ok(pageResult);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查询失败");
        }
    }

    @ApiOperation("通过临时合作伙伴ID查询临时合作伙伴")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchById(@ApiParam("商户ID") @PathVariable Integer id) {
        return ResponseEntity.ok(temporaryPartnersService.getById(id));
    }
}
