package com.pt.beans;

import org.junit.Test;

import java.beans.Statement;
import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author nate-pt
 * @date 2021/7/16 10:52
 * @Since 1.8
 * @Description
 */
public class StatementTest {

    /**
     * 如果是内部类的话调用不到对应的方法
     * @throws Exception
     */
    @Test
    public void exec1() throws Exception {
        Statement statement = new Statement(new SDemo1(),"ss",new Object[]{"是的试试"});

        System.out.println(statement.getArguments());
        System.out.println(statement.getMethodName());

        statement.execute();

    }

    /**
     * 内部类调用不到
     * @throws Exception
     */
    @Test
    public void exec2() throws Exception {
        Statement statement = new Statement(new Inner(),"innerMethod",new Object[]{1});
        statement.execute();
    }

    class Inner{

        public void innerMethod(Inner i){
            System.out.println(i);
        }
    }

    /**
     * 结合xmlEnCode 写入方法信息
     * @throws IOException
     */
    @Test
    public void exec3() throws IOException {
        Statement statement = new Statement(new SDemo1(),"ss",new Object[]{"是的试试"});
        FileOutputStream fileOutputStream = new FileOutputStream("2.xml");
        XMLEncoder xmlEncoder = new XMLEncoder(fileOutputStream);
        xmlEncoder.writeStatement(statement);
        xmlEncoder.close();
        fileOutputStream.close();
    }


}
