package com.example.service;

import com.example.exception.CustomException;
import com.example.dao.ZhidaoInfoCommentDao;
import org.springframework.stereotype.Service;
import com.example.entity.ZhidaoInfoComment;
import com.example.vo.ZhidaoInfoCommentVo;
import com.example.entity.Account;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ZhidaoInfoCommentService {

    @Resource
    private ZhidaoInfoCommentDao zhidaoInfoCommentDao;

    public ZhidaoInfoComment add(ZhidaoInfoComment commentInfo, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "请先登录！");
        }
        commentInfo.setName(user.getName());
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        zhidaoInfoCommentDao.insertSelective(commentInfo);
        return commentInfo;
    }

    public void delete(Long id) {
        zhidaoInfoCommentDao.deleteByPrimaryKey(id);
    }

    public void update(ZhidaoInfoComment commentInfo) {
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        zhidaoInfoCommentDao.updateByPrimaryKeySelective(commentInfo);
    }

    public ZhidaoInfoComment findById(Long id) {
        return zhidaoInfoCommentDao.selectByPrimaryKey(id);
    }

    public List<ZhidaoInfoCommentVo> findAll() {
        return zhidaoInfoCommentDao.findAllVo(null);
    }

    public PageInfo<ZhidaoInfoCommentVo> findPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ZhidaoInfoCommentVo> all = zhidaoInfoCommentDao.findAllVo(name);
        return PageInfo.of(all);
    }

    public List<ZhidaoInfoCommentVo> findByForeignId (Long id) {
        List<ZhidaoInfoCommentVo> all = zhidaoInfoCommentDao.findByForeignId(id, 0L);
        for (ZhidaoInfoCommentVo reserveInfoVo : all) {
            Long parentId = reserveInfoVo.getId();
            List<ZhidaoInfoCommentVo> children = new ArrayList<>(zhidaoInfoCommentDao.findByForeignId(id, parentId));
            reserveInfoVo.setChildren(children);
        }
        return all;
    }
}
