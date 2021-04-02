package sx.nine.guide;

/**
 * @author psx
 * @date 2021/4/1 16:31
 * @description
 * @since 1.0
 * @version 1.0
 */

/**
 *
 *
 * 我们提供了一个类：
 *
 * public class Foo {
 *   public void first() { print("first"); }
 *   public void second() { print("second"); }
 *   public void third() { print("third"); }
 * }
 * 三个不同的线程 A、B、C 将会共用一个 Foo 实例。
 *
 * 一个将会调用 first() 方法
 * 一个将会调用 second() 方法
 * 还有一个将会调用 third() 方法
 * 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
 *
 * 来源：力扣（LeetCode）
 * @throws
*/
public class Foo {

  private int count = 0;

  private Object lock = new Object();



  public Foo() {
  }

  public static void main(String[] args) throws InterruptedException{
    FooTest01();
  }


  public void first(Runnable printFirst) throws InterruptedException {

    synchronized (lock){
     if(count!=0){
       lock.wait();
     }

      printFirst.run();
      count=1;
      lock.notifyAll();
    }
  }

  public void second(Runnable printSecond) throws InterruptedException {
    synchronized (lock){
      if(count!=1){
        lock.wait();
      }

      printSecond.run();
      count=2;
      lock.notifyAll();
    }
  }

  public void third(Runnable printThird) throws InterruptedException {

    synchronized (lock){
      if(count!=2){
        lock.wait();
      }

      printThird.run();
    }
  }

  private static void FooTest01() throws InterruptedException{
    Foo foo = new Foo();
    Thread thread1 = new Thread(()->{
      try {
        foo.first(()->{
          System.out.println(1);
        });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread thread2 = new Thread(()->{
      try {
        foo.second(()->{
          System.out.println(2);
        });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread thread3 = new Thread(()->{
      try {
        foo.third(()->{
          System.out.println(3);
        });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });

    thread1.start();
    thread2.start();
    thread3.start();
  }
}
