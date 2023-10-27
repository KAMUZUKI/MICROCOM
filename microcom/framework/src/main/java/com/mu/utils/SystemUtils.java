package com.mu.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author : MUZUKI
 * @date : 2023-03-27 15:59
 **/

public class SystemUtils {
    /**
     * 实体类对象转URL参
     * @param t 实体类对象
     * @param callSuper 是否转换父类成员
     * @param <T> 实体类泛型
     * @return a=1&b=2
     */
    public static <T> String entityToUrlParam(T t,boolean callSuper){
        // URL 参数存储器
        StringBuffer urlParam = new StringBuffer();
        //扩展转换父类成员功能
        entitySuperclassToUrlParam(t, t.getClass(),callSuper,urlParam);
        if(urlParam.length()>0){
            //去除最后一个&字符
            urlParam.deleteCharAt(urlParam.length() - 1);
        }
        return urlParam.toString();
    }

    /**
     * 实体类对象转URL参
     * @param t 实体类对象
     * @param clazz 实体类类型
     * @param callSuper 是否转换父类成员
     * @param urlParam URL 参数存储器
     * @param <T> 实体类泛型
     * @return a=1&b=2
     */
    @Deprecated
    public static <T> void entitySuperclassToUrlParam(T t,Class clazz,boolean callSuper,StringBuffer urlParam){
        //如果实体类对象为Object类型，则不处理
        if(!clazz.equals(Object.class)) {
            //获取实体类对象下的所有成员，并保存到 URL 参数存储器中
            Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
                //设置可以操作私有成员
                field.setAccessible(true);
                try {
                    //获取成员值
                    Object value = field.get(t);
                    //成员为 serialVersionUID 时，则不处理
                    if(field.getName().equalsIgnoreCase("serialVersionUID")){
                        return;
                    }
                    //成员值为 Null 时，则不处理
                    if (Objects.nonNull(value)) {
                        urlParam.append(field.getName()).append("=").append(value).append("&");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            //是否转换父类成员
            if(callSuper){
                //获取父类类型
                clazz = clazz.getSuperclass();
                //递归调用，获取父类的处理结果
                entitySuperclassToUrlParam(t,clazz,callSuper,urlParam);
            }
        }
    }

    public static String getClientIP(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            //打印所有头信息
            String s = headerNames.nextElement();
            String header = request.getHeader(s);
        }
        String unknown = "unknown";
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
