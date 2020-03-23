package sx.nine.util.IDCard;

import sx.nine.util.DateUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 身份证处理工具类
 *
 * @author susu
 */
public class IdentNoUtils {
    // 每位加权因子
    private static int power[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    /**
     * 将15位的身份证转成18位身份证
     *
     * @param idcard
     * @return
     */
    private static String convertIdcarBy15bit(String idcard) {
        String idcard17 = null;
        // 非15位身份证
        if (idcard.length() != 15) {
            return null;
        }

        if (isDigital(idcard)) {
            // 获取出生年月日
            String birthday = idcard.substring(6, 12);
            Date birthdate = null;
            try {
                birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Calendar cday = Calendar.getInstance();
            cday.setTime(birthdate);
            String year = String.valueOf(cday.get(Calendar.YEAR));

            idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);

            char c[] = idcard17.toCharArray();
            String checkCode = "";

            if (null != c) {
                int bit[] = new int[idcard17.length()];

                // 将字符数组转为整型数组
                bit = converCharToInt(c);
                int sum17 = 0;
                sum17 = getPowerSum(bit);

                // 获取和值与11取模得到余数进行校验码
                checkCode = getCheckCodeBySum(sum17);
                // 获取不到校验位
                if (null == checkCode) {
                    return null;
                }

                // 将前17位与第18位校验码拼接
                idcard17 += checkCode;
            }
        } else { // 身份证包含数字
            return null;
        }
        return idcard17;
    }

    /**
     * 数字验证
     *
     * @param str
     * @return
     */
    private static boolean isDigital(String str) {
        return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param bit
     * @return
     */
    private static int getPowerSum(int[] bit) {
        int sum = 0;
        if (power.length != bit.length) {
            return sum;
        }

        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < power.length; j++) {
                if (i == j) {
                    sum = sum + bit[i] * power[j];
                }
            }
        }
        return sum;
    }

    /**
     * 将和值与11取模得到余数进行校验码判断
     *
     * @param sum17
     * @param sum17
     * @return 校验位
     */
    private static String getCheckCodeBySum(int sum17) {
        String checkCode = null;
        switch (sum17 % 11) {
            case 10:
                checkCode = "2";
                break;
            case 9:
                checkCode = "3";
                break;
            case 8:
                checkCode = "4";
                break;
            case 7:
                checkCode = "5";
                break;
            case 6:
                checkCode = "6";
                break;
            case 5:
                checkCode = "7";
                break;
            case 4:
                checkCode = "8";
                break;
            case 3:
                checkCode = "9";
                break;
            case 2:
                checkCode = "x";
                break;
            case 1:
                checkCode = "0";
                break;
            case 0:
                checkCode = "1";
                break;
        }
        return checkCode;
    }

    /**
     * 将字符数组转为整型数组
     *
     * @param c
     * @return
     * @throws NumberFormatException
     */
    private static int[] converCharToInt(char[] c) throws NumberFormatException {
        int[] a = new int[c.length];
        int k = 0;
        for (char temp : c) {
            a[k++] = Integer.parseInt(String.valueOf(temp));
        }
        return a;
    }

    public static Map IdentNoData(String idcard, String birthday) {
        Map m = new HashMap();
        String gender = "0";
        String gender_val = "未知的性别 ";
        String bir = "";
        if (idcard.length() == 15) {
            idcard = convertIdcarBy15bit(idcard);
        }
        // 获取性别
        String id17 = idcard.substring(16, 17);
        if (Integer.parseInt(id17) % 2 != 0) {
            gender = "1";
            gender_val = "男";
        } else {
            gender = "2";
            gender_val = "女";
        }
        if ("".equals(birthday) || birthday == null) {
            //获取出生年月
            birthday = idcard.substring(6, 14);
        }
        Date birthdate;
        try {
            birthdate = new SimpleDateFormat("yyyyMMdd")
                    .parse(birthday);
            GregorianCalendar currentDay = new GregorianCalendar();
            currentDay.setTime(birthdate);
            int year = currentDay.get(Calendar.YEAR);
            int month = currentDay.get(Calendar.MONTH) + 1;
            int day = currentDay.get(Calendar.DAY_OF_MONTH);
            bir = year + "-" + month + "-" + day;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        m.put("gender", gender);
        m.put("gender_val", gender_val);
        m.put("birthday", bir);
        return m;
    }

    public static String getSex(String idn, int type) {
        //type为1就是返回中文
        if (idn.length() == 15) {
            if (Integer.valueOf(idn.substring(14, 15)) % 2 == 0) {
                if (type == 1) {
                    return "女";
                }
                return "2";
            } else {
                if (type == 1) {
                    return "男";
                }
                return "1";
            }
        } else {
            if (Integer.valueOf(idn.substring(16, 17)) % 2 == 0) {
                if (type == 1) {
                    return "女";
                }
                return "2";
            } else {
                if (type == 1) {
                    return "男";
                }
                return "1";
            }
        }
    }

    public static Date getBirthday(String idn) {
        try {
            String birthday = idn.substring(6, 10) + "-" + idn.substring(10, 12) + "-" + idn.substring(12, 14);
            return DateUtil.str2date(birthday);
        } catch (
                Exception e
        ) {
            e.printStackTrace();
            return new Date();
        }
    }

}
