package com.linn.tradepro.controller;

import com.linn.tradepro.entity.WarehouseLocations;
import com.linn.tradepro.service.ProductsService;
import com.linn.tradepro.service.WarehouseLocationsService;
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
 * 仓库位置表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "仓库位置表 前端控制器")
@RequestMapping("/warehouseLocations")
public class WarehouseLocationsController {

    @Resource
    private WarehouseLocationsService warehouseLocationsService;

    @Resource
    private ProductsService productsService;

    @RequiresRoles(value = {"superadmin","admin","store"}, logical = Logical.OR)
    @ApiOperation("新增或修改仓库区域")
    @PostMapping("/save")
    public ResponseEntity<?> save(@ApiParam("仓库区域请求体") @RequestBody WarehouseLocations warehouseLocations)  {

        List<WarehouseLocations> warehouseLocationsList = warehouseLocationsService.searchByWarehouseId(warehouseLocations.getWarehouseId());

        for (WarehouseLocations wl : warehouseLocationsList) {
            if (wl.getLocation().equals(warehouseLocations.getLocation())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("该仓库区域已存在");
            }
        }

        if (warehouseLocationsService.save(warehouseLocations)){
            return ResponseEntity.ok("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("操作失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","store"}, logical = Logical.OR)
    @ApiOperation("删除仓库区域")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@ApiParam("仓库区域请求体") @RequestBody List<WarehouseLocations> warehouseLocations) {

        for (WarehouseLocations wl : warehouseLocations) {
            if (!productsService.getByWarehouseLocationId(wl.getId()).isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("所选仓库区域下存在商品，请先转移");
            }
        }
         if (warehouseLocationsService.removeByIds(warehouseLocations)){
             return ResponseEntity.ok("删除成功");
         } else {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("删除失败");
         }
    }


    @ApiOperation("通过仓库区域ID查询仓库区域")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchByWarehouseId(@ApiParam("仓库区域ID") @PathVariable("id") Integer id) {
        return ResponseEntity.ok(warehouseLocationsService.getById(id));
    }
}
