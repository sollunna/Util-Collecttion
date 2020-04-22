package sx.nine.guava.test;


import com.google.common.base.Objects;

/**
 * @Author NineEr
 * @Description //
 * @Date $ $
 **/
public class GuavaTest {
    public static void main(String[] args) {
        String a = "a";
        String b = "a";
        System.out.println(Objects.equal(a, b));
        //System.out.println(a.equals(b));
        //System.out.println(Objects.equals(a, b));
    }
}
