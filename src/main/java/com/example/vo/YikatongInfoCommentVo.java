package com.example.vo;

import com.example.entity.YikatongInfoComment;
import java.util.List;

public class YikatongInfoCommentVo extends YikatongInfoComment {

    private String foreignName;

    private List<YikatongInfoCommentVo> children;

    public List<YikatongInfoCommentVo> getChildren() {
        return children;
    }

    public void setChildren(List<YikatongInfoCommentVo> children) {
        this.children = children;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }
}