package com.pt;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author nate-pt
 * @date 2021/7/14 9:50
 * @Since 1.8
 * @Description
 */
public class AsynFiled {

    public static void main(String[] args) {
        Supplier<AsynA> aNew = AsynA::new;
        AsynA asynA = aNew.get();
        asynA.printListFile();
    }


    static class AsynA{
        private  volatile List<String> filePath = new ArrayList<>();


        {
            System.out.println("构造方法之前执行");
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "：执行执行。。。。。。");
                String rootPath = "D:/";
                File[] files = new File(rootPath).listFiles();
                Arrays.stream(files).forEach(file -> {
                    noDicFile(file);
                });

            });
            // 获取异步线程执行结果
            try {
                voidCompletableFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        public AsynA(){
            System.out.println("构造方法中执行进行条件过滤");
            filePath = filePath.stream().filter(s -> s.contains("$")).collect(Collectors.toList());
        }

        private void noDicFile(File file){
            if (!file.isDirectory()) {
                filePath.add(file.getAbsolutePath());
                return;
            }else{
                for (File listFile : file.listFiles()) {
                    noDicFile(listFile);
                }
            }
        }

        public void printListFile(){
            filePath.stream().forEach(System.out::println);
        }

    }
}
