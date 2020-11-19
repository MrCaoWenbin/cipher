package com.cao.cipher.des;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @author：Cao Wenbin
 * @date：2020-11-18 9:11
 *  DES/AES加密解密
 */
public class DesAesDemo {
    public static void main(String[] args) throws Exception {
        String input = "曹文斌";

        //key：DES加密算法的key必须是8个字节
        String key = "abcd1234";
        //key：AES加密算法的key必须是16个字节
        String key2 = "abcdefgh12345678";

        //获取Cipher对象的算法，默认加密模式和填充模式为 ECB/PKCS5Padding
        String transformation = "DES";
        //加密模式为CBC，必须指定初始向量，初始向量中密钥长度必须是8个字节
        String transformation1 = "DES/ECB/PKCS5Padding";
        //填充模式为NoPadding，原文的长度必须是8字节的整数倍
        String transformation2 = "DES/ECB/PKCS5Padding";

        //指定加密算法
        String algorithm = "DES";

        String encryptDES = encryptDES(input, key, transformation, algorithm);
        System.out.println("加密后为：" + encryptDES);

        String decryptDES =  decryptDES(encryptDES, key, transformation, algorithm);
        System.out.println("解密后为：" + decryptDES);
    }

    /**
     * 加密
     * @param input 原文
     * @param key   密钥
     * @param transformation    获取Cipher对象的算法
     * @param algorithm         获取密钥的算法
     * @return
     */
    public static String encryptDES(String input, String key, String transformation, String algorithm) throws Exception {
        //获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        //创建加密规则
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);

        //加密模式为CBC时：初始向量，长度必须是8位
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());

        //初始化加密模式和算法
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        //加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        //对数据进行Base64编码
        String encode = Base64.encodeBase64String(bytes);
        return new String(encode);
    }

    /**
     * 解密
     * @param input
     * @param key
     * @param transformation
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static String decryptDES(String input, String key, String transformation, String algorithm) throws Exception {
        //获取解密对象
        Cipher cipher = Cipher.getInstance(transformation);
        //指定密钥规则
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);

        //加密模式为CBC时：初始向量，长度必须是8位
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());

        //初始化加密模式和算法
        cipher.init(Cipher.DECRYPT_MODE, sks);
        //对数据进行Base64转码，解密
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(input));
        return new String(bytes);
    }
}
