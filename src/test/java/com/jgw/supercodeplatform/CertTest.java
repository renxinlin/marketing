package com.jgw.supercodeplatform;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

public class CertTest {
	public static void main(String[] args) throws Exception {
		//读取keystore文件转换为keystore密钥库对象
		FileInputStream fis = new FileInputStream("H:\\test\\cert\\test.keystore");
		//因为生成证书的类型为JKS 也有其他的格式
		KeyStore keyStore = KeyStore.getInstance("JKS");
		String storepass="123456";
		keyStore.load(fis, storepass.toCharArray());
		fis.close();
		// 从keystore中读取证书和私钥
		String alias = "test1";//别名
		String keypass = "123456"; //别名密码
		keyStore.getCertificateChain(alias);
		X509Certificate  certificate = (X509Certificate) keyStore.getCertificate(alias);
		X500Principal p=certificate.getIssuerX500Principal();
		String name=p.getName("RFC1779");
		System.out.println(name);
		//读取公钥对象
		PublicKey publicKey = certificate.getPublicKey();
		System.out.println("提取的公钥为___:\n"+encodeBase64(publicKey.toString()));
		//读取私钥对象
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias,keypass.toCharArray());
		System.out.println("提取的私钥为___:\n"+encodeBase64(privateKey.toString()));
	}
	// 对字符密码加密  
    public static String encodeBase64(String str) throws Exception {  
        // 1.将传递进来的字符串密码 转换为字节数组 放到base64加密工具里 生产出一个加了密的字符串  
        return "";  
    }  
  
    // 对密文字符串解密  
    public static String decodeBase64(String base64Str) throws Exception {  
        // 根据加了密的字符串 使用base64的解密工具里 获取原来的明文字符串密码  
        return "";  
    }  	
}
