package com.cao.cipher.rsa;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author：Cao Wenbin
 * @date：2020-11-18 16:01
 * 非对称加密RSA：私钥加密，公钥解密
 */
public class RsaDemo02 {
    public static void main(String[] args) throws Exception {
        String input = "原文";

        //创建密钥对生成器对象
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        //生成密钥对
        KeyPair keyPair = kpg.generateKeyPair();
        //生成私钥和公钥
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        //获取私钥公钥字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();
        byte[] publicKeyEncoded = publicKey.getEncoded();

        //私钥加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(input.getBytes());
        System.out.println("加密后：" + Base64.encodeBase64String(bytes));

        //公钥解密
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] bytes1 = cipher.doFinal(bytes);
        System.out.println("解密后：" + new String(bytes1));

    }
}
