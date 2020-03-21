import sun.nio.cs.ArrayEncoder;

import java.io.Serializable;

/**
 * @Author: NineEr
 * @Date: 2020/3/19 10:59
 * @Description:
 */
public class MyArrayList implements Serializable {


    /*
    使用这个字段来判断当前集合类是否被并发修改
    即迭代器并发修改的fail-fast机制
     */
    private transient int modConut = 0;

    //第一次扩容的容量
    private static final int DAFAULT_CAPACITY = 10;

    //用于初始化空的list
    private static final Object[] EMPTY_ELEMENT_DATA = {};

    //实际存储的元素
    transient  Object[] elementData;

    //实际list集合大小,从0开始
    private int size;

    public MyArrayList(){
        this.elementData=EMPTY_ELEMENT_DATA;
    }

    public MyArrayList(int initialCapctity){
        if(initialCapctity>0){
            this.elementData = new Object[initialCapctity];
        }else if(initialCapctity==0) {
            this.elementData = EMPTY_ELEMENT_DATA;
        }else {
            throw new IllegalArgumentException("参数异常");
        }
    }

    public boolean add(Object e){
        //判断容量
        ensureCapacityInternal(size+1);
        //使用下标赋值,尾部插入
        elementData[size++] = e;

        return true;
    }

    //计算容量+确保容量
    private void ensureCapacityInternal(int miniCapacity){
        //用于并发判断
        modConut++;

        //如果是初次扩容,则使用默认的容量
        if(elementData==EMPTY_ELEMENT_DATA){
            miniCapacity = Math.max(DAFAULT_CAPACITY,miniCapacity);
        }
        //是否需要扩容,需要的最少容量大于现在数组的长度则要扩容
        if(miniCapacity - elementData.length>0){
            int oldCapacity = elementData.length;

            int newCapacity = oldCapacity+(oldCapacity>>1);
            //如果新容量<最小容量,则将最新的容量赋值给新的容量
            if(newCapacity - miniCapacity < 0){
                newCapacity = miniCapacity;
            }

            //创建新数组
            Object[] objects = new Object[newCapacity];
            //将旧的数组复制到新的数组里面
            System.arraycopy(elementData,0,objects,0,elementData.length);

            //修改引用
            elementData = objects;
        }

    }

    /**
     * 通过下标获得对象
     * @param index
     * @return
     */
    public Object get(int index){
        rangeCheck(index);
        return elementData[index];
    }

    /**
     * 边界检查
     * @param index
     */
    private void rangeCheck(int index) {
        if(index>size|| size<0){
            throw new IndexOutOfBoundsException("数组越界");
        }
    }

    /**
     * 判断对象所在位置
     * @param o
     * @return
     */
    public int indexOf(Object o){
        if(o==null){
            for(int i=0; i<size;i++){
                if(elementData[i]==null){
                    return i;
                }
            }
        }else {
            for (int i = 0; i < size; i++) {
                if(o.equals(elementData[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * @param index
     * @param obj
     * @return
     */
    public Object set(int index,Object obj){
        rangeCheck(index);
        Object oldValue = elementData[index];
        elementData[index]=obj;
        return oldValue;
    }

    /**
     * 根据索引删除元素
     * @param index
     * @return
     */
    public Object remove(int index){

        //用于并发判断
        modConut++;

        rangeCheck(index);
        Object oldValue = elementData[index];
        //计算要删除的位置后面有几个元素
        int numMove = size-index-1;
        if(numMove>0){
          System.arraycopy(elementData,index-1,elementData,index,numMove);
        }
        /*
         * 将多出的位置设置为空没有引用对象
         * 垃圾收集器可以回收
         * 如果不为空将会保存一个引用
         * 可能会导致内存泄漏
         */
        elementData[--size] =null;
        return oldValue;
    }

    /**
     * 获取数组实际大小
     * @return
     */
    public int size(){
        return this.size;
    }

}
