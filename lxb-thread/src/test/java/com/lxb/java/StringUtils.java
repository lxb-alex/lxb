package com.lxb.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Liaoxb
 * @Date 18-3-23 13:34:34
 */
public class StringUtils {

    public static void main1(String[] args) {
        String str = "sdfjaofansdfajsiofasfdl12313kasf\\ji/][{}*&^%$#@!~`+-()osd在是我是fnaslfaosidffna";
        char[] chars = str.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (char c : str.toCharArray()){
            int cunt = 1;
            if (map.containsKey(c)){
                map.put(c, (map.get(c)+1));
            }else{
                map.put(c, cunt);
            }
        }
        for (Map.Entry m : map.entrySet()){
             //System.out.println(m.getKey()+ " 出现过 " + m.getValue() + " 次");
        }

        String s = "z1w";
        for (char c : s.toCharArray()){
           if (Character.hashCode(c)>=48 && Character.hashCode(c) <= 57){
               System.out.println(c);
           }
        }
    }

    public static void main(String[] args) {
        String[] str = new String[3];
        System.out.println(str.length);
    }

}
