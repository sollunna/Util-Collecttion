package sx.nine.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: NineEr
 * @Date: 2020/3/18 11:47
 * @Description:数据校验用正则表达式， 所有正则表达式都能匹配空字符串，如需非空校验需要另外校验
 */
public class Regular {
    /** ID用 正则表达式(32位 16进制小写编码) */
    public static final String REGEX_ID = "^$|^[a-f0-9]{32}$";
    /** 手机用 正则表达式(首位为1,共11位数字) */
    public static final String REGEX_MOBILE = "^$|^1\\d{10}$";
    /** 验证是否是Json数据 正则表达式(首尾是{}) */
    public static final String REGEX_JSON = "^$|^\\{.*\\}$";
    /** 验证预约日期时间 正则表达式(精确到半小时) */
    public static final String REGEX_DATE_APPOINTMENT = "^$|^\\d{4}-[01]\\d-[0-3]\\d [0-2]\\d:(0|3)0$";
    /** 日期 正则表达式 */
    public static final String REGEX_DATE = "^$|^\\d{4}-[01]\\d-[0-3]\\d$";
    /** 6位数字 */
    public static final String REGEX_NUM6 = "^$|^\\d{6}$";
    /** 两位以内正整数 */
    public static final String REGEX_PINT2 = "^$|^[1-9]\\d{0,1}$";
    /** 三位以内正整数 */
    public static final String REGEX_PINT3 = "^$|^[1-9]\\d{0,2}$";
    /** 五位以内正整数 */
    public static final String REGEX_PINT5 = "^$|^[1-9]\\d{0,4}$";
    /** 十位以内正整数 */
    public static final String REGEX_PINT10 = "^$|^[1-9]\\d{0,9}$";
    /** 十位以内正整或0 */
    public static final String REGEX_INT10 = "^$|^[1-9]\\d{0,9}$|^0$";
    /** 可有8位整数,2位小数 */
    public static final String REGEX_PFLOAT10_2 = "^$|^(\\d\\.\\d{1})|([1-9]\\d{0,7}(\\.\\d{1,2})?)$";

    /**
     * 校验传入字符串是否匹配正则表达式
     *
     * @param  regex
     * @param  str
     * @return boolean
     */
    public static boolean matches(String regex, String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


    /**
     * 身份证校验
     *
     * @param  regex
     * @param  idn
     * @return boolean
     */
    public static boolean idnMatches(String regex, String idn) {
        char[] ind = new char[18];
        ind = idn.toCharArray();
        int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        char[] verfiy = {1,0,'X',9,8,7,6,5,4,3,2};
        int total = 0;
        for(int index = 0;index<17;index++) {
            total+=Integer.parseInt(""+ind[index])*weight[index];
        }
        return ind[17] == verfiy[total%11];
    }
}
