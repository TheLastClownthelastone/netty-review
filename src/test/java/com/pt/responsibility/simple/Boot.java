package com.pt.responsibility.simple;

/**
 * @author nate-pt
 * @date 2021/7/14 11:16
 * @Since 1.8
 * @Description
 */
public abstract class Boot {


    private Object param;

    private Boot next;

    public abstract void start();

    public abstract void stop();

    public Boot setNext(Boot boot) {
        this.next = boot;
        return boot;
    }

    public void startNext(Boot boot) {
        boot.start();
        boot.handler(boot.param);
        if (boot.next!=null) {
            boot.next.setParam(boot.param);
            startNext(boot.next);
        }
    }


    public abstract void handler(Object param);


    public void setParam(Object param){
        this.param = param;
    }

    public Object getParam(){
        return this.param;
    }

}
