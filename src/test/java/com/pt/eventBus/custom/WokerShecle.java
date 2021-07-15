package com.pt.eventBus.custom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author nate-pt
 * @date 2021/7/15 10:05
 * @Since 1.8
 * @Description 在使用事件驱动表明使用异步启动还是同步驱动
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WokerShecle {
    /**
     * 默认线程模型为主线程驱动
     * @return
     */
    ThreadMode threadmode() default ThreadMode.MAIN;
}
