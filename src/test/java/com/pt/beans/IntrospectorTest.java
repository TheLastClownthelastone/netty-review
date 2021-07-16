package com.pt.beans;

import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.Arrays;

/**
 * @author nate-pt
 * @date 2021/7/16 10:37
 * @Since 1.8
 * @Description
 */
public class IntrospectorTest {

    /**
     * decapitalize 方法将类名的首字母进行小写进行输出
     */
    @Test
    public void exec1(){
        String decapitalize = Introspector.decapitalize(IntrospectorTest.class.getSimpleName());
        System.out.println(decapitalize);
    }

    /**
     * 获取BeanInfo对象
     * @throws IntrospectionException
     */
    @Test
    public void exec2() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(IntrospectorTest.class);
        System.out.println(beanInfo);
    }

    @Test
    public void exec3(){
        String[] beanInfoSearchPath = Introspector.getBeanInfoSearchPath();

        System.out.println(Arrays.toString(beanInfoSearchPath));
    }

}
