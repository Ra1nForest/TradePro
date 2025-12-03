package com.linn.tradepro.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linn.tradepro.common.entity.PageRequest;
import com.linn.tradepro.common.entity.PageResult;
import com.linn.tradepro.common.util.DateTimeUtils;
import com.linn.tradepro.entity.*;
import com.linn.tradepro.service.FinancesService;
import com.linn.tradepro.service.OrderDetailsService;
import com.linn.tradepro.service.OrdersService;
import com.linn.tradepro.service.ProductsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "订单表 前端控制器")
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    private OrdersService orderService;

    @Resource
    private FinancesService financesService;

    @RequiresRoles(value = {"superadmin", "admin", "trade"}, logical = Logical.OR)
    @ApiOperation("新建或修改订单")
    @PostMapping("/save")
    public ResponseEntity<?> save(@ApiParam("订单信息") @RequestBody Orders order,
                       @ApiParam("订单细节") @RequestBody List<OrderDetails> details) {
        if (orderService.save(order,details)){
            return ResponseEntity.ok("操作成功");
        } else {
            return ResponseEntity.badRequest().body("操作失败");
        }
    }

    @ApiOperation("分页查询账单")
    @PostMapping("/search")
    public ResponseEntity<?> search(@ApiParam("分页查询请求体") @RequestBody PageRequest pageRequest)  {
        List<Orders> orders = orderService.search(pageRequest.getKeyword());
        if (orders != null){
            IPage<Orders> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
            page.setRecords(orders);
            page.setPages(orders.size() / pageRequest.getPageSize() + 1);
            page.setTotal(orders.size());
            PageResult<Orders> pageResult = new PageResult<>(page);
            return ResponseEntity.ok(pageResult);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查询失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","trade"},logical = Logical.OR)
    @ApiOperation("接受订单")
    @PostMapping ("/acceptIn/{id}")
    public ResponseEntity<?> accept(@ApiParam("订单ID") @PathVariable Integer id,
                                    @ApiParam("商品存放类") @RequestBody List<Products> products)  {
        if (orderService.accept(id,products) && financesService.saveOrder(orderService.getById(id))){
            return ResponseEntity.ok("接受成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("接受失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","trade"},logical = Logical.OR)
    @ApiOperation("拒绝该订单")
    @GetMapping ("/reject/{id}")
    public ResponseEntity<?> reject(@ApiParam("订单ID") @PathVariable Integer id)  {
        if (orderService.reject(id)){
            return ResponseEntity.ok("拒绝成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("拒绝失败");
        }
    }

    @ApiOperation("根据订单ID获取订单信息")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> getOrderById(@ApiParam("订单ID") @PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getById(id));
    }
}
