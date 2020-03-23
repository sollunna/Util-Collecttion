import java.util.concurrent.Callable;

/**
 * @Author: NineEr
 * @Date: 2020/3/22 17:19
 * @Description:
 */
public class Mytask implements Callable<Object> {
    @Override
    public Object call() throws Exception {
        System.out.println("Callable实现多线程名称"+Thread.currentThread().getName());
        return "callThreaf";
    }
}
