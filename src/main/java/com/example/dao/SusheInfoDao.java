package com.example.dao;

import com.example.entity.SusheInfo;
import com.example.vo.SusheInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SusheInfoDao extends Mapper<SusheInfo> {
    List<SusheInfoVo> findByName(@Param("name") String name);
    
    
    
    Integer count();
}
