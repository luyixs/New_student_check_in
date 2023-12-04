package com.example.controller;

import com.example.common.Result;
import com.example.entity.SubmitInfoComment;
import com.example.vo.SubmitInfoCommentVo;
import com.example.service.SubmitInfoCommentService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/submitInfoComment")
public class SubmitInfoCommentController {
    @Resource
    private SubmitInfoCommentService submitInfoCommentService;

    @PostMapping
    public Result<SubmitInfoComment> add(@RequestBody SubmitInfoComment commentInfo, HttpServletRequest request) {
        submitInfoCommentService.add(commentInfo, request);
        return Result.success(commentInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        submitInfoCommentService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody SubmitInfoComment commentInfo) {
        submitInfoCommentService.update(commentInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<SubmitInfoComment> detail(@PathVariable Long id) {
        SubmitInfoComment commentInfo = submitInfoCommentService.findById(id);
        return Result.success(commentInfo);
    }

    @GetMapping
    public Result<List<SubmitInfoCommentVo>> all() {
        return Result.success(submitInfoCommentService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<SubmitInfoCommentVo>> page(@PathVariable String name,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(submitInfoCommentService.findPage(name, pageNum, pageSize));
    }

    @GetMapping("/findByForeignId/{id}")
    public Result<List<SubmitInfoCommentVo>> findByForeignId (@PathVariable Long id) {
        return Result.success(submitInfoCommentService.findByForeignId(id));
    }
}
