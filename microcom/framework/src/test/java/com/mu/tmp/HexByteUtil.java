package cn.ffcs.up.auth.utils;


/**
 *功能说明:
 *
 *创建人:李涌
 *
 *创建时间:2013-10-31 上午8:40:02
 *
 *修改人             修改时间             修改描述
 *
 *
 *Copyright (c)2013 福建富士通信息软件有限公司-版权所有
 *
 */
public class HexByteUtil {

    // ---------------------------
    // 16进制字符串转数组
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    // 数组转16进制字符串
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        // 最大128位
        String result = sb.toString();
        return result;
    }

}