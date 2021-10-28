package sx.nine.util.order;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author shaoxia.peng
 * @date 2021/10/28 11:16
 * @description
 * @since 1.0
 * @version 1.0
 */
public class Test {


  public static void main(String[] args) {
    int[] nums = new int[5];
    nums[0] = 5;
    nums[1] = 2;
    nums[2] = 30;
    nums[3] = 13;
    nums[4] = 9;

    System.out.println(order(nums));
  }

  public  static List<String> order(int[] ints){

    List<String> collect = IntStream.of(ints).mapToObj(a -> String.valueOf(a))
        .sorted((a, b) -> ( b+a).compareTo( a+b)).collect(
            Collectors.toList());

    return collect;
  }

}
