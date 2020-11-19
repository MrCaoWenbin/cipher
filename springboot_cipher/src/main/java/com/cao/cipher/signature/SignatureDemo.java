package com.cao.cipher.signature;

import com.cao.cipher.rsa.RsaDemo03;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * @author：Cao Wenbin
 * @date：2020-11-18 17:59
 * 数字签名
 */
public class SignatureDemo {
    public static void main(String[] args) throws Exception{
        String input = "原文";
        //获取私钥，公钥
        PrivateKey privateKey = RsaDemo03.getPrivateKey("a.pri", "RSA");
        PublicKey publicKey = RsaDemo03.getPublicKey("a.pub", "RSA");
        //签名
        String signaturedData = getSignature(input, "sha256withrsa", privateKey);
        System.out.println("生成签名后：" + signaturedData);
        //验签
        boolean b = verifySignature(input, "sha256withrsa", publicKey, signaturedData);
        System.out.println("验签结果：" + b);

    }

    /**
     * 生成签名
     * @param input 原文
     * @param algorithm 加密算法
     * @param privateKey    私钥
     * @return
     * @throws Exception
     */
    private static String getSignature(String input, String algorithm, PrivateKey privateKey) throws Exception{
        //获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        //初始化签名
        signature.initSign(privateKey);
        //传入原文
        signature.update(input.getBytes());
        //开始签名
        byte[] sign = signature.sign();
        //对签名进行Base64编码
        return Base64.encodeBase64String(sign);
    }

    /**
     * 校验签名
     * @param input
     * @param algorithm
     * @param publicKey
     * @param signaturedData
     * @return
     * @throws Exception
     */
    private static boolean verifySignature(String input, String algorithm, PublicKey publicKey, String signaturedData) throws Exception{
        //获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        //初始化签名
        signature.initVerify(publicKey);
        //传入原文
        signature.update(input.getBytes());
        //校验数据
        return signature.verify(Base64.decodeBase64(signaturedData));
    }
}
