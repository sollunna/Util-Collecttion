package sx.nine.util.order;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author shaoxia.peng
 * @date 2021/10/28 10:50
 * @description 排序
 * @since 1.0
 * @version 1.0
 */
public class OrderTest {

  public static void main(String[] args) {
    int[] nums = new int[5];
   nums[0] = 5;
   nums[1] = 2;
   nums[2] = 30;
   nums[3] = 13;
   nums[4] = 9;

    System.out.println(Solution.orderMath(nums));

  }

  /***
   * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
   * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
   * @author shaoxia.peng
   * @date 2021/10/28 11:30
   * @param
   * @return
   * @throws
  */
  static class Solution {
    public static String largestNumber(int[] nums) {
      return String.join("", IntStream.of(nums).mapToObj(a -> String.valueOf(a)).sorted((a, b) -> (b + a).compareTo(a + b)).collect(
          Collectors.toList())).replaceAll("^0+$", "0");
    }


    public static List<String> orderMath(int[] nums){
      List<String> collect = IntStream.of(nums).mapToObj(a -> String.valueOf(a))
          .sorted((a, b) -> (b + a).compareTo(a + b)).collect(Collectors.toList());

      return collect;
    }

  }

}
