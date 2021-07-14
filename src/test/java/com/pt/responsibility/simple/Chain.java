package com.pt.responsibility.simple;

/**
 * @author nate-pt
 * @date 2021/7/14 11:15
 * @Since 1.8
 * @Description
 */
public class Chain {

    private Boot boot = new Boot() {
        @Override
        public void start() {
//            System.out.println("最开始执行");
        }

        @Override
        public void stop() {

        }

        @Override
        public void handler(Object param) {
        }
    };

    private Boot last = boot;

    public void start(){
        boot.setParam("开始的handler设置为String");
        boot.startNext(boot);
    }


    public Chain setNext(Boot boot){
        this.last = last.setNext(boot);
        return this;
    }
}
