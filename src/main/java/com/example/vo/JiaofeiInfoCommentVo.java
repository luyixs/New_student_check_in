package com.example.vo;

import com.example.entity.JiaofeiInfoComment;
import java.util.List;

public class JiaofeiInfoCommentVo extends JiaofeiInfoComment {

    private String foreignName;

    private List<JiaofeiInfoCommentVo> children;

    public List<JiaofeiInfoCommentVo> getChildren() {
        return children;
    }

    public void setChildren(List<JiaofeiInfoCommentVo> children) {
        this.children = children;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }
}