package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.SusheInfo;
import com.example.exception.CustomException;
import com.example.service.*;
import com.example.vo.SusheInfoVo;

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
@RequestMapping(value = "/susheInfo")
public class SusheInfoController {
    @Resource
    private SusheInfoService susheInfoService;

    @PostMapping
    public Result<SusheInfo> add(@RequestBody SusheInfoVo susheInfo) {
        List<SusheInfoVo> all = susheInfoService.findAll();
        List<SusheInfoVo> collect = all.stream().filter(x -> x.getName().equals(susheInfo.getName())).collect(Collectors.toList());
        if (collect.size() >= 4) {
            throw new CustomException("1001", "同一个宿舍最多四人，不允许超额添加");
        }
        susheInfoService.add(susheInfo);
        return Result.success(susheInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        susheInfoService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody SusheInfoVo susheInfo) {
        susheInfoService.update(susheInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SusheInfo> detail(@PathVariable Long id) {
        SusheInfo susheInfo = susheInfoService.findById(id);
        return Result.success(susheInfo);
    }

    @GetMapping
    public Result<List<SusheInfoVo>> all() {
        return Result.success(susheInfoService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<SusheInfoVo>> page(@PathVariable String name,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(susheInfoService.findPage(name, pageNum, pageSize, request));
    }

    /**
    * 批量通过excel添加信息
    * @param file excel文件
    * @throws IOException
    */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {

        List<SusheInfo> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(SusheInfo.class);
        if (!CollectionUtil.isEmpty(infoList)) {
            // 处理一下空数据
            List<SusheInfo> resultList = infoList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getName())).collect(Collectors.toList());
            for (SusheInfo info : resultList) {
                susheInfoService.add(info);
            }
        }
        return Result.success();
    }

    @GetMapping("/getExcelModel")
    public void getExcelModel(HttpServletResponse response) throws IOException {
        // 1. 生成excel
        Map<String, Object> row = new LinkedHashMap<>();
		row.put("name", "");
		row.put("student", "");

        List<Map<String, Object>> list = CollUtil.newArrayList(row);

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=susheInfoModel.xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }
}
