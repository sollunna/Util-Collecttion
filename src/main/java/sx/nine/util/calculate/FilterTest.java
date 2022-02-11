package sx.nine.util.calculate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import sx.nine.dto.User;

/**
 * @author shaoxia.peng
 * @date 2021/11/17 16:29
 * @description
 * @since 1.0
 * @version 1.0
 */
public class FilterTest {

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

    //为true的会进集合
    List<User> collect = users.stream().filter(user1 -> user1.getAab001()==2222).collect(
        Collectors.toList());
    for (User user1 : collect) {
      System.out.println(user1);
    }


  }


}
