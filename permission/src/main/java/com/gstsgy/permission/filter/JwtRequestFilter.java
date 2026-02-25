package com.gstsgy.permission.filter;


import java.io.IOException;
import java.util.Objects;

import com.gstsgy.base.bean.dto.ResponseBean;
import com.gstsgy.base.utils.SpringUtils;
import com.gstsgy.permission.conf.IpConfCache;
import com.gstsgy.permission.service.RoleMenuService;
import com.gstsgy.permission.service.UserRoleService;
import com.gstsgy.permission.utils.AuthWhiteUtil;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstsgy.base.utils.WebUtils;
import com.gstsgy.permission.utils.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtRequestFilter extends OncePerRequestFilter implements OrderedFilter{

    ObjectMapper objectMapper = new ObjectMapper();
    UserRoleService userRoleService;

    RoleMenuService roleMenuService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        WebUtils.setUserId(-1L);
        if(IpConfCache.getIpBlacklist().contains(WebUtils.getRequestIp())){
            ResponseBean responseBean = ResponseBean.getNoLogin("Token不存在");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf8");
            response.getWriter().write(objectMapper.writeValueAsString(responseBean));
            return;
        };
        // HttpServletResponse res = response;
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT,OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "*");
        if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
            ResponseBean responseBean = ResponseBean.getSuccess(true);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf8");
            response.getWriter().write(objectMapper.writeValueAsString(responseBean));
            return;
        }


        if (!AuthWhiteUtil.isOpen) {
            chain.doFilter(request, response);
            return;
        }

        String url = request.getRequestURI().substring(request.getContextPath().length());
        String token = request.getHeader("token");
        if (AuthWhiteUtil.matches(url)) {
            chain.doFilter(request, response);
            return;
        }

        if (!StringUtils.hasText(token)) {

            ResponseBean responseBean = ResponseBean.getNoLogin("Token不存在");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf8");
            response.getWriter().write(objectMapper.writeValueAsString(responseBean));
            return;
        }
        try {
            JWTUtil.validateToken(token);
        }catch (Exception exception){
            ResponseBean responseBean = ResponseBean.getNoLogin(exception.getMessage());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf8");
            response.getWriter().write(objectMapper.writeValueAsString(responseBean));
            return;
        }
        if(WebUtils.getUserId().equals(1L)){
            chain.doFilter(request, response);
            return;
        }

        // interface kakong
        if(userRoleService == null){
            userRoleService = SpringUtils.getBean(UserRoleService.class);
        }
        if(roleMenuService == null){
            roleMenuService = SpringUtils.getBean(RoleMenuService.class);
        }
        // 获取完整 URL（包含查询参数）

//        String method = request.getMethod();
//        var roles = userRoleService.queryRoleByUser(WebUtils.getUserId());
//        if (roles == null || roles.isEmpty()) {
//            ResponseBean responseBean = ResponseBean.getNoLogin("Token不存在");
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json;charset=utf8");
//            response.getWriter().write(objectMapper.writeValueAsString(responseBean));
//            return;
//        }
//        var interfaces =roles.stream().flatMap(it->roleMenuService.getAllInterfaceByRole(it.getRoleId()).stream()).distinct().toList();
//        var res=interfaces.stream().filter(it-> url.contains(it.getUrl())).
//                filter(it->Objects.equals(method,it.getMethod())).toList();
//        if(res.isEmpty()){
//            ResponseBean responseBean = ResponseBean.getNoLogin("Token不存在");
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json;charset=utf8");
//            response.getWriter().write(objectMapper.writeValueAsString(responseBean));
//            return;
//        }

        chain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return 0;
    }
}




