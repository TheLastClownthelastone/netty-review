package com.pt.xml;

import lombok.Data;

import java.io.Serializable;

/**
 * @author nate-pt
 * @date 2021/7/16 10:26
 * @Since 1.8
 * @Description
 */
@Data
public class User implements Serializable {

    private String name;
    private Integer age;
}
