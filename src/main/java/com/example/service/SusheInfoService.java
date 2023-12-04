package com.example.service;

import cn.hutool.json.JSONUtil;
import com.example.dao.SusheInfoDao;
import org.springframework.stereotype.Service;
import com.example.entity.SusheInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.SusheInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class SusheInfoService {

    @Resource
    private SusheInfoDao susheInfoDao;

    public SusheInfo add(SusheInfo susheInfo) {
        susheInfoDao.insertSelective(susheInfo);
        return susheInfo;
    }

    public void delete(Long id) {
        susheInfoDao.deleteByPrimaryKey(id);
    }

    public void update(SusheInfo susheInfo) {
        susheInfoDao.updateByPrimaryKeySelective(susheInfo);
    }

    public SusheInfo findById(Long id) {
        return susheInfoDao.selectByPrimaryKey(id);
    }

    public List<SusheInfoVo> findAll() {
        return susheInfoDao.findByName("all");
    }

    public PageInfo<SusheInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<SusheInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    public List<SusheInfoVo> findAllPage(HttpServletRequest request, String name) {
		return susheInfoDao.findByName(name);
    }

}
