package com.linn.tradepro.controller;

import com.linn.tradepro.entity.Warehouses;
import com.linn.tradepro.service.WarehouseLocationsService;
import com.linn.tradepro.service.WarehousesService;
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
 * 仓库表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "仓库表 前端控制器")
@RequestMapping("/warehouses")
public class WarehousesController {

    @Resource
    private WarehousesService warehousesService;

    @Resource
    private WarehouseLocationsService warehouseLocationsService;

    @RequiresRoles(value = {"superadmin","admin","store"}, logical = Logical.OR)
    @ApiOperation("新增或修改仓库")
    @PostMapping("/save")
    public ResponseEntity<?> save(@ApiParam("仓库请求体") @RequestBody Warehouses warehouses)  {
        if (warehousesService.save(warehouses)){
            return ResponseEntity.ok("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("操作失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","store"}, logical = Logical.OR)
    @ApiOperation("删除仓库")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@ApiParam("仓库请求体") @RequestBody Warehouses warehouses)  {
        if (warehousesService.delete(warehouses)){
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("删除失败");
        }
    }

    @ApiOperation("查询仓库")
    @PostMapping("/search")
    public ResponseEntity<?> search()  {
        if (warehousesService.searchAll().size() > 0){
            return ResponseEntity.ok(warehousesService.searchAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查询失败");
        }
    }

    @ApiOperation("通过仓库ID查询所有仓库区域")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> search(@ApiParam("仓库ID") @PathVariable("id") Integer id) {
        return ResponseEntity.ok(warehouseLocationsService.searchByWarehouseId(id));
    }
}
