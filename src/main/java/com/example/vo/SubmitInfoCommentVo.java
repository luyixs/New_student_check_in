package com.example.vo;

import com.example.entity.SubmitInfoComment;
import java.util.List;

public class SubmitInfoCommentVo extends SubmitInfoComment {

    private String foreignName;

    private List<SubmitInfoCommentVo> children;

    public List<SubmitInfoCommentVo> getChildren() {
        return children;
    }

    public void setChildren(List<SubmitInfoCommentVo> children) {
        this.children = children;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }
}