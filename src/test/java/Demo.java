
import java.util.concurrent.*;

/**
 * @Author: NineEr
 * @Date: 2020/3/18 15:26
 * @Description:
 */
public class Demo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new ThreadPoolDemo());
        }
        System.out.println("主线程名称:"+Thread.currentThread().getName());
        //关闭线程池
        executorService.shutdown();
    }





}
