package com.example.dao;

import com.example.entity.StudentInfo;
import com.example.vo.StudentInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StudentInfoDao extends Mapper<StudentInfo> {
    List<StudentInfoVo> findByName(@Param("name") String name);
    
    int checkRepeat(@Param("column") String column, @Param("value") String value, @Param("id") Long id);
    StudentInfoVo findByUsername(String username);
    Integer count();
}
