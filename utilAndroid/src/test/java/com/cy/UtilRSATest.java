package com.cy;

import com.cy.data.UtilByte;
import com.cy.security.UtilRSA;

import org.junit.Test;

import java.util.Map;

public class UtilRSATest {

    static String publicKey;
    static String privateKey;

    public void init(){
        try {
            Map<String, Object> keyMap = UtilRSA.genKeyPair();
            publicKey = UtilRSA.getPublicKey(keyMap);
            privateKey = UtilRSA.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥： \n\r" + privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testMain() throws Exception {
        init();
        test();
        testSign();
        testHttpSign();
        testByteArray();
    }

    private void testByteArray() {
        byte[] salt = { 0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x5, 0x4, 0x3, 0x2, 0x1, 0x0 };
        System.out.println("byte数组："+ UtilByte.byteArray2HexStringWithSpace(salt));
    }

    public void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = UtilRSA.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = UtilRSA.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    public void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = UtilRSA.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = UtilRSA.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = UtilRSA.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = UtilRSA.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }

    public void testHttpSign() throws Exception {
        String param = "id=1&name=张三";
        byte[] encodedData = UtilRSA.encryptByPrivateKey(param.getBytes(), privateKey);
        System.out.println("加密后：" + encodedData);
        
        byte[] decodedData = UtilRSA.decryptByPublicKey(encodedData, publicKey);
        System.out.println("解密后：" + new String(decodedData));
        
        String sign = UtilRSA.sign(encodedData, privateKey);
        System.err.println("签名：" + sign);
        
        boolean status = UtilRSA.verify(encodedData, publicKey, sign);
        System.err.println("签名验证结果：" + status);
    }
    
    
}