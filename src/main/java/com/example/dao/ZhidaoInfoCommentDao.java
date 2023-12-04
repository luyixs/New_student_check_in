package com.example.dao;

import com.example.entity.ZhidaoInfoComment;
import com.example.vo.ZhidaoInfoCommentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ZhidaoInfoCommentDao extends Mapper<ZhidaoInfoComment> {
    List<ZhidaoInfoCommentVo> findAllVo(@Param("name") String name);
    List<ZhidaoInfoCommentVo> findByForeignId (@Param("id") Long id, @Param("parentId") Long parentId);
}
