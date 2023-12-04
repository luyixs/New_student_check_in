package com.example.vo;

import com.example.entity.ZhidaoInfoComment;
import java.util.List;

public class ZhidaoInfoCommentVo extends ZhidaoInfoComment {

    private String foreignName;

    private List<ZhidaoInfoCommentVo> children;

    public List<ZhidaoInfoCommentVo> getChildren() {
        return children;
    }

    public void setChildren(List<ZhidaoInfoCommentVo> children) {
        this.children = children;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }
}