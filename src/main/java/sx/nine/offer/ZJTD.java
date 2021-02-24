package sx.nine.offer;

import java.util.Arrays;
import java.util.List;

/**
 * @author psx
 * @date 2021/2/8 9:00
 * @description
 * @since 1.0
 * @version 1.0
 */
public class ZJTD {

  public static void main(String[] args) {

    List<Integer> integers = Arrays.asList(1, -2, 1, -1);

  }


  /*
      6.1.0 。

  解答:




 */


/*
      6.2.4 数据库分表的方法？



      */

    /*
      6.2.5 表结构，订单纪录如下，写一个语句，求卖的最好的 top 10 product_id。
      */

  /*
      6.2.6 微服务，A服务请求B服务B1接口，B1接口又请求A服务A2接口。会不会有问题？
      */


  /*
      6.3.0 socket中，在tcp协议层面，数据分为10个报文发放。1-7次很顺利，第8次丢失。这次通信一定失败吗？如果第8次数据会重发，那在接收端是不是：先读取到1-7次的数据，然后读取到8-10次的数据?还是9-10次的数据会先到达？
      */


  /*
      6.1.2 一个环上有10个点,编号为0-9,从0点出发,每步可以顺时针到下一个点,也可以逆时针到上一个点,求:经过n步又回到0点有多少种不同的走法？？
      */

  /*
      6.1.3 一个乱序数组，求第K大的数。排序方式使用字典序。
      */

  /*
      6.1.4 一棵二叉树，求最大通路长度。（即最大左右子树高度之和）
      */


  /*
      6.1.8 有函数如下，输入1，返回什么？
      */
  /*
      6.2.0 流量总入口为api_gateway，api_gateway挂了会导致全部挂挂，用什么机制增大可用性？
      */

  /*
      6.2.1 mysql为什么要用b+树，不用平衡二叉树做索引结构？
      */




  /*
      6.4.1
      */

  /*
      6.4.2
      */

  /*
      6.4.3
      */

  /*
      6.4.4 当大量数据要求用redis保存，单机单点难以满足需要，设计（换寻找）一个负载均衡的方案
      */

  /*
      6.4.5 当redis 采用hash做sharding，现在有8个节点，负载方案是 pos = hash(key) % 8，然后保存在pos节点上。这样做有什么好处坏处？当8个节点要扩充到10个节点，应该怎么办？有什么更方便扩充的方案吗？（一致性hash, presharding）
      */

  /*
      6.4.6 如何保证redis和数据库数据的一致性。比如用户名既保存在数据库，又保存在redis做缓存。有如下操作 update_db(username); update_redis(username)。但是执行update_db后故障，update_redis没有执行。有什么简单办法解决这个问题。
      */

  /*
      6.5.0 数据库表包含三列：广告编号ad_id，广告开始投放时间ad_start，广告投放结束时间ad_end。用一行SQL语句查询给定时间段内存在的广告。
      */

  /*
      6.5.1 讲讲MapReduce的原理。
      */

  /*
      6.5.2 举出几种进程通信、线程通信的方式。
      */

  /*
      6.5.3 对列表中每一个元素找出比它大的第一个元素：输入一个listin，返回一个listout。对于任意listin[x]，将满足 y > x 且 listin[y] > listin[x] 的第一个 listin[y] 值作为 listout[x] 的值。时间复杂度限制为O(n)。
*/








}
