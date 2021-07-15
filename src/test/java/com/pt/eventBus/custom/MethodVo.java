package com.pt.eventBus.custom;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author nate-pt
 * @date 2021/7/15 11:04
 * @Since 1.8
 * @Description
 */
@Data
@Builder
public class MethodVo {

    /**
     * 启动方法原对象
     */
    private Object o;
    /**
     * 方法形参
     */
    private Object[] param;
    /**
     * 执行方式
     */
    private ThreadMode threadMode;

    private Method method;


    /**
     * 判断类型是否相同
     * @param params
     * @return
     */
    public boolean check(Object[] params){
        return Arrays.equals(this.param, params);
    }
}
