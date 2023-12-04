package com.example.dao;

import com.example.entity.JiaofeiInfo;
import com.example.vo.JiaofeiInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface JiaofeiInfoDao extends Mapper<JiaofeiInfo> {
    List<JiaofeiInfoVo> findByPublishId(@Param("id") Long id);
    List<JiaofeiInfoVo> findByReserveId(@Param("id") Long id, @Param("parentId") Long parentId);
    List<JiaofeiInfoVo> findAll();
    List<JiaofeiInfoVo> findAllReserve();
}