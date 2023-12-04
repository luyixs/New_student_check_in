package com.example.controller;

import com.example.common.Result;
import com.example.entity.ZhidaoInfoComment;
import com.example.vo.ZhidaoInfoCommentVo;
import com.example.service.ZhidaoInfoCommentService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/zhidaoInfoComment")
public class ZhidaoInfoCommentController {
    @Resource
    private ZhidaoInfoCommentService zhidaoInfoCommentService;

    @PostMapping
    public Result<ZhidaoInfoComment> add(@RequestBody ZhidaoInfoComment commentInfo, HttpServletRequest request) {
        zhidaoInfoCommentService.add(commentInfo, request);
        return Result.success(commentInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        zhidaoInfoCommentService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody ZhidaoInfoComment commentInfo) {
        zhidaoInfoCommentService.update(commentInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<ZhidaoInfoComment> detail(@PathVariable Long id) {
        ZhidaoInfoComment commentInfo = zhidaoInfoCommentService.findById(id);
        return Result.success(commentInfo);
    }

    @GetMapping
    public Result<List<ZhidaoInfoCommentVo>> all() {
        return Result.success(zhidaoInfoCommentService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<ZhidaoInfoCommentVo>> page(@PathVariable String name,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(zhidaoInfoCommentService.findPage(name, pageNum, pageSize));
    }

    @GetMapping("/findByForeignId/{id}")
    public Result<List<ZhidaoInfoCommentVo>> findByForeignId (@PathVariable Long id) {
        return Result.success(zhidaoInfoCommentService.findByForeignId(id));
    }
}
