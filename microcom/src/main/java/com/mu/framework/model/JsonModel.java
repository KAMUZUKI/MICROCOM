package com.mu.framework.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author : MUZUKI
 * @date : 2023-03-04 21:14
 **/

@Data
@Accessors(chain = true)
public class JsonModel implements Serializable {
    /**
     * 0失败  1成功
     */
    private Integer code;
    /**
     * 当code=0时的错误信息
     */
    private String msg;
    /**
     * 当code=1时，操作的结果数据
     */
    private Object data;

//    public Integer getCode() {
//        return code;
//    }
//
//    public void setCode(Integer code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public Object getData(Article article) {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
}

