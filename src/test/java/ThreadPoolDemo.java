/**
 * @Author: NineEr
 * @Date: 2020/3/22 18:15
 * @Description:
 */
public class ThreadPoolDemo implements Runnable {
    @Override
    public void run() {
        System.out.println("通过线程池实现多线程:"+Thread.currentThread().getName());
    }
}
