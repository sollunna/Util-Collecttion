package sx.nine.guide;

import java.util.Objects;

/**
 * @author Nineer
 * @description ：单例模式的实现--懒汉式 线程不安全
 * 饿汉式先new 对象 有内存消耗
 * @datetime 2021/3/30 11:21 下午
 **/
public class SingeEgg {

    private static SingeEgg singeEgg;

    private SingeEgg(){

    }

    public static SingeEgg getSingeEgg(){
        if(singeEgg == null){
            singeEgg = new SingeEgg();
        }
        return singeEgg;
    }

}
