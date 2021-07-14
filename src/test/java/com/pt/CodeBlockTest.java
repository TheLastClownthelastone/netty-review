package com.pt;

/**
 * @author nate-pt
 * @date 2021/7/14 9:39
 * @Since 1.8
 * @Description
 */
public class CodeBlockTest {


    public static void main(String[] args) {
        Test test = new Test() {
            {
                System.out.println("开始执行代码块了");
                Inner.getInstance().start();
            }

            @Override
            public void say() {
                System.out.println("say");
            }
        };

        test.say();
    }


    abstract static class Test {
        public abstract void say();
    }

    static class Inner extends  Thread{



        private Inner(){
            System.out.println("初始化Inner");
        }

        public static Inner getInstance(){
            return new Inner();
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("代码快中执行"+i);
            }
        }
    }
}
