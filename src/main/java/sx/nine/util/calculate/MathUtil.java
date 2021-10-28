package sx.nine.util.calculate;

import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

/**
 * @author shaoxia.peng
 * @date 2021/9/29 11:43
 * @description 金额类处理
 * @since 1.0
 * @version 1.0
 */
public class MathUtil {


  /***
   * 判断金额字符串参数是否>0
   * @author shaoxia.peng
   * @date 2021/9/18 15:26
   * @param money
   * @return java.lang.String
   * @throws
   */
  public static String getMoneyToZero(String money){
    if(StringUtils.isBlank(money)){
      return "0";
    }else if(new BigDecimal(money).compareTo(BigDecimal.ZERO)>0){
      return "1";
    }
    return "0";
  }

  /***
   * 字符串金额转BigDecimal
   * @author shaoxia.peng
   * @date 2021/9/18 15:40
   * @param money
   * @return java.math.BigDecimal
   * @throws
   */
  public static BigDecimal getMoneyToBigDecimal(String money){
    return StringUtils.isNotBlank(money)?new BigDecimal(money):BigDecimal.ZERO;
  }

}
