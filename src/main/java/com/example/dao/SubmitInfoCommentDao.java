package com.example.dao;

import com.example.entity.SubmitInfoComment;
import com.example.vo.SubmitInfoCommentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SubmitInfoCommentDao extends Mapper<SubmitInfoComment> {
    List<SubmitInfoCommentVo> findAllVo(@Param("name") String name);
    List<SubmitInfoCommentVo> findByForeignId (@Param("id") Long id, @Param("parentId") Long parentId);
}
