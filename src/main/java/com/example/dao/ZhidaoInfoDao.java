package com.example.dao;

import com.example.entity.ZhidaoInfo;
import com.example.vo.ZhidaoInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ZhidaoInfoDao extends Mapper<ZhidaoInfo> {
    List<ZhidaoInfoVo> findByName(@Param("name") String name);
    
    
    
}
