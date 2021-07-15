package com.pt;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author nate-pt
 * @date 2021/7/14 17:31
 * @Since 1.8
 * @Description
 */
public class TestConfig {
    public static void main(String[] args) {
        // 默认加载application开头配置文件和reference 的配置文件
        Config config = ConfigFactory.load();
        System.out.println(config);


    }
}
