package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.common.ResultCode;
import com.example.entity.ZhidaoInfo;
import com.example.service.*;
import com.example.vo.ZhidaoInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/zhidaoInfo")
public class ZhidaoInfoController {
    @Resource
    private ZhidaoInfoService zhidaoInfoService;

    @PostMapping
    public Result<ZhidaoInfo> add(@RequestBody ZhidaoInfoVo info) {
        zhidaoInfoService.add(info);
        return Result.success(info);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        zhidaoInfoService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody ZhidaoInfoVo info) {
        zhidaoInfoService.update(info);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<ZhidaoInfo> detail(@PathVariable Long id) {
        ZhidaoInfo info = zhidaoInfoService.findById(id);
        return Result.success(info);
    }

    @GetMapping
    public Result<List<ZhidaoInfoVo>> all() {
        return Result.success(zhidaoInfoService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<ZhidaoInfoVo>> page(@PathVariable String name,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(zhidaoInfoService.findPage(name, pageNum, pageSize, request));
    }
}
