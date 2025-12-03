package com.linn.tradepro.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linn.tradepro.common.entity.PageRequest;
import com.linn.tradepro.common.entity.PageResult;
import com.linn.tradepro.entity.Finances;
import com.linn.tradepro.entity.Products;
import com.linn.tradepro.service.FinancesService;
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
 * 财务表 前端控制器
 * </p>
 *
 * @author linn
 * @since 2023-05-20
 */
@RestController
@Api(tags = "财务表 前端控制器")
@RequestMapping("/finances")
public class FinancesController {

    @Resource
    private FinancesService financesService;

    @RequiresRoles(value = {"superadmin","admin","finance"}, logical = Logical.OR)
    @ApiOperation("新增或修改财务事件")
    @PostMapping("/save")
    public ResponseEntity<?> save(@ApiParam("财务请求体") @RequestBody Finances finances)  {
        if (financesService.save(finances)){
            return ResponseEntity.ok("操作成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("操作失败");
        }
    }

    @RequiresRoles(value = {"superadmin","admin","finance"}, logical = Logical.OR)
    @ApiOperation("删除财务事件")
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@ApiParam("财务请求体") @RequestBody List<Finances> finances)  {
        if (financesService.delete(finances)){
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("删除失败");
        }
    }

    @ApiOperation("分页查询财务事件")
    @PostMapping("/search")
    public ResponseEntity<?> search(@ApiParam("分页请求体") @RequestBody PageRequest pageRequest)  {
        IPage<Finances> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<Finances> list = financesService.search(pageRequest.getKeyword());
        page.setRecords(list);
        page.setTotal(list.size());
        page.setPages(list.size() / pageRequest.getPageSize() + 1);
        PageResult<Finances> result = new PageResult<>(page);
        return ResponseEntity.ok(result);
    }

    @ApiOperation("根据ID查询财务事件")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchById(@PathVariable("id") Integer id)  {
        if (financesService.searchById(id) != null){
            return ResponseEntity.ok(financesService.searchById(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查询失败");
        }
    }
}
