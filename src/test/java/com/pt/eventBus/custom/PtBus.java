package com.pt.eventBus.custom;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author nate-pt
 * @date 2021/7/15 10:04
 * @Since 1.8
 * @Description 实现EventBus功能
 */
public class PtBus {

    private List<MethodVo> methodVos = new CopyOnWriteArrayList<>();

    /**
     * 内置线程池
     */
    private ThreadPoolExecutor executor;






    public static PtBus getInstance(){
        return new PtBus();
    }


    private PtBus(){

    }

    /**
     * 订阅事件
     * @param objects
     */
    public void send(Object... objects){
        Object[] paramTypes = new Object[objects.length];
        for (int i = 0; i < objects.length; i++) {
            paramTypes[i] = objects[i].getClass();
        }
        Optional<MethodVo> any = methodVos.stream().filter(methodVo -> methodVo.check(paramTypes)).findAny();
        if (!any.isPresent()) {
            Consumer consumer = System.err::println;
            consumer.accept("无订阅事件");
            return;
        }
        MethodVo methodVo = any.get();
        // 判断线程模型
        ThreadMode threadMode = methodVo.getThreadMode();
        // 异步执行
        Object o = methodVo.getO();
        Method method = methodVo.getMethod();
        if (ThreadMode.ASYNC.equals(threadMode)) {
            new Thread(()->{
                try {
                    method.invoke(o,objects);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            },"【PtBus】").start();
        }else {
            // 主线程执行
            try {
                method.invoke(o,objects);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 利用线程池驱动
     * @param params
     */
    public void sendPool(Object... params){
        // 将参数进行类型转换
    }

    /**
     * 将监听器进行注册
     * @param o
     */
    public void register(Object o){
        Class<?> aClass = o.getClass();

        // 监听器中含有WokerShecle注解的方法进行解析
        List<Method> methods = Arrays.stream(aClass.getMethods()).
                filter(method -> method.isAnnotationPresent(WokerShecle.class)).
                collect(Collectors.toList());

        if (CollectionUtils.isEmpty(methods)) {
            Consumer consumer = System.err::println;
            consumer.accept("监听器中无事件监听..");
            return;
        }

        methods.forEach(method -> {
            MethodVo build = MethodVo.builder().
                    o(o)
                    .param(method.getParameterTypes())
                    .threadMode(method.getAnnotation(WokerShecle.class).threadmode())
                    .method(method)
                    .build();
            methodVos.add(build);
        });
    }






}
