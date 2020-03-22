package sx.nine.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StrUtil {
    public static char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 判断字符串，数组，集合 是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            if (obj == null || "".equals((String) obj)) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof Object[]) {
            if (obj == null || ((Object[]) obj).length == 0) {
                return true;
            } else {
                return false;
            }
        } else if (obj instanceof Collection<?>) {
            if (obj == null || ((Collection<?>) obj).size() == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            if (obj == null) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 生成UUID
     *
     * @return
     */
    public static String genUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成11位编号，可以用作订单号，有很小几率出现重复，需要做异常处理<br/>
     * 左边第一位为正负标识：正数1 负数0<br/>
     * 剩余位数为UUID的hashcode值<br/>
     * 可以再生成的编号基础上嵌入其他标识编码
     *
     * @return
     */
    public static String genNum() {
        int hashCode = UUID.randomUUID().toString().hashCode();
        StringBuffer num = new StringBuffer();
        if (hashCode < 0) {
            hashCode = 0 - hashCode;
            num.append("0");
        } else {
            num.append("1");
        }
        return num.append(String.format("%010d", hashCode)).toString().substring(0, 8);
    }

    /**
     * 解析aa=bb&cc=dd&ee=ff格式的字符串，返回HashMap
     *
     * @param urlencoded
     * @return
     */
    public static Map<String, String> parseUrlencoded(String urlencoded) {
        if (isEmpty(urlencoded)) {
            return null;
        }
        String[] entrys = urlencoded.split("&");
        if (isEmpty(entrys)) {
            return null;
        }

        Map<String, String> map = new HashMap<String, String>();
        String[] kv = null;
        for (String entry : entrys) {
            if (isEmpty(entry)) {
                continue;
            }
            kv = entry.split("=");
            if (isEmpty(kv)) {
                continue;
            }
            if (kv.length > 1) {
                map.put(kv[0], kv[1]);
            } else {
                map.put(kv[0], null);
            }
        }
        return map;
    }

    /**
     * 字符串转换方法 把字节数组转换成16进制字符串
     *
     * @param buf 初始字节数组
     * @return 转换后字符串
     */
    public static String toHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    /**
     * 获取字符串的MD5码
     *
     * @param str
     * @return
     */
    public static String toMD5(String str) {
        if (str == null) {
            return null;
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        md.update(str.getBytes());
        byte tmp[] = md.digest();
        return toHex(tmp);
    }

    /**
     * SHA256加密
     *
     * @param str
     * @return
     */
    public static String toSHA256(final String str) {
        return toSHA(str, "SHA-256");
    }

    /**
     * SHA加密
     *
     * @param str
     * @param key
     * @return
     */
    public static String toSHA(final String str, final String key) {
        // 是否是有效字符串
        if (str == null) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        messageDigest.update(str.getBytes());
        byte tmp[] = messageDigest.digest();
        return toHex(tmp);
    }

    /**
     * 用ISO-8859-1解码 再用UFT-8编码
     *
     * @param str
     * @return
     */
    public static String iso2utf(String str) {
        String utfStr = null;
        try {
            utfStr = new String(str.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utfStr;
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 对ID（32位）进行编码
     *
     * @param id
     * @return
     */
    public static String codeId(String id) {
        if (id == null || id.length() != 32) {
            return id;
        }

        StringBuilder coded = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            coded.append(HEX_CHAR[(int) (Math.random() * 15L) + 1]);
        }
        coded.append(id.substring(0, 11));
        for (int i = 0; i < 7; i++) {
            coded.append(HEX_CHAR[(int) (Math.random() * 15L) + 1]);
        }
        coded.append(id.substring(11));
        for (int i = 0; i < 12; i++) {
            coded.append(HEX_CHAR[(int) (Math.random() * 15L) + 1]);
        }

        return coded.toString();
    }

    /**
     * 对ID（32位）进行解码
     *
     * @param coded
     * @return
     */
    public static String decodeId(String coded) {
        if (coded == null || coded.length() != 64) {
            return coded;
        }

        StringBuilder id = new StringBuilder();
        id.append(coded.substring(13, 24));
        id.append(coded.substring(31, 52));

        return id.toString();
    }

    /**
     * map parameters 转url parameters
     *
     * @param sortedParams
     * @return
     */
    public static String getSignContent(Map<String, ?> sortedParams) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;

        for (int i = 0; i < keys.size(); ++i) {
            String key = keys.get(i);
            Object value = sortedParams.get(key);
            if (isNotEmpty(key) && isNotEmpty(value)) {
                content.append((index == 0 ? "" : "&") + key + "=" + value);
                ++index;
            }
        }
        return content.toString();
    }

//    /**
//     * map 排序
//     *
//     * @param sortedParams
//     * @return
//     */
//    public static Map<String, Object> mapSort(Map<String, ?> sortedParams) {
//        Map<String, Object> map = new HashMap<>();
//        List<String> keys = new ArrayList<>(sortedParams.keySet());
//        Collections.sort(keys);
//        int index = 0;
//        for (int i = 0; i < keys.size(); ++i) {
//            String key = keys.get(i);
//            Object value = sortedParams.get(key);
//            map.put(key, value);
//            ++index;
//        }
//        return map;
//    }
}
