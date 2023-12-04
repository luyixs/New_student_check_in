package com.example.service;

import com.example.exception.CustomException;
import com.example.dao.JiaofeiInfoCommentDao;
import org.springframework.stereotype.Service;
import com.example.entity.JiaofeiInfoComment;
import com.example.vo.JiaofeiInfoCommentVo;
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
public class JiaofeiInfoCommentService {

    @Resource
    private JiaofeiInfoCommentDao jiaofeiInfoCommentDao;

    public JiaofeiInfoComment add(JiaofeiInfoComment commentInfo, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "请先登录！");
        }
        commentInfo.setName(user.getName());
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        jiaofeiInfoCommentDao.insertSelective(commentInfo);
        return commentInfo;
    }

    public void delete(Long id) {
        jiaofeiInfoCommentDao.deleteByPrimaryKey(id);
    }

    public void update(JiaofeiInfoComment commentInfo) {
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        jiaofeiInfoCommentDao.updateByPrimaryKeySelective(commentInfo);
    }

    public JiaofeiInfoComment findById(Long id) {
        return jiaofeiInfoCommentDao.selectByPrimaryKey(id);
    }

    public List<JiaofeiInfoCommentVo> findAll() {
        return jiaofeiInfoCommentDao.findAllVo(null);
    }

    public PageInfo<JiaofeiInfoCommentVo> findPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JiaofeiInfoCommentVo> all = jiaofeiInfoCommentDao.findAllVo(name);
        return PageInfo.of(all);
    }

    public List<JiaofeiInfoCommentVo> findByForeignId (Long id) {
        List<JiaofeiInfoCommentVo> all = jiaofeiInfoCommentDao.findByForeignId(id, 0L);
        for (JiaofeiInfoCommentVo reserveInfoVo : all) {
            Long parentId = reserveInfoVo.getId();
            List<JiaofeiInfoCommentVo> children = new ArrayList<>(jiaofeiInfoCommentDao.findByForeignId(id, parentId));
            reserveInfoVo.setChildren(children);
        }
        return all;
    }
}
