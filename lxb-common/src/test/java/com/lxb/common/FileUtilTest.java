package com.lxb.common;

import org.junit.Test;

import java.util.Properties;
import java.util.Set;

/**
 * @Description
 * @Author Liaoxb
 * @Date 2017/9/29 13:46:46
 */
public class FileUtilTest {

    @Test
    public void systemPropertiesTest(){
        Properties properties = System.getProperties();
        Set keySet  = properties.keySet();
        for (Object key : keySet){
            System.out.println(key + " : " + properties.get(key));
        }
    }

}
