package com.gstsgy.permission.service.impl;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.permission.bean.db.FormBtn;
import com.gstsgy.permission.bean.db.Menu;
import com.gstsgy.permission.bean.db.RoleMenu;
import com.gstsgy.permission.bean.db.UserRole;
import com.gstsgy.permission.bean.view.MenuTreeVO;
import com.gstsgy.permission.repository.FormBtnRepository;
import com.gstsgy.permission.repository.MenuRepository;
import com.gstsgy.permission.repository.RoleMenuRepository;
import com.gstsgy.permission.repository.UserRoleRepository;
import com.gstsgy.permission.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu, RoleMenuRepository> implements RoleMenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private FormBtnRepository formBtnRepository;
    @Override
    public ResponseBean getAllMenuTree(Integer type) {
        return null;
    }

//    @Override
//    public ResponseBean getAllMenuTree(Integer type, Long userId) {
//        if (userId == 1) {
//            //查询模块
//            List<MenuTreeVO> tree = menuRepository.findAllByTypeAndParentId( type,null).stream().sorted(Comparator.comparing(Menu::getSeq)).
//                    map(MenuTreeVO::new).
//                    collect(Collectors.toList());
//            // 二级菜单
//            tree.forEach(item -> item.setChildren(menuRepository.findAllByTypeAndParentId(type,item.getId()).
//                    stream().sorted(Comparator.comparing(Menu::getSeq)).map(MenuTreeVO::new).peek(it -> it.setParentName(item.getName())).
//                    collect(Collectors.toList())));
//            return ResponseBean.getSuccess(tree);
//        }
//        // 查询所有菜单并缓存
//
//        List<Menu> allMenus = menuRepository.findAllByType(type);
//
//        Map<Long, Menu> mapMenus = allMenus.stream().collect(Collectors.toMap(Menu::getId, it -> it));
//
//
//        // 1  通过用户 查询角色
//        List<UserRole> list = userRoleRepository.findByUserId(userId);
//        List<FormBtn> menuDOS = list.stream().flatMap(it -> this.repository.findByRoleId(it.getRoleId()).stream()).
//                map(i->formBtnRepository.findById(i.getMenuId()).orElse(null)).toList();
//        Map<Long, List<MenuTreeVO>> map =  menuDOS.stream().map(FormBtn::getFormId).distinct().map(mapMenus::get).filter(Objects::nonNull).
//                map(MenuTreeVO::new).collect(Collectors.groupingBy(MenuTreeVO::getParentId));
//
//        List<MenuTreeVO> menuIdlist = map.keySet().stream().filter(Objects::nonNull).map(mapMenus::get).filter(Objects::nonNull).
//                sorted(Comparator.comparingInt(Menu::getSeq)).
//                map(MenuTreeVO::new).
//                peek(item -> item.setChildren(map.get(item.getId()).stream().sorted(Comparator.comparingInt(MenuTreeVO::getOrder)).peek(it -> it.setParentName(item.getName())).collect(Collectors.toList()))).collect(Collectors.toList());
//        return ResponseBean.getSuccess(menuIdlist);
//    }

    @Override
    public ResponseBean getAllMenuTree(Integer type, Long userId) {
        // 1. 一次性获取该类型下的所有菜单，减少数据库交互
        List<Menu> allMenus = menuRepository.findAllByType(type);

        List<MenuTreeVO> result;
        if (userId == 1) {
            // 超级管理员：直接基于全量数据构建树
            result = buildTree(allMenus);
        } else {
            // 普通用户：先筛选权限，再构建树
            // 1. 获取角色关联的菜单ID
            List<Long> roleIds = userRoleRepository.findByUserId(userId).stream()
                    .map(UserRole::getRoleId).collect(Collectors.toList());

            List<Long> authorizedBtnsMenuIds = repository.findByRoleIdIn(roleIds).stream()
                    .map(RoleMenu::getMenuId).distinct().toList();
            List<Long> authorizedMenuIds = formBtnRepository.findFormBtnsByIdIn(authorizedBtnsMenuIds)
                    .stream().map(FormBtn::getFormId).distinct().toList();

            // 2. 过滤出有权访问的菜单
            List<Menu> userMenus = allMenus.stream()
                    .filter(m -> authorizedMenuIds.contains(m.getId()))
                    .collect(Collectors.toList());
            // 这里写死三级菜单
            List<Long> menuIds = userMenus.stream().map(Menu::getParentId).distinct().toList();
            List<Menu> firstMenus = allMenus.stream()
                    .filter(m -> menuIds.contains(m.getId()))
                    .toList();
            userMenus.addAll(firstMenus);
            result = buildTree(userMenus);
        }
        return ResponseBean.getSuccess(result);
    }

    /**
     * 内存中构建菜单树（通用递归/分组逻辑）
     */
    private List<MenuTreeVO> buildTree(List<Menu> menus) {
        // 将菜单按 ParentId 分组缓存到 Map，避免递归时重复遍历 List
        Map<Long, List<Menu>> groupByParent = menus.stream()
                .collect(Collectors.groupingBy(m -> m.getParentId() == null ? 0L : m.getParentId()));

        return internalBuild(groupByParent, 0L, null);
    }

    private List<MenuTreeVO> internalBuild(Map<Long, List<Menu>> map, Long pid, String pName) {
        List<Menu> children = map.getOrDefault(pid, List.of());
        return children.stream()
                .sorted(Comparator.comparingInt(Menu::getSeq))
                .map(m -> {
                    MenuTreeVO vo = new MenuTreeVO(m);
                    vo.setParentName(pName);
                    // 递归设置子节点
                    vo.setChildren(internalBuild(map, m.getId(), m.getName()));
                    return vo;
                })
                .collect(Collectors.toList());
    }



    @Override
    public ResponseBean getAllFuns(Integer type, Long userId) {

//        Map<Long, Menu> pages = menuRepository.findAll().stream().
//                collect(Collectors.toMap(Menu::getId, it -> it));
//
//        if (userId == 1) {
//            //查询所有三级菜单
//            List<Menu> list = menuRepository.findAll();
//
//          //  return ResponseBean.getSuccess(list.stream().collect(Collectors.toMap(it -> getLastPath(pages.get(it.getParentId()).getPath()) + "_" + getLastPath(it.getPath()), obj -> true)));
//        }
        // 1  通过用户 查询角色


//        Map map = list.stream().flatMap(item -> roleMenuMapper.
//                        selectMenuIdByRole(item.getRoleId(), type).stream()).
//                distinct().filter(Objects::nonNull).
//                collect(Collectors.toMap(it -> getLastPath(pages.get(it.getParentId()).getPath()) + "_" + getLastPath(it.getPath()), obj -> true));

        return ResponseBean.getSuccess(true);

    }
}