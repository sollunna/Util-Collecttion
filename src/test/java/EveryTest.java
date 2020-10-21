import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author psx
 * @date 2020/10/10 20:19
 * @description:  测试类
 * @version: V1.0
 * @since 1.0
 **/

public class EveryTest {

  public static void main(String[] args) {
    Set<Integer> longs = new HashSet<>();
    longs.add(1);
    longs.add(2);
    longs.add(3);

    List<Integer> integers = new ArrayList<>();

    integers.add(1);
    integers.add(2);
    integers.add(3);
    integers.add(4);

    System.out.println(integers.containsAll(longs));

  }

}
