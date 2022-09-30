package cn.ewsd.system.dto;

import java.util.List;

public class TreeNode {

    private Integer id;

    private String text;

    private List<TreeNode> children;

    public TreeNode() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public TreeNode(Integer id, String text) {
        super();
        this.id = id;
        this.text = text;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

}