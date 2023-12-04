package com.example.service;

import com.example.exception.CustomException;
import com.example.dao.SubmitInfoCommentDao;
import org.springframework.stereotype.Service;
import com.example.entity.SubmitInfoComment;
import com.example.vo.SubmitInfoCommentVo;
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
public class SubmitInfoCommentService {

    @Resource
    private SubmitInfoCommentDao submitInfoCommentDao;

    public SubmitInfoComment add(SubmitInfoComment commentInfo, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "请先登录！");
        }
        commentInfo.setName(user.getName());
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        submitInfoCommentDao.insertSelective(commentInfo);
        return commentInfo;
    }

    public void delete(Long id) {
        submitInfoCommentDao.deleteByPrimaryKey(id);
    }

    public void update(SubmitInfoComment commentInfo) {
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        submitInfoCommentDao.updateByPrimaryKeySelective(commentInfo);
    }

    public SubmitInfoComment findById(Long id) {
        return submitInfoCommentDao.selectByPrimaryKey(id);
    }

    public List<SubmitInfoCommentVo> findAll() {
        return submitInfoCommentDao.findAllVo(null);
    }

    public PageInfo<SubmitInfoCommentVo> findPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SubmitInfoCommentVo> all = submitInfoCommentDao.findAllVo(name);
        return PageInfo.of(all);
    }

    public List<SubmitInfoCommentVo> findByForeignId (Long id) {
        List<SubmitInfoCommentVo> all = submitInfoCommentDao.findByForeignId(id, 0L);
        for (SubmitInfoCommentVo reserveInfoVo : all) {
            Long parentId = reserveInfoVo.getId();
            List<SubmitInfoCommentVo> children = new ArrayList<>(submitInfoCommentDao.findByForeignId(id, parentId));
            reserveInfoVo.setChildren(children);
        }
        return all;
    }
}
