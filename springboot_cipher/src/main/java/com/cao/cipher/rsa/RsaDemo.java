package com.cao.cipher.rsa;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author：Cao Wenbin
 * @date：2020-11-18 16:01
 * 非对称加密RSA：获取私钥和公钥
 */
public class RsaDemo {
    public static void main(String[] args) throws Exception {

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
        //对公私钥进行base64编码
        String privateKeyString = Base64.encodeBase64String(privateKeyEncoded);
        String publicKeyString = Base64.encodeBase64String(publicKeyEncoded);

        System.out.println(privateKeyString);
        System.out.println(publicKeyString);

    }
}
