package com.example.dao;

import com.example.entity.JiaofeiInfoComment;
import com.example.vo.JiaofeiInfoCommentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface JiaofeiInfoCommentDao extends Mapper<JiaofeiInfoComment> {
    List<JiaofeiInfoCommentVo> findAllVo(@Param("name") String name);
    List<JiaofeiInfoCommentVo> findByForeignId (@Param("id") Long id, @Param("parentId") Long parentId);
}
