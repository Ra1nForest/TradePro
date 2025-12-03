package com.linn.tradepro.controller;

import com.linn.tradepro.entity.ProductCategories;
import com.linn.tradepro.service.ProductCategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 商品分类表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "商品分类表 前端控制器")
@RequestMapping("/productCategories")
public class ProductCategoriesController {

    @Resource
    private ProductCategoriesService productCategoriesService;

    @RequiresRoles(value = {"superadmin","admin","store"}, logical = Logical.OR)
    @ApiOperation("新增或修改商品类")
    @PostMapping("/save")
    public ResponseEntity<?> save(@ApiParam("商品类请求体") @RequestBody ProductCategories productCategories)  {
        if (productCategoriesService.save(productCategories)){
            return ResponseEntity.ok("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("操作失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","store"}, logical = Logical.OR)
    @ApiOperation("删除商品类")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@ApiParam("商品类请求体") @RequestBody ProductCategories productCategories)  {
        if (productCategoriesService.delete(productCategories)){
            return ResponseEntity.ok("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("操作失败");
        }
    }

    @ApiOperation("搜索商户下所有商品类")
    @PostMapping("/searchAll")
    public ResponseEntity<?> searchAll()  {
        if (productCategoriesService.searchAll().size() > 0){
            return ResponseEntity.ok(productCategoriesService.searchAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("操作失败");
        }
    }

    @ApiOperation("通过ID查询商品分类")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchById(@ApiParam("商品分类ID") @PathVariable Integer id) {
        return ResponseEntity.ok(productCategoriesService.getById(id));
    }
}
