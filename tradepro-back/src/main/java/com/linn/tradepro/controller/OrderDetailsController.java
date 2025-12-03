package com.linn.tradepro.controller;

import com.linn.tradepro.entity.OrderDetails;
import com.linn.tradepro.service.OrderDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单详情表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "订单详情表 前端控制器")
@RequestMapping("/orderDetails")
public class OrderDetailsController {

    @Resource
    private OrderDetailsService orderDetailsService;

    @ApiOperation("按订单id查询订单详情")
    @GetMapping("/search/{orderId}")
    public ResponseEntity<?> search(@ApiParam("订单id") @PathVariable Integer orderId)  {
        List<OrderDetails> list = orderDetailsService.getOrderDetailsByOrderId(orderId);
        return ResponseEntity.ok(list);
    }

}
