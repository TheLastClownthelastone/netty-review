package com.pt.eventBus.custom;

/**
 * @author nate-pt
 * @date 2021/7/15 10:09
 * @Since 1.8
 * @Description 同步还是异步
 */
public enum ThreadMode {
    /**
     * 异步线程驱动
     */
    ASYNC,
    /**
     * 主线程驱动
     */
    MAIN
}
