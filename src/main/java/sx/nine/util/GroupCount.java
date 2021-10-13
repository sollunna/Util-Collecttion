package sx.nine.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
    user.setAab001(1);
    user.setAge(1);
    users.add(user);
    User user2 = new User();
    user2.setAac001("小二");
    user2.setAab001(1);
    user2.setAge(1);
    users.add(user2);
    User user3 = new User();
    user3.setAac001("小三");
    user3.setAab001(2);
    user3.setAge(1);
    users.add(user3);
    User user5 = new User();
    user5.setAac001("小五");
    user5.setAab001(2);
    user5.setAge(1);
    users.add(user5);
    User user6 = new User();
    user6.setAac001("小六");
    user6.setAab001(3);
    user6.setAge(1);
    users.add(user6);
    //测试分组
    Map<Integer, List<User>> collect = users.stream().collect(Collectors.groupingBy(User::getAab001));
    final Map<Integer,Double> pickingAmountMap = new HashMap<>();
    collect.forEach((key, value) -> {
      if (null !=value && !value.isEmpty()){
        pickingAmountMap.put(key,value.stream().filter(p -> null != p.getAge()).
            map(pmsPickLineEntity -> BigDecimal.valueOf(pmsPickLineEntity.getAge())).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).doubleValue());
      }
    });
    System.out.println(pickingAmountMap);
  }

}
