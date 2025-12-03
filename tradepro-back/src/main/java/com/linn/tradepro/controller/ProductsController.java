package com.linn.tradepro.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linn.tradepro.common.entity.PageRequest;
import com.linn.tradepro.common.entity.PageResult;
import com.linn.tradepro.entity.Products;
import com.linn.tradepro.service.ProductsService;
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
 * 商品表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "商品表 前端控制器")
@RequestMapping("/products")
public class ProductsController {

    @Resource
    private ProductsService productsService;

    @RequiresRoles(value = {"superadmin","admin","store"}, logical = Logical.OR)
    @ApiOperation("新增或修改商品")
    @PostMapping("/save")
    public ResponseEntity<?> save(@ApiParam("商品请求体") @RequestBody Products products)  {
        if (productsService.save(products)){
            return ResponseEntity.ok("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("操作失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","store"}, logical = Logical.OR)
    @ApiOperation("删除商品")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@ApiParam("商品请求体") @RequestBody Products products)  {
        if (productsService.delete(products)){
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("删除失败");
        }
    }

    @ApiOperation("通过商品分类分页查询商品")
    @PostMapping("/search/category/{categoryId}")
    public ResponseEntity<?> searchByCategory(@ApiParam("分页查询请求体") @RequestBody PageRequest pageRequest,
                                              @ApiParam("商品类id") @PathVariable("categoryId") Integer categoryId)  {
        IPage<Products> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<Products> products = productsService.searchByCategory(pageRequest.getKeyword(), categoryId);
        page.setRecords(products);
        page.setTotal(products.size());
        page.setPages(products.size() / pageRequest.getPageSize() + 1);
        PageResult<Products> pageResult = new PageResult<>(page);
        return ResponseEntity.ok(pageResult);
    }

    @ApiOperation("通过关键字仓库区域分页查询商品")
    @PostMapping("/search/area/{areaId}")
    public ResponseEntity<?> searchByArea(@ApiParam("分页查询请求体") @RequestBody PageRequest pageRequest,
                                              @ApiParam("仓库区域id") @PathVariable("areaId") Integer areaId)  {
        IPage<Products> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<Products> products = productsService.searchByArea(pageRequest.getKeyword(), areaId);
        page.setRecords(products);
        page.setTotal(products.size());
        page.setPages(products.size() / pageRequest.getPageSize() + 1);
        PageResult<Products> pageResult = new PageResult<>(page);
        return ResponseEntity.ok(pageResult);
    }

    @ApiOperation("通过ID查询商品")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchById(@ApiParam("商品ID") @PathVariable Integer id )  {
        return ResponseEntity.ok(productsService.getById(id));
    }
}
