package com.gstsgy.permission.service.impl;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.base.bean.enums.BooleanEnum;
import com.gstsgy.permission.bean.db.Menu;
import com.gstsgy.permission.bean.view.MenuVO;
import com.gstsgy.permission.repository.MenuRepository;
import com.gstsgy.permission.service.MenuService;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, MenuRepository> implements MenuService {
    @Override
    public ResponseBean GetMenuTree(Integer type) {
        // 1. 一次性查出该类型下的所有菜单
        List<Menu> allMenus = (type == null) ? repository.findAll() : repository.findAllByType(type);

        // 2. 转换为 VO 并用 Map 存储，方便查找
        Map<Long, MenuVO> lookup = allMenus.stream()
                .map(MenuVO::new)
                .collect(Collectors.toMap(MenuVO::getId, it -> it));

        List<MenuVO> rootNodes = new ArrayList<>();

        // 3. 建立父子关系
        for (Menu menu : allMenus) {
            MenuVO current = lookup.get(menu.getId());
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                rootNodes.add(current); // 顶级节点
            } else {
                MenuVO parent = lookup.get(menu.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) parent.setChildren(new ArrayList<>());
                    parent.getChildren().add(current);
                }
            }
        }
        return ResponseBean.getSuccess(rootNodes);
    }
}