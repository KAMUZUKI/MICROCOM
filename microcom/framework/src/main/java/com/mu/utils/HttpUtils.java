package com.mu.utils;

import com.google.gson.GsonBuilder;
import com.mu.model.JsonModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author : MUZUKI
 * @date : 2023-03-04 16:41
 **/

public class HttpUtils {
    public static void writeJson(JsonModel jm, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        //加了serializeNulls,会将空值也序列化
        GsonBuilder gson=new GsonBuilder().serializeNulls();
        //Gson gson = new Gson();
        //利用Gson框架将json对象转为json格式的字符串
        String jsonString = gson.create().toJson(jm);
        out.println(jsonString);
        out.flush();
    }

    public static <T> T parseRequestToT(HttpServletRequest request, Class<T> cls){
        //1.通过class字节码创建一个对象
        T obj = null;
        try {
            obj = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //2.从request中取出所有的属性，存到这个obj中
        Map<String,String[]> map=request.getParameterMap();
        //3.取出cls中所有的属性，看有几个和map中的键相同，相同则调用cls中的set方法，注入值
        Method[] ms = cls.getMethods();
        for(Method m:ms){
            if(m.getName().startsWith("set")){
                //这个m是一个setXxx（），取出Xxx是什么，根据Xxx，在 map.get（Xxx）取出这个值
                String fieldName=getFieldName(m);
                String[] values=map.get(fieldName);
                String v=null;
                if(values!=null){
                    if(values.length > 1){
                        StringBuffer sb = new StringBuffer();
                        for(String s : values){
                            sb.append(s + ",");
                        }
                        v = sb.toString();
                    } else {
                        v = values[0];
                    }
                }
                if( v==null ){
                    continue;
                }
                //判断这个m方法的参数类型，然后将v进行类型转换
                String methodParameterTypeName=m.getParameterTypes()[0].getTypeName();
                if("java.lang.Integer".equals(methodParameterTypeName) || "int".equals(methodParameterTypeName)){
                    Integer va = Integer.parseInt(v);
                    //然后在调用setXxx（），将值注入
                    try {
                        m.invoke(obj,va);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }else if("java.lang.Double".equals(methodParameterTypeName) || "double".equals(methodParameterTypeName)){
                    Double va = Double.parseDouble(v);
                    try {
                        m.invoke(obj,va);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }else if("java.lang.Float".equals(methodParameterTypeName) || "float".equals(methodParameterTypeName)){
                    Float va = Float.parseFloat(v);
                    try {
                        m.invoke(obj,va);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }else if("java.lang.Long".equals(methodParameterTypeName) || "long".equals(methodParameterTypeName)){
                    Long va = Long.parseLong(v);
                    try {
                        m.invoke(obj,va);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        m.invoke(obj,v);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return obj;
    }

    private static String getFieldName(Method setMethod){
        String fieldName=setMethod.getName().substring("set".length());
        //将fieldName的首字母改小写
        fieldName=fieldName.substring(0,1).toLowerCase()+ fieldName.substring(1);
        return fieldName;
    }
}
