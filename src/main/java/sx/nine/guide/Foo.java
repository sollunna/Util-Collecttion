package sx.nine.guide;

/**
 * @author psx
 * @date 2021/4/1 16:31
 * @description
 * @since 1.0
 * @version 1.0
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
      count++;
      lock.notifyAll();
    }
  }

  public void second(Runnable printSecond) throws InterruptedException {
    synchronized (lock){
      if(count!=1){
        lock.wait();
      }

      printSecond.run();
      count++;
      lock.notifyAll();
    }
  }

  public void third(Runnable printThird) throws InterruptedException {

    synchronized (lock){
      if(count!=2){
        lock.wait();
      }

      printThird.run();
      count++;
      lock.notifyAll();
    }
  }

  private static void FooTest01() throws InterruptedException{
    Foo foo = new Foo();
    Thread thread1 = new Thread(() -> {
      try {
        foo.first(() -> {
          System.out.println("one");

        });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread thread2 = new Thread(() -> {
      try {
        foo.second(() -> {
          System.out.println("two");
        });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    Thread thread3 = new Thread(() -> {
      try {
        foo.third(() -> {
          System.out.println("third");
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
