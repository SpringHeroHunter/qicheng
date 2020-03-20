package com.springboot.springboot.controller;

import com.springboot.springboot.dao.IAccountDao;
import com.springboot.springboot.service.IAccountService;
import com.springboot.springboot.utils.ResultVO;
import com.springboot.springboot.utils.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@Api(value = "account",description = "API")
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountDao accountDao;

    @GetMapping("/list")
    @ApiOperation(value="查询列表", notes="查询列表")
    public ResultVO getAccounts() {
//        return ResultVOUtil.success(accountService.findAccountList());
        return ResultVOUtil.success(accountDao.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value="根据ID获取信息", notes="获取信息")
    public ResultVO getAccountById(@PathVariable("id") int id) {
        return ResultVOUtil.success(accountService.findAccountById(id));
    }


    @GetMapping("/up/{id}")
    @ApiOperation(value="根据ID更新信息", notes="更新信息")
    public ResultVO updateAccount(@PathVariable("id") int id,
                                @RequestParam(value = "name", required = true) String name,
                                @RequestParam(value = "money", required = true) double money) {
        int t = accountService.update(name, money, id);
        if (t == 1) {
            return ResultVOUtil.success("success");
        }else {
            return ResultVOUtil.success("fail");
        }

    }
    @PostMapping("/add")
    @ApiOperation(value="增加信息", notes="增加信息")
    public ResultVO postAccount(@RequestParam(value = "name") String name,
                              @RequestParam(value = "money") double money) {
        int t = accountService.add(name, money);
        if (t ==1) {
            return ResultVOUtil.success("success");
        }else {
            return ResultVOUtil.success("fail");
        }
    }
}
