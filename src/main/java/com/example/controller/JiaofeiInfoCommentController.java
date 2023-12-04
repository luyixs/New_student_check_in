package com.example.controller;

import com.example.common.Result;
import com.example.entity.JiaofeiInfoComment;
import com.example.vo.JiaofeiInfoCommentVo;
import com.example.service.JiaofeiInfoCommentService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/jiaofeiInfoComment")
public class JiaofeiInfoCommentController {
    @Resource
    private JiaofeiInfoCommentService jiaofeiInfoCommentService;

    @PostMapping
    public Result<JiaofeiInfoComment> add(@RequestBody JiaofeiInfoComment commentInfo, HttpServletRequest request) {
        jiaofeiInfoCommentService.add(commentInfo, request);
        return Result.success(commentInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        jiaofeiInfoCommentService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody JiaofeiInfoComment commentInfo) {
        jiaofeiInfoCommentService.update(commentInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<JiaofeiInfoComment> detail(@PathVariable Long id) {
        JiaofeiInfoComment commentInfo = jiaofeiInfoCommentService.findById(id);
        return Result.success(commentInfo);
    }

    @GetMapping
    public Result<List<JiaofeiInfoCommentVo>> all() {
        return Result.success(jiaofeiInfoCommentService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<JiaofeiInfoCommentVo>> page(@PathVariable String name,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(jiaofeiInfoCommentService.findPage(name, pageNum, pageSize));
    }

    @GetMapping("/findByForeignId/{id}")
    public Result<List<JiaofeiInfoCommentVo>> findByForeignId (@PathVariable Long id) {
        return Result.success(jiaofeiInfoCommentService.findByForeignId(id));
    }
}
