package com.pt.eventBus.custom;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author nate-pt
 * @date 2021/7/15 16:01
 * @Since 1.8
 * @Description
 */
public class PtBusBuild {
    private PtBusBuild (){

    }

    public static  PtBusBuild getInstance(){
        return new PtBusBuild();
    }


    /**
     * 工作线程数
     */
    private int corePoolSize;
    /**
     * 最大线程数量
     */
    private int maxPoolSize;

    /**
     * 最大超时时间
     */
    private long keepAliveTime;

    /**
     * 等待时间单位
     */
    private TimeUnit unit;

    private  final int COREPOOLSIZE_DEAFALUT = 2;
    private  final int MAXPOOLSIZE_DEAFALUT = 5;
    private  final long KEEPALIVETIME_DEAFALUT = 3;
    private  final TimeUnit UNIT_DEAFALUT = TimeUnit.SECONDS;
    private  final LinkedBlockingDeque LINKEDBLOCKINGDEQUE_DEAFALUT = new LinkedBlockingDeque(5);


    private LinkedBlockingDeque linkedBlockingDeque;


    public PtBusBuild corePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    public PtBusBuild maxPoolSize(int corePoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    public PtBusBuild keepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    public PtBusBuild unit(TimeUnit unit) {
        this.unit = unit;
        return this;
    }

    public PtBusBuild linkedBlockingDeque(LinkedBlockingDeque linkedBlockingDeque) {
        this.linkedBlockingDeque = linkedBlockingDeque;
        return this;
    }


    public PtBus build() {
        return new PtBus(this.corePoolSize == 0 ? COREPOOLSIZE_DEAFALUT : this.corePoolSize,
                this.maxPoolSize == 0 ? MAXPOOLSIZE_DEAFALUT : this.maxPoolSize,
                this.keepAliveTime == 0L ? KEEPALIVETIME_DEAFALUT : this.keepAliveTime,
                this.unit == null ? UNIT_DEAFALUT : this.unit,
                this.linkedBlockingDeque == null ? LINKEDBLOCKINGDEQUE_DEAFALUT : this.linkedBlockingDeque);
    }
}
