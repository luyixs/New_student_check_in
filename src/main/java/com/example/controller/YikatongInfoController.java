package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.YikatongInfo;
import com.example.vo.YikatongInfoVo;
import com.example.service.YikatongInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/yikatongInfo")
public class YikatongInfoController {
    @Resource
    private YikatongInfoService yikatongInfoService;

    @PostMapping
    public Result add(@RequestBody YikatongInfo info, HttpServletRequest request) {
    // 新增时候只有发布者，有两种用户，管理员和非管理员
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("1001", "session已失效，请重新登录");
        }
        // 管理员新增，自动发布审核通过
        if (user.getLevel() == 1) {
            info.setPublishStatus("审核通过");
            info.setPublishReason("符合要求");
            info.setPublishId(user.getId());
            info.setPublishVerifyName(user.getName());
        } else {
            // 其他人新增，默认未提交，等待发布人提交
            info.setPublishStatus("未提交");
            info.setPublishId(user.getId());
        }

        info.setPublishName(user.getName());
        yikatongInfoService.add(info);
        return Result.success(info);
    }

    /** 发布者或者管理员编辑发布信息后提交 */
    @PutMapping("/submit")
    public Result updateSubmit(@RequestBody YikatongInfo info, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("1001", "session已失效，请重新登录");
        }
        YikatongInfo dbInfo = yikatongInfoService.findById(info.getId());
        if (!dbInfo.getPublishId().equals(user.getId()) || !dbInfo.getPublishName().equals(user.getName())) {
            return Result.error("1001", "只能编辑自己发布的信息");
        }

        if (user.getLevel() == 1) {
            info.setPublishStatus("审核通过");
            info.setPublishReason("符合要求");
            info.setPublishId(0L);
            info.setPublishVerifyName(user.getName());
        } else {
            info.setPublishStatus("未提交");
            info.setPublishVerifyName("");
            info.setPublishReason("");
        }
        yikatongInfoService.update(info);
        return Result.success();
    }

    /** 更新审核内容，用户管理员审核按钮 */
    @PutMapping("/verify")
    public Result updateReserve(@RequestBody YikatongInfo info, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("1001", "session已失效，请重新登录");
        }
        info.setPublishVerifyName(user.getName());
        yikatongInfoService.update(info);

        // 更新一下父节点
        info.setId(info.getParentId());
        info.setParentId(0L);
        yikatongInfoService.update(info);
        return Result.success();
    }

    /**
    * 发布提交审核，专门处理发布
    */
    @PostMapping("/submit")
    public Result submit(@RequestBody YikatongInfo info, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("1001", "session已失效，请重新登录");
        }
        if (1 == user.getLevel()) {
            // 如果是管理员发布审核，直接审核通过
            info.setPublishStatus("审核通过");
            info.setPublishVerifyName(user.getName());
            info.setPublishReason("管理员发布自动审核通");
        } else {
            info.setPublishStatus("待审核");
            info.setPublishId(user.getId());
            info.setPublishVerifyName("");
            info.setPublishReason("");
        }
        info.setPublishName(user.getName());
        yikatongInfoService.update(info);
        return Result.success();
    }

    /**
    * 预约提交审核，专门处理预约
    */
    @PostMapping("/reserve")
    public Result reserve(@RequestBody YikatongInfo info, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("1001", "session已失效，请重新登录");
        }

        YikatongInfo reserveInfo = yikatongInfoService.findByReserveId(user.getId(), info.getId());
        if (reserveInfo != null) {
            return Result.error("1001", "请不要重复点击，耐心等待管理员审核，您可以在后台查看审核结果");
        }
        // 复制一份
        YikatongInfo copyInfo = new YikatongInfo();
        BeanUtils.copyProperties(info, copyInfo);
        copyInfo.setParentId(info.getId());
        copyInfo.setReserveStatus("待审核");
        copyInfo.setReserveReason("");
        copyInfo.setReserveName(user.getName());
        copyInfo.setReserveId(user.getId());
        copyInfo.setFileId(info.getFileId());
        copyInfo.setFileName(info.getFileName());
        copyInfo.setId(null);
        yikatongInfoService.add(copyInfo);

        // 将原记录加个状态
        info.setReserveStatus("待审核");
        info.setReserveId(user.getId());
        info.setReserveName(user.getName());
        info.setFileId(null);
        info.setFileName(null);
        yikatongInfoService.update(info);
        return Result.success();
    }

    @DeleteMapping("/publish/{id}")
    public Result deletePublish(@PathVariable Long id, HttpServletRequest request) {
        YikatongInfo dbInfo = yikatongInfoService.findById(id);
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("1001", "session已失效，请重新登录");
        }
        if (user.getLevel() != 1) {
            // 非管理员只能删除自己的
            if (!user.getId().equals(dbInfo.getPublishId()) || !user.getName().equals(dbInfo.getPublishName())) {
                return Result.error("1001", "只能删除自己发布的信息");
            }
        }
        yikatongInfoService.delete(id);
        return Result.success();
    }

    @DeleteMapping("/reserve/{id}")
    public Result deleteReserve(@PathVariable Long id, HttpServletRequest request) {
        YikatongInfo dbInfo = yikatongInfoService.findById(id);
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("1001", "session已失效，请重新登录");
        }
        if (user.getLevel() != 1) {
            // 非管理员只能删除自己的
            if (!user.getId().equals(dbInfo.getReserveId()) || !user.getName().equals(dbInfo.getReserveName())) {
                return Result.error("1001", "只能删除自己预约的信息");
            }
        }
        yikatongInfoService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<YikatongInfo> detail(@PathVariable Long id) {
        YikatongInfo info = yikatongInfoService.findById(id);
        return Result.success(info);
    }

    @GetMapping
    public Result<List<YikatongInfoVo>> all() {
        return Result.success(yikatongInfoService.findAll());
    }

    @GetMapping("/page/{flag}")
    public Result<PageInfo<YikatongInfoVo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "5") Integer pageSize,
                                                      @PathVariable Integer flag,
                                                      HttpServletRequest request) {
        return Result.success(yikatongInfoService.findPage(pageNum, pageSize, flag, request));
    }

    @GetMapping("/page")
    public Result<PageInfo<YikatongInfoVo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.success(yikatongInfoService.findPage(pageNum, pageSize));
    }

}
