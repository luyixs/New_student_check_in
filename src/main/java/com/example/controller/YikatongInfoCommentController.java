package com.example.controller;

import com.example.common.Result;
import com.example.entity.YikatongInfoComment;
import com.example.vo.YikatongInfoCommentVo;
import com.example.service.YikatongInfoCommentService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/yikatongInfoComment")
public class YikatongInfoCommentController {
    @Resource
    private YikatongInfoCommentService yikatongInfoCommentService;

    @PostMapping
    public Result<YikatongInfoComment> add(@RequestBody YikatongInfoComment commentInfo, HttpServletRequest request) {
        yikatongInfoCommentService.add(commentInfo, request);
        return Result.success(commentInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        yikatongInfoCommentService.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody YikatongInfoComment commentInfo) {
        yikatongInfoCommentService.update(commentInfo);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<YikatongInfoComment> detail(@PathVariable Long id) {
        YikatongInfoComment commentInfo = yikatongInfoCommentService.findById(id);
        return Result.success(commentInfo);
    }

    @GetMapping
    public Result<List<YikatongInfoCommentVo>> all() {
        return Result.success(yikatongInfoCommentService.findAll());
    }

    @GetMapping("/page/{name}")
    public Result<PageInfo<YikatongInfoCommentVo>> page(@PathVariable String name,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "5") Integer pageSize,
                                                HttpServletRequest request) {
        return Result.success(yikatongInfoCommentService.findPage(name, pageNum, pageSize));
    }

    @GetMapping("/findByForeignId/{id}")
    public Result<List<YikatongInfoCommentVo>> findByForeignId (@PathVariable Long id) {
        return Result.success(yikatongInfoCommentService.findByForeignId(id));
    }
}
