package sx.nine.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import sx.nine.dto.User;

/**
 * @author shaoxia.peng
 * @date 2021/10/13 13:38
 * @description 分组统计
 * @since 1.0
 * @version 1.0
 */
public class GroupCount {
  public static void main(String[] args) {
    List<User> users = new ArrayList<>();
    User user = new User();
    user.setAac001("小一");
    user.setAab001(1111);
    user.setAge(1);
    user.setSize(new BigDecimal("11"));
    users.add(user);

    User user2 = new User();
    user2.setAac001("小二");
    user2.setAab001(1111);
    user2.setAge(1);
    user2.setSize(new BigDecimal("22"));
    users.add(user2);
    //1+11+1+22
    User user3 = new User();
    user3.setAac001("小三");
    user3.setAab001(2222);
    user3.setAge(1);
    user3.setSize(new BigDecimal("33"));
    users.add(user3);

    //1+33
    User user5 = new User();
    user5.setAac001("小五");
    user5.setAab001(2222);
    user5.setAge(2);
    user5.setSize(new BigDecimal("55"));
    users.add(user5);
    //2+55

    User user6 = new User();
    user6.setAac001("小六");
    user6.setAab001(3333);
    //user6.setAge(1);
    user6.setSize(new BigDecimal("66"));
    users.add(user6);
    //1+66
    //对某字段求和
    BigDecimal result = users.stream().map(user000 -> BigDecimal.valueOf(user000.getAge())).reduce(BigDecimal.ZERO,BigDecimal::add);
    System.out.println(result);

// 将user对象的age取出来map为Bigdecimal

// 使用reduce()聚合函数,实现累加器
    //测试分组求和
    /*Map<String, List<User>> collect = users.stream().collect(Collectors
        .groupingBy(usertool -> usertool.getAab001()+"-"+usertool.getAge()));
    final Map<String,BigDecimal> fuckup = new HashMap<>();
    collect.forEach((key, value) -> {
      if (null !=value && !value.isEmpty()){
        fuckup.put(key,value.stream().filter(p -> null != p.getAge()).
            map(fucku -> BigDecimal.valueOf(fucku.getAge())
                .add(fucku.getSize())).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
      }
    });
    Iterator<Map.Entry<String,BigDecimal>> iterator = fuckup.entrySet().iterator();
    while (iterator.hasNext()){
      Entry<String, BigDecimal> next = iterator.next();
      System.out.println("key:"+next.getKey()+",value:"+next.getValue());
    }
    System.out.println(fuckup);*/
  }

}
