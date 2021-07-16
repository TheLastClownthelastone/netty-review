package com.pt.xml;

import io.netty.handler.codec.xml.XmlDecoder;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author nate-pt
 * @date 2021/7/16 10:26
 * @Since 1.8
 * @Description
 */
public class Test {

    public static void main(String[] args) {
        OutputStream os = null;

        try {
            os = new FileOutputStream("1.xml");
            User user = new User();
            user.setAge(1);
            user.setName("pt");
            XMLEncoder xmlEncoder = new XMLEncoder(os);
            xmlEncoder.writeObject(user);
            xmlEncoder.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
