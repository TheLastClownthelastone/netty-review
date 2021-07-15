package com.pt.eventBus.custom;

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


    public static PtBus getInstance() {
        return new PtBus();
    }

    public static PtBusBuild getBuilder(){
        return PtBusBuild.getInstance();
    }

    private PtBus() {

    }

    public PtBus(int a, int b, long c, TimeUnit d, LinkedBlockingDeque e) {
        this.executor = new ThreadPoolExecutor(a, b, c, d, e);
        // 增加守护线程用来关闭当前线程,在程序终止的时候触发
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            this.executor.shutdownNow();
        }));
    }

    /**
     * 订阅事件
     *
     * @param objects
     */
    @Deprecated
    public void send(Object... objects) {
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
            new Thread(() -> {
                try {
                    method.invoke(o, objects);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }, "【PtBus】").start();
        } else {
            // 主线程执行
            try {
                method.invoke(o, objects);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 利用线程池驱动
     *
     * @param params
     */
    public void sendPool(Object... params) {
        // 如果当前线程池没有值得话不往下走了
        if (this.executor == null) {
            Consumer consumer = System.err::println;
            consumer.accept("线程池为进行初始化");
            return;
        }
        // 将参数进行类型转换
        List<? extends Class<?>> collect = Arrays.stream(params).map(o -> o.getClass()).collect(Collectors.toList());
        // 所有参数的集合
        Object[] paramTypes = collect.toArray();

        List<MethodVo> methodVoList = methodVos.stream().filter(methodVo -> methodVo.check(paramTypes)).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(methodVoList)) {
            Consumer consumer = System.err::println;
            consumer.accept("无事件进行绑定....");
            return;
        }

        methodVoList.forEach(methodVo -> {
            Object o = methodVo.getO();
            Method method = methodVo.getMethod();
            try {
                if (ThreadMode.ASYNC.equals(methodVo.getThreadMode())) {
                    this.executor.execute(()->{
                        try {
                            method.invoke(o,params);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    });
                }else {
                    method.invoke(o,params);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });


    }

    /**
     * 将监听器进行注册
     *
     * @param o
     */
    public void register(Object o) {
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


//    /**
//     * 事件驱动构造器
//     */
//      final class PtBusBuild {
//
//        private PtBusBuild (){
//
//        }
//
//
//        /**
//         * 工作线程数
//         */
//        private int corePoolSize;
//        /**
//         * 最大线程数量
//         */
//        private int maxPoolSize;
//
//        /**
//         * 最大超时时间
//         */
//        private long keepAliveTime;
//
//        /**
//         * 等待时间单位
//         */
//        private TimeUnit unit;
//
//        private  final int COREPOOLSIZE_DEAFALUT = 2;
//        private  final int MAXPOOLSIZE_DEAFALUT = 5;
//        private  final long KEEPALIVETIME_DEAFALUT = 3;
//        private  final TimeUnit UNIT_DEAFALUT = TimeUnit.SECONDS;
//        private  final LinkedBlockingDeque LINKEDBLOCKINGDEQUE_DEAFALUT = new LinkedBlockingDeque(5);
//
//
//        private LinkedBlockingDeque linkedBlockingDeque;
//
//
//        public PtBusBuild corePoolSize(int corePoolSize) {
//            this.corePoolSize = corePoolSize;
//            return this;
//        }
//
//        public PtBusBuild maxPoolSize(int corePoolSize) {
//            this.maxPoolSize = maxPoolSize;
//            return this;
//        }
//
//        public PtBusBuild keepAliveTime(long keepAliveTime) {
//            this.keepAliveTime = keepAliveTime;
//            return this;
//        }
//
//        public PtBusBuild unit(TimeUnit unit) {
//            this.unit = unit;
//            return this;
//        }
//
//        public PtBusBuild linkedBlockingDeque(LinkedBlockingDeque linkedBlockingDeque) {
//            this.linkedBlockingDeque = linkedBlockingDeque;
//            return this;
//        }
//
//
//        public PtBus build() {
//            return new PtBus(this.corePoolSize == 0 ? COREPOOLSIZE_DEAFALUT : this.corePoolSize,
//                    this.maxPoolSize == 0 ? MAXPOOLSIZE_DEAFALUT : this.maxPoolSize,
//                    this.keepAliveTime == 0L ? KEEPALIVETIME_DEAFALUT : this.keepAliveTime,
//                    this.unit == null ? UNIT_DEAFALUT : this.unit,
//                    this.linkedBlockingDeque == null ? LINKEDBLOCKINGDEQUE_DEAFALUT : this.linkedBlockingDeque);
//        }
//    }


}
