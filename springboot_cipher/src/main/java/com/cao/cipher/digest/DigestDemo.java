package com.cao.cipher.digest;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * @author：Cao Wenbin
 * @date：2020-11-18 15:43
 * 消息摘要：MD5
 */
public class DigestDemo {
    public static void main(String[] args) throws Exception {
        String input = "原文";
        //获取数字摘要对象
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //获取数字摘要的字节数组
        byte[] digest = messageDigest.digest(input.getBytes());
        //System.out.println(Base64.encodeBase64String(digest));

        StringBuilder sb = new StringBuilder();
        for(byte b : digest){
            //转成16进制
            String s = Integer.toHexString(b & 0xff);
            if(s.length() == 1){
                s = "0" + s;
            }
            sb.append(s);
        }
        System.out.println(sb.toString());
    }
}
