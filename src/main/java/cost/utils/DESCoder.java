package cost.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * DES算法
 *
 * @author XIONGXIAOLONG944
 *
 */
public class DESCoder {

    private static Logger logger = LoggerFactory.getLogger(DESCoder.class);
    /**
     * 密钥算法 java支持56位密钥，bouncycastle支持64位
     */
    public static final String KEY_ALGORITHM = "DES";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    /**
     * 加密公钥
     */
    public static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCB8U7Ubqt1DtGPSt+7hcjNxyuDalu+JYJBI1Vb5reLf2SW9dPl1CTG7wFxgiHb+q69yoMwd/PcdoHZpooT0cFh1UEtLd4TVBLUB0NVDQZHwuLeAzrEMSYpHkfTc+MLzFx76LDTorcm8r/cbAu3CHNMtpqCVkIqvxL+oiEKOknuaQIDAQAB";

    /**
     * 加密私钥
     */
    public static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIHxTtRuq3UO0Y9K37uFyM3HK4NqW74lgkEjVVvmt4t/ZJb10+XUJMbvAXGCIdv6rr3KgzB389x2gdmmihPRwWHVQS0t3hNUEtQHQ1UNBkfC4t4DOsQxJikeR9Nz4wvMXHvosNOitybyv9xsC7cIc0y2moJWQiq/Ev6iIQo6Se5pAgMBAAECgYAoMSpv3Ldd4rKA/ujLCOt+cr+Ly2cLHnD2kuZBIdDd7g5//xOGwCKMAbtRj63Ha/eGrs/7Ou8L3v/1AWUuEjAdEB64RXB9k9PHvbMeUQyBB3qU5AdCcyeHSWOugFJSjF4oG+UDiv04BmvUs8INP4caUx7PlLsGt+PFRBDeJQF+sQJBAMv/AG9SYSspM0+NKvXeRdV2i4F4mxB7ONvb6G1LOdzhhLySGQP20Y0H1hCbn/TNyQxFFp6KZzLomeCORQxlDIUCQQCjEX+GTLcrYiNVAYvF0OFBuc707pIEkZlzF/ms2r38WlbPaO5Frs31CH6W3B4UFYjKNQ6FtmGzNTopN4LkUqGVAkEAizvKVTD6B1aAogxtRRFnwrG9uCnUVKR2A4IOGwLcRIlcasSg9FazYjn1/rHptFH8pjZFoQJAO7wMwvr33+TcbQJACqtabK8YTP5e0XEIdCj2lBZUNDJn22SrZZxr+aNWSuyeQXPcIzMMxruHjNdaIHZIPPK7wIPuYItbAtde+RFvAQJABo05bgj4RBx3tZLW3NBvb4wCrExTCapN16Ns5QlariyMPs/3VJ61DVCl31CoLYi/nWSAFgxkVCh65QQyR+aORA==";

    /**
     * base64加密解密key
     */
    public static final byte[] key = "YZTConnectRRJF".getBytes();

    /**
     *
     * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥
     *
     * @return byte[] 二进制密钥
     */
    public static byte[] initkey() throws Exception {

        // 实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥生成器
        kg.init(56);
        // 生成密钥
        SecretKey secretKey = kg.generateKey();
        // 获取二进制密钥编码形式
        return secretKey.getEncoded();
    }

    /**
     * 转换密钥
     *
     * @param key
     *            二进制密钥
     * @return Key 密钥
     */
    public static Key toKey(byte[] key) throws Exception {
        // 实例化Des密钥
        DESKeySpec dks = new DESKeySpec(key);
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        // 生成密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 加密数据
     *
     * @param data
     *            待加密数据
     * @param key
     *            密钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 解密数据
     *
     * @param data
     *            待解密数据
     * @param key
     *            密钥
     * @return byte[] 解密后的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 欢迎密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String str = "吆西 下班";
        System.out.println("原文：" + str);

        System.out.println("密钥：" + Base64.encodeBase64String(key));
        // 加密数据
        byte[] data = DESCoder.encrypt(str.getBytes(), key);
        System.out.println("加密后：" + Base64.encodeBase64String(data));
        // 解密数据
        data = DESCoder.decrypt(data, key);
        System.out.println("解密后：" + new String(data));
    }

    /**
     * 加密并使用Base64编码
     * @param str
     * @return
     */
    public static String encryptAndBase64(String str){
        logger.info("加密原文： {}", str);
        // 加密数据
        try {
            byte[] data = DESCoder.encrypt(str.getBytes(), key);
            String sign = Base64.encodeBase64String(data);
            logger.info("加密后的密文： {}", sign);
            return sign;
        } catch (Exception e) {
            logger.error("加密时出现异常 异常原因： " + e.toString());
        }
        return null;
    }

    /**
     * 解密
     * @param sign
     * @return
     */
    public static String decryptAndBase64(String sign){
        logger.info("需要解密的密文： {}", sign);
        // 加密数据
        try {
            byte[] data = Base64.decodeBase64(sign);
            data = DESCoder.decrypt(data, key);
            String str = new String(data);
            logger.info("解密后的原文： {}", str);
            return str;
        } catch (Exception e) {
            logger.error("加密时出现异常 异常原因： " + e.toString());
        }
        return null;
    }
}