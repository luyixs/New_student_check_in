package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import com.example.dao.YikatongInfoDao;
import com.example.entity.Account;
import com.example.entity.YikatongInfo;
import com.example.vo.YikatongInfoVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class YikatongInfoService {

    @Resource
    private YikatongInfoDao yikatongInfoDao;

    public YikatongInfo add(YikatongInfo info) {
    yikatongInfoDao.insertSelective(info);
        return info;
    }

    public void delete(Long id) {
        yikatongInfoDao.deleteByPrimaryKey(id);
    }

    public void update(YikatongInfo info) {
        yikatongInfoDao.updateByPrimaryKeySelective(info);
    }

    public YikatongInfo findById(Long id) {
        return yikatongInfoDao.selectByPrimaryKey(id);
    }

    public YikatongInfo findByReserveId(Long id, Long parentId) {
        List<YikatongInfoVo> list = yikatongInfoDao.findByReserveId(id, parentId);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public PageInfo<YikatongInfoVo> findPage(Integer pageNum, Integer pageSize, Integer flag, HttpServletRequest request) {
        Account account = (Account) request.getSession().getAttribute("user");
        if (account == null) {
            return PageInfo.of(new ArrayList<>());
        }
        Integer level = account.getLevel();
        PageHelper.startPage(pageNum, pageSize);
        List<YikatongInfoVo> info;
        if (1 == flag) {
            info = (1 == level) ? yikatongInfoDao.findByPublishId(0L) : yikatongInfoDao.findByPublishId(account.getId());
        } else {
            info = (1 == level) ? yikatongInfoDao.findByReserveId(0L, null) : yikatongInfoDao.findByReserveId(account.getId(), null);
        }
        return PageInfo.of(info);
    }

    public PageInfo<YikatongInfoVo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<YikatongInfoVo> info = yikatongInfoDao.findAllReserve();
        return PageInfo.of(info);
    }

    public List<YikatongInfoVo> findAll() {
        return yikatongInfoDao.findAllReserve();
    }
}
