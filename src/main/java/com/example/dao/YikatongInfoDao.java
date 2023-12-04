package com.example.dao;

import com.example.entity.YikatongInfo;
import com.example.vo.YikatongInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface YikatongInfoDao extends Mapper<YikatongInfo> {
    List<YikatongInfoVo> findByPublishId(@Param("id") Long id);
    List<YikatongInfoVo> findByReserveId(@Param("id") Long id, @Param("parentId") Long parentId);
    List<YikatongInfoVo> findAll();
    List<YikatongInfoVo> findAllReserve();
}