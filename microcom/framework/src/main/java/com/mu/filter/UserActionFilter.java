package com.mu.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author MUZUKI
 * @Classname UserActionFilter
 * @Description TODO
 * @Date 2023/5/24 12:16
 */

@Slf4j
@WebFilter(filterName = "UserActionFilter")
public class UserActionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化操作
        log.info("-----  进入  Filter init 方法  -----");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        // 获取请求URL
//        String url = httpRequest.getRequestURI();
//
//        String[] urlArr = url.split("/");
//        // 用户对评论的操作
//        if ("vlogcomment".equalsIgnoreCase(urlArr[1])){
//            // 用户查看评论
//            if ("findChildren".equalsIgnoreCase(urlArr[2])) {
//                // 记录用户查看评论的行为
//                // TODO:
//            }
//            // 用户添加评论
//            if ("save".equalsIgnoreCase(urlArr[2])) {
//                // 记录用户添加评论的行为
//                // TODO:
//                VlogComment vlogComment = (VlogComment) request.getAttribute("vlogComment");
//                BufferedReader reader = request.getReader();
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line);
//                }
//                String requestBody = sb.toString();
//                request.getParameter("vlogComment");
//            }
//        }


        // 记录用户行为
        // ...
        log.info("-----  进入  Filter doFilter 方法  -----");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 销毁操作
        log.info("-----  进入  Filter destroy 方法  -----");
    }
}