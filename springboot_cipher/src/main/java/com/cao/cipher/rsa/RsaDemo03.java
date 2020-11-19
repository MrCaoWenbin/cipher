package com.cao.cipher.rsa;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author：Cao Wenbin
 * @date：2020-11-18 16:01
 * 非对称加密RSA
 *      综合，先把私钥和公钥保存到本地文件中，再读取出来进行私钥加密，公钥解密
 */
public class RsaDemo03 {
    public static void main(String[] args) throws Exception {
        String input = "原文";

        //generateKeyToFile("RSA", "a.pub", "a.pri");
        PrivateKey privateKey = getPrivateKey("a.pri", "RSA");
        PublicKey publicKey = getPublicKey("a.pri", "RSA");

        //加密
        String encrypt = encryptRSA("RSA", privateKey, input);
        //解密
        String decrypt = decryptRSA("RSA", publicKey, encrypt);
        System.out.println("解密后：" + decrypt);

    }

    /**
     * 保存公钥和私钥到本地文件
     * @param algorithm 加密算法
     * @param pubPath   公钥保存路径
     * @param priPath   私钥保存路径
     * @throws Exception
     */
    private static void generateKeyToFile(String algorithm, String pubPath, String priPath) throws Exception{
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

        //保存文件
        FileUtils.writeStringToFile(new File(pubPath), publicKeyString, Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(priPath), privateKeyString, Charset.forName("UTF-8"));
    }

    /**
     * 读取私钥
     * @param priPath
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String priPath, String algorithm) throws Exception{
        //将文件内容转为字符串
        String privateKeyString = FileUtils.readFileToString(new File(priPath), Charset.defaultCharset());
        //获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        //构建密钥规范，进行Base64解码
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        //生成私钥
        return keyFactory.generatePrivate(spec);
    }

    /**
     * 读取公钥
     * @param pulickPath
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String pulickPath,String algorithm) throws Exception{
        // 将文件内容转为字符串
        String publicKeyString = FileUtils.readFileToString(new File(pulickPath), Charset.defaultCharset());
        // 获取密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 构建密钥规范 进行Base64解码
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        // 生成公钥
        return keyFactory.generatePublic(spec);
    }

    /**
     * 公钥解密
     * @param algorithm 加密算法
     * @param key   公钥
     * @param encrypted 密文
     * @return
     * @throws Exception
     */
    public static String decryptRSA(String algorithm,Key key,String encrypted) throws Exception{
        // 创建加密对象
        // 参数表示加密算法
        Cipher cipher = Cipher.getInstance(algorithm);
        // 私钥进行解密
        cipher.init(Cipher.DECRYPT_MODE,key);
        // 由于密文进行了Base64编码, 在这里需要进行解码
        byte[] decode = Base64.decodeBase64(encrypted);
        // 对密文进行解密，不需要使用base64，因为原文不会乱码
        byte[] bytes1 = cipher.doFinal(decode);
        System.out.println(new String(bytes1));
        return new String(bytes1);
    }

    /**
     * 私钥加密
     * @param algorithm 加密算法
     * @param key   私钥
     * @param input 原文
     * @return
     * @throws Exception
     */
    public static String encryptRSA(String algorithm,Key key,String input) throws Exception{
        // 创建加密对象
        // 参数表示加密算法
        Cipher cipher = Cipher.getInstance(algorithm);
        // 初始化加密
        // 第一个参数:加密的模式
        // 第二个参数：使用私钥进行加密
        cipher.init(Cipher.ENCRYPT_MODE,key);
        // 私钥加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        // 对密文进行Base64编码
        return Base64.encodeBase64String(bytes);
    }
}
