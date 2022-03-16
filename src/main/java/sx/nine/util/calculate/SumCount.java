package sx.nine.util.calculate;

/**
 * @author shaoxia.peng
 * @date 2022-3-16 9:14
 * @description
 * @since 1.0
 * @version 1.0
 */
public class SumCount {


  //https://blog.csdn.net/qq_38974638/article/details/113913754
  /**
   * BigDecimal类型数值累加求和
   * stream().reduce()实现
   * List<BigDecimal> list=new ArrayList<>();
   * BigDecimal sum=list.stream().reduce(0,BigDecimal::add);
   */

  /**
   * Integer类型数值累加求和
   * stream().reduce()实现
   *
   List<Integer> list=new ArrayList<>();
   //lambda表达式实现
   Integer sum = list.stream().reduce(0, (current, number) -> current + number);
   //方法引用实现
   Integer sum2 = list.stream().reduce(0, Integer::sum);
   */

  /**
   * Collectors.summingInt()实现
   * List<Integer> list=new ArrayList<>();
   * long sum = list.stream().collect(Collectors.summingInt(x -> x));
   */

  /**
   * Collectors.summarizingInt()实现
   * List<Integer> list = new ArrayList<>();
   * IntSummaryStatistics summaryStatistics = list.stream().collect(Collectors.summarizingInt(x -> x));
   * long sum = summaryStatistics.getSum();
   */

  /**
   * Long类型数值累加求和
   * List<Long> list=new ArrayList<>();
   * //lambda表达式实现
   * Long sum = list.stream().reduce(0L, (current, number) -> current + number);
   * //方法引用实现
   * Long sum2 = list.stream().reduce(0L, Long::sum);
   */

  /**
   * Collectors.summingLong()实现
   * List<Long> list=new ArrayList<>();
   * long sum = list.stream().collect(Collectors.summingLong(x -> x));
   */

  /**
   * Collectors.summarizingInt()实现
   *         List<Long> list = new ArrayList<>();
   *         LongSummaryStatistics summaryStatistics = list.stream().collect(Collectors.summarizingLong(x -> x));
   *         long sum = summaryStatistics.getSum();
   */

  /**
   * Double类型数值累加求和
   * stream().reduce()实现
   * List<Double> list=new ArrayList<>();
   * //lambda表达式实现
   * Double sum = list.stream().reduce(0d, (current, number) -> current + number);
   * //方法引用实现
   * Double sum2 = list.stream().reduce(0d, Double::sum);
   */

  /**
   * Collectors.summingDouble()实现
   * List<Double> list=new ArrayList<>();
   * Double sum = list.stream().collect(Collectors.summingDouble(x -> x));
   */

  /**
   * Collectors.summarizingDouble()实现
   *         List<Double> list = new ArrayList<>();
   *         DoubleSummaryStatistics summaryStatistics = list.stream().collect(Collectors.summarizingDouble(x -> x));
   *         double sum = summaryStatistics.getSum();
   */

}
