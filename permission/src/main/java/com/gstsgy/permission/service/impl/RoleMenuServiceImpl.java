package com.gstsgy.permission.service.impl;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.base.utils.WebUtils;
import com.gstsgy.permission.bean.db.*;
import com.gstsgy.permission.bean.view.MenuTreeVO;
import com.gstsgy.permission.bean.view.TreeNodeVO;
import com.gstsgy.permission.repository.FormBtnRepository;
import com.gstsgy.permission.repository.MenuRepository;
import com.gstsgy.permission.repository.RoleMenuRepository;
import com.gstsgy.permission.repository.UserRoleRepository;
import com.gstsgy.permission.service.RoleMenuService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gstsgy.base.service.impl.BaseServiceImpl;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenu, RoleMenuRepository> implements RoleMenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    private FormBtnRepository formBtnRepository;


    @Override
    public ResponseBean getAllMenuTree(Integer type) {
        long userId = WebUtils.getUserId();
        List<TreeNodeVO> tree;

        if (userId == 1) {
            // 管理员逻辑：一次性查出所有菜单和按钮，在内存组装
            tree = buildAdminTree();
        } else {
            // 普通用户逻辑：基于权限过滤
            tree = buildUserPermissionsTree(userId);
        }

        return ResponseBean.getSuccess(tree);
    }

    /**
     * 管理员：全量加载（优化掉嵌套循环查询）
     */
    private List<TreeNodeVO> buildAdminTree() {
        // 一次性查出所有菜单
        List<Menu> allMenus = menuRepository.findAll();
        // 一次性查出所有按钮
        List<FormBtn> allBtns = formBtnRepository.findAll();

        // 1. 按 ParentId 分组菜单
        Map<Long, List<Menu>> menuByParent = allMenus.stream()
                .filter(m -> m.getParentId() != null)
                .collect(Collectors.groupingBy(Menu::getParentId));

        // 2. 按 FormId 分组按钮
        Map<Long, List<FormBtn>> btnsByPage = allBtns.stream()
                .collect(Collectors.groupingBy(FormBtn::getFormId));

        // 3. 组装顶层（模块）
        return allMenus.stream()
                .filter(m -> m.getParentId() == null)
                .map(model -> {
                    TreeNodeVO modelVO = new TreeNodeVO(model.getId(), model.getName());
                    // 组装二级（页面）
                    List<TreeNodeVO> pageVOs = menuByParent.getOrDefault(model.getId(), List.of()).stream()
                            .map(page -> {
                                TreeNodeVO pageVO = new TreeNodeVO(page.getId(), page.getName());
                                // 组装三级（按钮）
                                List<TreeNodeVO> btnVOs = btnsByPage.getOrDefault(page.getId(), List.of()).stream()
                                        .map(btn -> new TreeNodeVO(btn.getId(), btn.getBtn().getName(), true))
                                        .collect(Collectors.toList());
                                pageVO.setChildren(btnVOs);
                                return pageVO;
                            }).collect(Collectors.toList());
                    modelVO.setChildren(pageVOs);
                    return modelVO;
                }).collect(Collectors.toList());
    }

    /**
     * 普通用户：基于角色权限过滤
     */
    private List<TreeNodeVO> buildUserPermissionsTree(Long userId) {
        // 1. 获取用户所有的角色 ID
        List<Long> roleIds = userRoleRepository.findByUserId(userId).stream()
                .map(UserRole::getRoleId).toList();

        // 2. 获取这些角色拥有的所有按钮 ID（通过关联表）
        // 对应原逻辑：flatMap(it -> this.queryMenuIdByRoleId(it.getRoleId()))
        List<Long> btnIds = repository.findByRoleIdIn(roleIds).stream().map(RoleMenu::getMenuId).toList();

        // 3. 批量查询涉及到的按钮实体
        List<FormBtn> myBtns = formBtnRepository.findAllById(btnIds);

        // 4. 获取所有菜单用于内存匹配
        Map<Long, Menu> menuMap = menuRepository.findAll().stream()
                .collect(Collectors.toMap(Menu::getId, m -> m));

        // 5. 逻辑分组：按钮 -> 页面(Parent) -> 模块(Grandparent)
        Map<Long, List<FormBtn>> page2Btns = myBtns.stream()
                .collect(Collectors.groupingBy(FormBtn::getFormId));

        // 找出所有涉及到的页面级菜单
        Map<Long, List<Menu>> model2Pages = page2Btns.keySet().stream()
                .map(menuMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Menu::getParentId));

        // 6. 最终组装
        return model2Pages.keySet().stream()
                .map(menuMap::get)
                .filter(Objects::nonNull)
                .map(model -> {
                    TreeNodeVO modelVO = new TreeNodeVO(model.getId(), model.getName());
                    List<TreeNodeVO> pageVOs = model2Pages.get(model.getId()).stream()
                            .map(page -> {
                                TreeNodeVO pageVO = new TreeNodeVO(page.getId(), page.getName());
                                List<TreeNodeVO> btnVOs = page2Btns.getOrDefault(page.getId(), List.of()).stream()
                                        .map(btn -> new TreeNodeVO(btn.getId(), btn.getBtn().getName(), true))
                                        .collect(Collectors.toList());
                                pageVO.setChildren(btnVOs);
                                return pageVO;
                            }).collect(Collectors.toList());
                    modelVO.setChildren(pageVOs);
                    return modelVO;
                }).collect(Collectors.toList());
    }



    @Override
    public ResponseBean getAllInterfaceTree() {
        List<TreeNodeVO> tree = new ArrayList<>();
        List<RoleInterface> mappings =  new ArrayList<>();
        this.requestMappingHandlerMapping.getHandlerMethods().forEach((k,v)->{
            Set<RequestMethod>methodSet =  k.getMethodsCondition().getMethods();
            Set<String>urlSets =  k.getDirectPaths();
            if(urlSets.isEmpty()){
                if(k.getPathPatternsCondition()!=null){
                    var pattern = k.getPathPatternsCondition().getFirstPattern();
                    urlSets = new HashSet<>(List.of(pattern.getPatternString().replaceAll("/\\{[^}]+\\}$",""))) ;
                }
            }

            if(!methodSet.isEmpty() && !urlSets.isEmpty()){
                Class<?> controllerClass = v.getBeanType();
                String controllerName = controllerClass.getName();
                controllerName = controllerName.substring(controllerName.lastIndexOf(".") + 1);
                String jarFileName = null;
                try {
                    jarFileName = getJarFileName(controllerClass);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // permission-0.0.2.jar
                if(!Objects.equals(jarFileName,"主项目")){
                    jarFileName= jarFileName.substring(0,jarFileName.indexOf("-"));
                }
                RoleInterface roleInterfaceDO = new RoleInterface();
                roleInterfaceDO.setController(controllerName);
                roleInterfaceDO.setJar(jarFileName);
                roleInterfaceDO.setMethod( methodSet.toArray()[0].toString());
                roleInterfaceDO.setUrl(urlSets.toArray()[0].toString());
                mappings.add(roleInterfaceDO);
            }
        });
        Map<String,List<RoleInterface>> mapTemps = mappings.stream().collect(Collectors.groupingBy(RoleInterface::getJar));
        AtomicLong index = new AtomicLong();
        mapTemps.keySet().forEach(it->{
            TreeNodeVO root = new TreeNodeVO(index.getAndIncrement(),it);
            tree.add(root);
            List<RoleInterface> controllers = mapTemps.get(it);
            Map<String,List<RoleInterface>> controllersTemps = controllers.stream().collect(Collectors.groupingBy(RoleInterface::getController));
            List<TreeNodeVO> controllerTrees = new ArrayList<>();
            controllersTemps.keySet().forEach(controller->{
                TreeNodeVO contr = new TreeNodeVO(index.getAndIncrement(),controller);
                controllerTrees.add(contr);
                List<TreeNodeVO> urlTrees = new ArrayList<>();
                List<RoleInterface> urls = controllersTemps.get(controller);
                urls.forEach(url->{
                    TreeNodeVO urlTree = new TreeNodeVO(index.getAndIncrement(),url.getUrl()+"-"+url.getMethod(),true);
                    urlTrees.add(urlTree);
                });
                contr.setChildren(urlTrees);
            });
            root.setChildren(controllerTrees);
        });
        return ResponseBean.getSuccess(tree);
    }
    public static String getJarFileName(Class<?> clazz) throws Exception {
        String className = clazz.getName().replace('.', '/') + ".class";
        URL classUrl = clazz.getClassLoader().getResource(className);
        if (classUrl != null) {
            String path = classUrl.getPath();
            if (path.startsWith("file:")) {
                path = path.substring(5);
            }
            path = URLDecoder.decode(path, StandardCharsets.UTF_8);
            if (path.contains(".jar!")) {
                return new File(path.substring(0, path.indexOf(".jar!") + 4)).getName();
            } else {
                return "主项目";
            }
        } else {
            return "Class not found.";
        }
    }

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

    @Transactional
    @Override
    public void saveRoleMenu(Long roleId, List<Long> menuIds) {
        repository.deleteByRoleId(roleId);
        repository.saveAll(menuIds.stream().map(i->{
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(i);
            return roleMenu;
        }).toList());
    }
}