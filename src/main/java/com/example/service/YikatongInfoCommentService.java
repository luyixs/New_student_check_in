package com.example.service;

import com.example.exception.CustomException;
import com.example.dao.YikatongInfoCommentDao;
import org.springframework.stereotype.Service;
import com.example.entity.YikatongInfoComment;
import com.example.vo.YikatongInfoCommentVo;
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
public class YikatongInfoCommentService {

    @Resource
    private YikatongInfoCommentDao yikatongInfoCommentDao;

    public YikatongInfoComment add(YikatongInfoComment commentInfo, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("1001", "请先登录！");
        }
        commentInfo.setName(user.getName());
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        yikatongInfoCommentDao.insertSelective(commentInfo);
        return commentInfo;
    }

    public void delete(Long id) {
        yikatongInfoCommentDao.deleteByPrimaryKey(id);
    }

    public void update(YikatongInfoComment commentInfo) {
        commentInfo.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        yikatongInfoCommentDao.updateByPrimaryKeySelective(commentInfo);
    }

    public YikatongInfoComment findById(Long id) {
        return yikatongInfoCommentDao.selectByPrimaryKey(id);
    }

    public List<YikatongInfoCommentVo> findAll() {
        return yikatongInfoCommentDao.findAllVo(null);
    }

    public PageInfo<YikatongInfoCommentVo> findPage(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<YikatongInfoCommentVo> all = yikatongInfoCommentDao.findAllVo(name);
        return PageInfo.of(all);
    }

    public List<YikatongInfoCommentVo> findByForeignId (Long id) {
        List<YikatongInfoCommentVo> all = yikatongInfoCommentDao.findByForeignId(id, 0L);
        for (YikatongInfoCommentVo reserveInfoVo : all) {
            Long parentId = reserveInfoVo.getId();
            List<YikatongInfoCommentVo> children = new ArrayList<>(yikatongInfoCommentDao.findByForeignId(id, parentId));
            reserveInfoVo.setChildren(children);
        }
        return all;
    }
}
