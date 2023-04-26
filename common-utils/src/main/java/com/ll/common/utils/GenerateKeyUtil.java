package com.ll.common.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class GenerateKeyUtil {
    private final static String RSA = "RSA";

    public static void generateKeyPair(String pubPath, String priPath) {
        try {
            //  创建密钥对生成器对象
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            // 生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            String privateKeyString = Base64.encode(privateKey.getEncoded());
            String publicKeyString = Base64.encode(publicKey.getEncoded());

            // 保存文件
            if (pubPath != null) {
                FileUtils.writeStringToFile(FileUtil.file(pubPath), publicKeyString, String.valueOf(StandardCharsets.UTF_8));
            }
            if (priPath != null) {
                FileUtils.writeStringToFile(FileUtil.file(priPath), privateKeyString, String.valueOf(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成密钥对失败！");
        }
    }

    public static PrivateKey loadPrivateKeyFromFile(String filePath) {
        try {
            // 将文件内容转为字符串
            String keyString = FileUtils.readFileToString(FileUtil.file(filePath), String.valueOf(StandardCharsets.UTF_8));
            return loadPrivateKeyFromString(keyString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取私钥文件字符串失败！");
        }
    }

    public static PublicKey loadPublicKeyFromFile(String filePath) {
        try {
            // 将文件内容转为字符串
            String keyString = FileUtils.readFileToString(new File(filePath), String.valueOf(StandardCharsets.UTF_8));

            return loadPublicKeyFromString(keyString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取公钥文件字符串失败！");
        }
    }

    public static PublicKey loadPublicKeyFromClassPath(String filePath) {
        try {
            // 将文件内容转为字符串
            InputStream resourceAsStream = GenerateKeyUtil.class.getClassLoader().getResourceAsStream(filePath);
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = resourceAsStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            String keyString = result.toString(String.valueOf(StandardCharsets.UTF_8));

            //jdk8以上
            //String keyString = new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8);

            return loadPublicKeyFromString(keyString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取公钥文件字符串失败！");
        }
    }

    public static PublicKey loadPublicKeyFromString(String keyString) {
        try {
            // 进行Base64解码
            byte[] decode = Base64.decode(keyString);
            // 获取密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            // 构建密钥规范
            X509EncodedKeySpec key = new X509EncodedKeySpec(decode);
            // 获取公钥
            return keyFactory.generatePublic(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取公钥失败！");
        }
    }

    public static PrivateKey loadPrivateKeyFromString(String keyString) {
        try {
            // 进行Base64解码
            byte[] decode = Base64.decode(keyString);
            // 获取密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            // 构建密钥规范
            PKCS8EncodedKeySpec key = new PKCS8EncodedKeySpec(decode);
            // 生成私钥
            return keyFactory.generatePrivate(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取私钥失败！");
        }
    }
}
