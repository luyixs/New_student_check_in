package com.example.controller;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.JiaofeiInfo;
import com.example.entity.StudentInfo;
import com.example.service.StudentInfoService;
import com.example.vo.JiaofeiInfoVo;
import com.example.service.JiaofeiInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/jiaofeiInfo")
public class JiaofeiInfoController {
    @Resource
    private JiaofeiInfoService jiaofeiInfoService;
    @Resource
    private StudentInfoService studentInfoService;

    @PostMapping
    public Result add(@RequestBody JiaofeiInfo info, HttpServletRequest request) {
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
        jiaofeiInfoService.add(info);
        return Result.success(info);
    }

    /** 发布者或者管理员编辑发布信息后提交 */
    @PutMapping("/submit")
    public Result updateSubmit(@RequestBody JiaofeiInfo info, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("1001", "session已失效，请重新登录");
        }
        JiaofeiInfo dbInfo = jiaofeiInfoService.findById(info.getId());
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
        jiaofeiInfoService.update(info);
        return Result.success();
    }

    /** 更新审核内容，用户管理员审核按钮 */
    @PutMapping("/verify")
    public Result updateReserve(@RequestBody JiaofeiInfo info, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("1001", "session已失效，请重新登录");
        }
        info.setPublishVerifyName(user.getName());
        jiaofeiInfoService.update(info);

        // 更新一下父节点
        info.setId(info.getParentId());
        info.setParentId(0L);
        jiaofeiInfoService.update(info);
        return Result.success();
    }

    /**
    * 发布提交审核，专门处理发布
    */
    @PostMapping("/submit")
    public Result submit(@RequestBody JiaofeiInfo info, HttpServletRequest request) {
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
        jiaofeiInfoService.update(info);
        return Result.success();
    }

    /**
    * 预约提交审核，专门处理预约
    */
    @PostMapping("/reserve")
    public Result reserve(@RequestBody JiaofeiInfo info, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            return Result.error("1001", "session已失效，请重新登录");
        }

        JiaofeiInfo reserveInfo = jiaofeiInfoService.findByReserveId(user.getId(), info.getId());
        if (reserveInfo != null) {
            return Result.error("1001", "请不要重复点击，耐心等待管理员审核，您可以在后台查看审核结果");
        }

        StudentInfo studentInfo = studentInfoService.findById(user.getId());

        if (Double.parseDouble(info.getPrice()) > studentInfo.getAccount()) {
            return Result.error("1001", "账户余额不足，请在后台先充值");
        }
        studentInfo.setAccount(studentInfo.getAccount() - Double.parseDouble(info.getPrice()));
        studentInfoService.update(studentInfo);
        // 复制一份
        JiaofeiInfo copyInfo = new JiaofeiInfo();
        BeanUtils.copyProperties(info, copyInfo);
        copyInfo.setParentId(info.getId());
        copyInfo.setReserveStatus("待审核");
        copyInfo.setReserveReason("");
        copyInfo.setReserveName(user.getName());
        copyInfo.setReserveId(user.getId());
        copyInfo.setFileId(info.getFileId());
        copyInfo.setFileName(info.getFileName());
        copyInfo.setId(null);
        jiaofeiInfoService.add(copyInfo);

        // 将原记录加个状态
        info.setReserveStatus("待审核");
        info.setReserveId(user.getId());
        info.setReserveName(user.getName());
        info.setFileId(null);
        info.setFileName(null);
        jiaofeiInfoService.update(info);
        return Result.success();
    }

    @DeleteMapping("/publish/{id}")
    public Result deletePublish(@PathVariable Long id, HttpServletRequest request) {
        JiaofeiInfo dbInfo = jiaofeiInfoService.findById(id);
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
        jiaofeiInfoService.delete(id);
        return Result.success();
    }

    @DeleteMapping("/reserve/{id}")
    public Result deleteReserve(@PathVariable Long id, HttpServletRequest request) {
        JiaofeiInfo dbInfo = jiaofeiInfoService.findById(id);
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
        jiaofeiInfoService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<JiaofeiInfo> detail(@PathVariable Long id) {
        JiaofeiInfo info = jiaofeiInfoService.findById(id);
        return Result.success(info);
    }

    @GetMapping
    public Result<List<JiaofeiInfoVo>> all() {
        return Result.success(jiaofeiInfoService.findAll());
    }

    @GetMapping("/page/{flag}")
    public Result<PageInfo<JiaofeiInfoVo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "5") Integer pageSize,
                                                      @PathVariable Integer flag,
                                                      HttpServletRequest request) {
        return Result.success(jiaofeiInfoService.findPage(pageNum, pageSize, flag, request));
    }

    @GetMapping("/page")
    public Result<PageInfo<JiaofeiInfoVo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.success(jiaofeiInfoService.findPage(pageNum, pageSize));
    }

}
