package com.gstsgy.permission.bean.view;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @ClassName DeptTre
 * @Description TODO
 * @Author guyue
 * @Date 2020/10/19 上午10:47
 **/
@Data
@Schema(description = "部门树结构")
public class TreeNodeVO {

    private Long key;
    private String title;
    private Boolean isLeaf;
    private List<TreeNodeVO> children; //      子节点属性数组


    public TreeNodeVO(Long id,  String label) {
        this.title = label;
        this.key = id;
    }

    public TreeNodeVO(Long id,  String label,boolean isLeaf) {
        this.title = label;
        this.key = id;
        this.isLeaf = isLeaf;
    }

    public TreeNodeVO(){}
}
