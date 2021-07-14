package com.pt.responsibility.simple;

/**
 * @author nate-pt
 * @date 2021/7/14 11:09
 * @Since 1.8
 * @Description 责任链
 */
public class Demo {

    public static void main(String[] args) {
        Chain chain = new Chain();
        chain.setNext(new Boot() {
            @Override
            public void start() {
//                System.out.println("小明执行了");
            }

            @Override
            public void stop() {

            }

            @Override
            public void handler(Object param) {
                System.out.println(param);
                setParam(11111);
            }
        });

        chain.setNext(new Boot() {
            @Override
            public void start() {
//                System.out.println("小白执行了");
            }

            @Override
            public void stop() {

            }

            @Override
            public void handler(Object param) {
                System.out.println(param);
                setParam(3.32);
            }
        });

        chain.setNext(new Boot() {
            @Override
            public void start() {
//                System.out.println("小强执行了");
            }

            @Override
            public void stop() {

            }

            @Override
            public void handler(Object param) {
                System.out.println(param);
                setParam("最后执行改成String");
                System.out.println(getParam());
            }
        });

        chain.start();
    }

}
