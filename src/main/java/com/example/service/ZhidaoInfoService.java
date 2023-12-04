package com.example.service;

import cn.hutool.json.JSONUtil;
import com.example.dao.ZhidaoInfoDao;
import org.springframework.stereotype.Service;
import com.example.entity.ZhidaoInfo;
import com.example.entity.AuthorityInfo;
import com.example.entity.Account;
import com.example.vo.ZhidaoInfoVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Value;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ZhidaoInfoService {

    @Resource
    private ZhidaoInfoDao zhidaoInfoDao;

    public ZhidaoInfo add(ZhidaoInfo info) {
        zhidaoInfoDao.insertSelective(info);
        return info;
    }

    public void delete(Long id) {
        zhidaoInfoDao.deleteByPrimaryKey(id);
    }

    public void update(ZhidaoInfo info) {
        zhidaoInfoDao.updateByPrimaryKeySelective(info);
    }

    public ZhidaoInfo findById(Long id) {
        return zhidaoInfoDao.selectByPrimaryKey(id);
    }

    public List<ZhidaoInfoVo> findAll() {
        return zhidaoInfoDao.findByName("all");
    }

    public PageInfo<ZhidaoInfoVo> findPage(String name, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        PageHelper.startPage(pageNum, pageSize);
        List<ZhidaoInfoVo> all = findAllPage(request, name);
        return PageInfo.of(all);
    }

    public List<ZhidaoInfoVo> findAllPage(HttpServletRequest request, String name) {
		return zhidaoInfoDao.findByName(name);
    }

}
