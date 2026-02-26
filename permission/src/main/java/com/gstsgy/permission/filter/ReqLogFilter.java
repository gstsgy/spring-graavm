package com.gstsgy.permission.filter;

import com.gstsgy.base.utils.SpringUtils;
import com.gstsgy.base.utils.WebUtils;
import com.gstsgy.permission.bean.db.ReqLog;
import com.gstsgy.permission.conf.IpConfCache;
import com.gstsgy.permission.service.ReqLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Component
public class ReqLogFilter extends OncePerRequestFilter implements OrderedFilter{
    @Autowired
    private ReqLogService logService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (logService == null) {
            logService = SpringUtils.getBean(ReqLogService.class);
        }



        if (Objects.equals(request.getMethod(), HttpMethod.OPTIONS.name())) {
            filterChain.doFilter(request, response);
            return;
        }
        String contentType = request.getContentType();
        if (contentType != null && contentType.toUpperCase().contains("FORM-DATA")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }

        // if (!(response instanceof ContentCachingResponseWrapper)) {
        //     response = new ContentCachingResponseWrapper(response);
        // }

       // long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);

        }catch (Exception ignore){

        }
        finally {
            if(!IpConfCache.getIpWhitelist().contains(WebUtils.getRequestIp())||!Objects.equals(request.getMethod(), HttpMethod.GET.name())){
                    //1. 记录日志
                ReqLog interfaceLogDO = new ReqLog();
                interfaceLogDO.setInsertId(WebUtils.getUserId());
                if(interfaceLogDO.getInsertId() == null){
                    interfaceLogDO.setInsertId(0L);
                }

                interfaceLogDO.setIp(WebUtils.getRequestIp());

                // 获取完整 URL（包含查询参数）
                String fullUrl = request.getRequestURI().substring(request.getContextPath().length());
                String queryString = request.getQueryString();
                if (queryString != null) {
                    fullUrl += "?" + queryString;
                }
                interfaceLogDO.setUrl(fullUrl);  // 替换原来的 request.getRequestURI()

                //interfaceLogDO.setHeader(getHeaders(request).toString());

                interfaceLogDO.setData(getRequestBody(request));

                interfaceLogDO.setMethod(request.getMethod());

                logService.saveOne(interfaceLogDO);
            }

        }
    }

    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return 2;
    }

    private String getRequestBody(HttpServletRequest request) {
        String requestBody = "";
        ContentCachingRequestWrapper wrapper = org.springframework.web.util.WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            try {
                requestBody = new String(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
                //IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
            } catch (IOException e) {
                // NOOP
            }
        }
        return requestBody;
    }
     private Map<String, Object> getHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, Object> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.put(name, request.getHeader(name));
        }
        return headers;
    }

}
