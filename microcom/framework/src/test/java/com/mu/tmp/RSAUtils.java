package cn.ffcs.up.auth.utils;


import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/** */

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 *
 * @author IceWee
 * @date 2012-4-26
 * @version 1.0
 */
public class RSAUtils {

    /** */
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** */
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    private static final String PRIVATEKEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIn2zWqU7K/2qm5pOpq5bp9R+3MTnStWTfJU9nC/Vo7UKH9dITPvrELCTK+qlqpx5Fes+l0GY7n6u4n4jyiw4ejsvkZYQ5ww477yLOn2FcoEGuZEwPgSCmfTST0OFUgQqn+/J11k9L92jEHyieE3qmhMkMt0UsVUSJwx/nZxo30ZAgMBAAECgYBD3YHigeuEC4R+14iaf8jo2j0kuGtB3Cxvnlez0otTqw1YyYkBsU49cLKkXvfKVEgM0Ow/QltgKvSBxCE31PrrDka5TygVMqqA/IM7NrDvjUcGLjyoeNmLA8660fWcDxUTlAGN5kxIvUATayVwKVflpWPWu0FPKsWrZustnEo+4QJBAMCmYsWqAKWYMVRXFP3/XGRfio8DV793TOckyBSN9eh8UhgoZyT3u7oeHmDJEwm4aNMHlg1Pcdc6tNsvi1FRCiUCQQC3VNzfF4xOtUgX7vWPL8YVljLuXmy12iVYmg6ofu9l31nwM9FLQ1TRFglvF5LWrIXTQb07PgGd5DJMAQWGsqLlAkAPE7Z9M73TN+L8b8hDzJ1leZi1cpSGdoa9PEKwYR/SrxAZtefEm+LEQSEtf+8OfrEtetWCeyo0pvKKiOEFXytFAkEAgynL/DC0yXsZYUYtmYvshHU5ayFTVagFICbYZeSrEo+BoUDxdI9vl0fU6A5NmBlGhaZ65G+waG5jLc1tTrlvoQJAXBEoPcBNAosiZHQfYBwHqU6mJ9/ZacJh3MtJzGGebfEwJgtln5b154iANqNWXpySBLvkK+Boq7FYRiD83pqmUg==";

    private static final String DEFAULT_CHARSET_NAME = "UTF-8";


//    public static void main(String[] args) {
//        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALCYOE/80phA/5HYXEUzCDLF2FxwwxM2Pvll51Ury7+P7bXkO/y2oqo+iB7TY3qUoICPEOqwaCYCDsyEfwoQ1AfWqgdMs/f8jVxrjSEv6jrVld+TyiPvk8wzQ83vN03R7bbdiF6DzmncNW/ALGMu3P3RVZlzWjmWZO9dtHrFdCWhAgMBAAECgYADLyaqY1u+wA+u2j2SFKeVfS8q14oRuunFLUFP4VbIRCwCmoEHmP/ZqjMuu+jWyUt6YsBo1h511PxEU5bg03xA0jlHQdSr4Fjq3gptbHxUweGg4Ng7z8bs85rkcRHCNSzYvArO92jymLhHUubSY9uQ0d+J8/xvR34xF4dFSxQ5kwJBAOroI7Mma+A2LQHTVvV/vA3gI7gsdpIgyd7069uz0/NThcrXClO82TjLe/Y8z1KNiDMivAxOfUFYJ7RiH3EQ+hsCQQDAc6TWjT4S8Jq47eunBqNNa96MG4f4nkl3X0W80LvZkIcPBa95gWnNZ0zEE6ZB7DkPOijqsuZ72xu737q50BrzAkEAsJUaqQQ72YrAIHwvBVMlGUUhdMQvyb9OIQT5++jdbi+eBpHKGJKAs/n60Gp76vGrrczxkUq7S5CqICYWvpblewJBAJ0EPTMHneOaBsJm62Mk2UurfLFXLQK9MjeAtcctjnYdovOwsdAys/3wEAPR/E3Ay3UPAJ8sQzw+MkyAZzp+/d0CQQCGlYn+/phAsyFBilTBj6FV5vWtE6UafI1CjOHQn31WhBibU2hIuUYwDsy5q7kLX1yL+yDSyLXZjvkD0WCJzVlm";
//        String token = "4a9b835d4a2b3a8663729adf00deb80036a4e7d6b73383a4c4dbf8cb06756ddc424efc94a6d086ae528854ece3cb25e65c0e569f5c33b54885330141868fe90186aa73fdab9b3122243c8a52f6489cc01801adc7b408c0da76882e253c2a5f2519fb1429b4153bfa0d463b2f0876d9b8b6cfcc22e7bb8974d4b62d7c98bc38d78acb3551abf5215c7ed15f1429faec72305841d7d83877e55a77524193ff52c7ec372b8bc3ad4e3208c13920c8d32cd40f8946a359e0fa6adc9deda8d57dc1b001ccf73127dac6ab53cc9a71c197f719be74b69787f1a289d4d028e691f17bfaa95c431dab138d2d37d6c39305730ce23c49d2389d4f48bc72d3a16f3a41c7d6956b95b4e65b89edc211998f0a67fc452cf88a393bf6c68bf667d41800720411ce8439368e66f58acec68fd293232cdb23d7f420e2fba37cfa68dbad39092d79b393508369fb1fd2d89ad953c3ff9b51f7d5630049a84375d7c0237b54b2ff8fd1da3c247e4b954cbb835368f8ef3fdfd58cfa039a32b88b06a3533a2a35a0e06066cf43db1ffb36e86e36cf119ce3db4bd5dde4aa42ad06bc18ebae3df74260f7d86625a832bc070155b8f3f479e6fe855911a55bf3d4fe0838e9521e90e583035b4e960f5e6473c4c4c1ffc9e778452ce8816d965c0960196bb961d4c7f34421db1e0af2c88b05732b732fab951c0353cd271f74d0cea46b88ce415e1c0e758532691c42b4609a3501810644fff71d40c02353e805d1910a9a15e0bfa692617076bb749e227a3005729716deb32560da58b6f20871fa9140ec77b8892ed0a55dd6aab5d7f2a5da572b4266258bd64bfe66cacf95905d7183f5e399930365f001d34ef37b8f7a3c12fc8777c2eb1ab8b6b6f5f7e6fcd1422e186aeb7ee40d293cac116970cb573a2a2035a23d7acd38e4cb89557ca0ff4454a5a0c965739447c30b6d0d14510bcdaa165aea45221fab649f3aa4cafdf74e4e368d9e51bbb7160468aefef083ef47ae8dc8b2bc2dbfcaffcd24ee1719cb3b1cc152f15218c0e9bb000e26cdfd2378d92bf660fd202e099e67e77bc37a852fed2e4d77d8ad505589f8668d6aca5805905d2f9c43aa7e884ca1081b66e1e8ebe6a69d8534fc8e33d292cd98dba22b4b5b99ce598367fbf6cd83fc53d094cbc934fba6b22be0cd8fb50ee2deec6c342918b5b4bf7d315c3472bd0a68246ddda91a290e061eb12dfb247a45b0f69b9ad8607d581d0a21d1d8433bdb2932b7b91ad85a5121bf84c4ae";
//        String json = "";
//        try {
//            json = RSAUtils.decryptByPrivateKeyStr(token, privateKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Gson gson = new Gson();
//        FjAccessToken accessTokenVo = gson.fromJson(json, FjAccessToken.class);
//
//        System.out.println(json);
//        System.out.println(accessTokenVo);
//
//    }


    /** */
    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 调整依据：《openapi-ctplat-20220428 软件源代码安全缺陷分析报告》 2.2.11 密码管理：弱加密：密钥长度不足 缺陷1
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /** */
    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data
     *            已加密数据
     * @param privateKey
     *            私钥(BASE64编码)
     *
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /** */
    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data
     *            已加密数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @param sign
     *            数字签名
     *
     * @return
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /** */
    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** */
    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /** */
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data
     *            源数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data
     *            源数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap
     *            密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /** */
    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap
     *            密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * java端公钥加密
     */
    public static String encryptedDataOnJava(String data, String PUBLICKEY) {
        try {
            data = Base64Utils.encode(encryptByPublicKey(data.getBytes(), PUBLICKEY));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    /**
     * java端私钥解密
     */
    public static String decryptDataOnJava(String data) {
        String temp = "";
        try {
            byte[] rs = Base64Utils.decode(data);
            temp = new String(RSAUtils.decryptByPrivateKey(rs, PRIVATEKEY),"UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 私钥加密
     *
     * @param dataStr
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKeyStr(String dataStr,
                                                String privateKey) throws Exception {
        byte[] data = dataStr.getBytes(DEFAULT_CHARSET_NAME);
        String encryptedDateStr = HexByteUtil
                .byteArr2HexStr(encryptByPrivateKey(data, privateKey));
        return encryptedDateStr;
    }

    /**
     * 公钥加密
     *
     * @param dataStr
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKeyStr(String dataStr, String publicKey)
            throws Exception {
        byte[] data = dataStr.getBytes(DEFAULT_CHARSET_NAME);
        String encryptedDateStr = HexByteUtil
                .byteArr2HexStr(encryptByPublicKey(data, publicKey));
        return encryptedDateStr;
    }


    /**
     * 私钥解密
     *
     * @param encryptedDataStr
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKeyStr(String encryptedDataStr,
                                               String publicKey) throws Exception {
        byte[] encryptedData = HexByteUtil.hexStr2ByteArr(encryptedDataStr);
        String str = new String(decryptByPublicKey(encryptedData, publicKey),
                DEFAULT_CHARSET_NAME);
        return str;
    }

    /**
     * 公钥解密
     *
     * @param encryptedDataStr
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKeyStr(String encryptedDataStr,
                                                String privateKey) throws Exception {
        byte[] encryptedData = HexByteUtil.hexStr2ByteArr(encryptedDataStr);
        String str = new String(decryptByPrivateKey(encryptedData, privateKey),
                DEFAULT_CHARSET_NAME);
        return str;
    }





}
