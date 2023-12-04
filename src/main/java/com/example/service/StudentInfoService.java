package com.example.service;

import com.example.dao.StudentInfoDao;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.entity.StudentInfo;
import com.example.exception.CustomException;
import com.example.common.ResultCode;
import com.example.vo.StudentInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.crypto.SecureUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class StudentInfoService {

    @Resource
    private StudentInfoDao studentInfoDao;

    public StudentInfo add(StudentInfo studentInfo) {
        // 唯一校验
        int count = studentInfoDao.checkRepeat("name", studentInfo.getName(), null);
        if (count > 0) {
            throw new CustomException("1001", "用户名\"" + studentInfo.getName() + "\"已存在");
        }
        if (StringUtils.isEmpty(studentInfo.getPassword())) {
            // 默认密码123456
            studentInfo.setPassword(SecureUtil.md5("123456"));
        } else {
            studentInfo.setPassword(SecureUtil.md5(studentInfo.getPassword()));
        }
        studentInfoDao.insertSelective(studentInfo);
        return studentInfo;
    }

    public void delete(Long id) {
        studentInfoDao.deleteByPrimaryKey(id);
    }

    public void update(StudentInfo studentInfo) {
        studentInfoDao.updateByPrimaryKeySelective(studentInfo);
    }

    public StudentInfo findById(Long id) {
        return studentInfoDao.selectByPrimaryKey(id);
    }

    public List<StudentInfoVo> findAll() {
        return studentInfoDao.findByName("all");
    }

    public PageInfo<StudentInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentInfoVo> all = studentInfoDao.findByName(name);
        return PageInfo.of(all);
    }

    public StudentInfoVo findByUserName(String name) {
        return studentInfoDao.findByUsername(name);
    }

    public StudentInfo login(String username, String password) {
        StudentInfo studentInfo = studentInfoDao.findByUsername(username);
        if (studentInfo == null) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        if (!SecureUtil.md5(password).equalsIgnoreCase(studentInfo.getPassword())) {
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        return studentInfo;
    }

}
