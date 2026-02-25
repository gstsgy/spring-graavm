package com.gstsgy.permission.bean.view;


import com.gstsgy.base.bean.enums.BooleanEnum;
import com.gstsgy.permission.bean.db.Menu;
import lombok.Data;

import java.util.List;

/**
 * @ClassName MenuTreeVO
 * @Description TODO 菜单结构
 * @Author guyue
 * @Date 2020/10/26 下午3:23
 **/
@Data
public class MenuTreeVO {
    // 默认两级菜单   模块->页面
    // 主页 -模块-页面
    private Long id;   //菜单id
    private String path;   //菜单路径
    private String name;   //菜单标题
    private String posi;   //菜单实体路径（动态路由）
    private BooleanEnum isLeaf;   //是否叶子菜单
    private String icon;   //模块图标
    private String parentName;//父级标题
    private Long parentId;
    private Integer order;
    private List<MenuTreeVO> children;

    public MenuTreeVO(Menu menuDO) {
        this.id = menuDO.getId();
        this.path = menuDO.getPath();
        this.name = menuDO.getName();
        this.posi = menuDO.getPosi();
        this.isLeaf = menuDO.getIsLeaf();
        this.icon=menuDO.getIco();
        this.parentId = menuDO.getParentId();
        this.order = menuDO.getSeq();
    }

    public MenuTreeVO(){}
}
