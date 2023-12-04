package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import com.example.dao.JiaofeiInfoDao;
import com.example.entity.Account;
import com.example.entity.JiaofeiInfo;
import com.example.vo.JiaofeiInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class JiaofeiInfoService {

    @Resource
    private JiaofeiInfoDao jiaofeiInfoDao;

    public JiaofeiInfo add(JiaofeiInfo info) {
    jiaofeiInfoDao.insertSelective(info);
        return info;
    }

    public void delete(Long id) {
        jiaofeiInfoDao.deleteByPrimaryKey(id);
    }

    public void update(JiaofeiInfo info) {
        jiaofeiInfoDao.updateByPrimaryKeySelective(info);
    }

    public JiaofeiInfo findById(Long id) {
        return jiaofeiInfoDao.selectByPrimaryKey(id);
    }

    public JiaofeiInfo findByReserveId(Long id, Long parentId) {
        List<JiaofeiInfoVo> list = jiaofeiInfoDao.findByReserveId(id, parentId);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public PageInfo<JiaofeiInfoVo> findPage(Integer pageNum, Integer pageSize, Integer flag, HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("user");
        if (account == null) {
            return PageInfo.of(new ArrayList<>());
        }
        Integer level = account.getLevel();
        PageHelper.startPage(pageNum, pageSize);
        List<JiaofeiInfoVo> info;
        if (1 == flag) {
            info = (1 == level) ? jiaofeiInfoDao.findByPublishId(0L) : jiaofeiInfoDao.findByPublishId(account.getId());
        } else {
            info = (1 == level) ? jiaofeiInfoDao.findByReserveId(0L, null) : jiaofeiInfoDao.findByReserveId(account.getId(), null);
        }
        return PageInfo.of(info);
    }

    public PageInfo<JiaofeiInfoVo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JiaofeiInfoVo> info = jiaofeiInfoDao.findAllReserve();
        return PageInfo.of(info);
    }

    public List<JiaofeiInfoVo> findAll() {
        return jiaofeiInfoDao.findAllReserve();
    }
}
