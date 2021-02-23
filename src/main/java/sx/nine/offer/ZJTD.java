package sx.nine.offer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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

    System.out.println(getIntRandom(integers));


  }


  /*
      6.1.0 5个人去一个海岛寻宝，最后一共找到了100枚金币。他们约定了一个分配方案。如下：
      五个海盗按照抽签的顺序依次提出方案，某一个人提出方案之后，剩余存活的人投票表决：
      方案需要获得超过半数人的认可之后才能被通过，否则方案提出者将会被扔进大海喂鲨鱼，某一个方案被通过后游戏就结束。
      注：每个人的投票都是在追求自己利益的最大化：保证自己不会被喂鲨鱼的前提下，尽量使自己分到更多的金币。

  解答:

从后往前逆向给出解题过程

从后往前推，假设1~3号提出方案后都没被接收，也就是1,2,3都喂了鲨鱼，只剩下4号和5号。这时4号知道5号一定会投反对票，让4号喂鲨鱼（这时5号只能获得自己的一票，没有超过人数的一半），
然后5号独吞金币。所以4号要想活命，必须不能让3号喂鲨鱼，也就是4号必定会支持3号。

3号也能明白4号一定会支持自己，所以当剩下3,4,5三个人的时候，三号提出的方案会是：100,0,0。即3号独吞掉100个金币，但3号依旧可以获得自己和4号的的票，获得了超过一半的票数。

2号推测得知3号的方案之后，会尝试为自己争取更多的票数，在2,3,4,5存活的时候，2号提出分配方案：98,0,1，1。这种分配方案下，4号，5号分到的金币比之前的0枚金币要多，

所以4号和5号一定会支持2号，在加上2号自己的票，2号的这种分配方案可以得到3票的支持，超过存活人数一半的票数。

1号的知2号的分配方案之后，必然不会让自己喂鲨鱼啊。1号会尝试去争取超过一半的票数，即1号在保证自己不会被喂鲨鱼的前提下尽量让自己得到更多的金币。

1号可以有以下两种方案：97,0,1,2,0或97,0,1,0,2。1号的这种方案可以获得3票（超过一半的票数）：自己、三号和四号或者5号的支持。

到这里问题就彻底明朗了，看到问题的第一眼可能会感觉无从下手，或者随便蒙一个答案，亦或是以为第一个提出方案的人最容易被喂鲨鱼。细细分析后得知：第一个提出方案的人才是最后的赢家。


 */





  /*
      6.1.1 给你一个有序整数数组，数组中的数可以是正数、负数、零，请实现一个函数，这个函数返回一个整数：返回这个数组所有数的平方值中有多少种不同的取值。

      */

    public static Integer getIntRandom(List list){
      if(Objects.isNull(list) || list.isEmpty()){
        return null;
      }
      //所有数据减去0,求绝对值
      //所有绝对值放入set去重
      //set长度即方案
      HashSet<Integer> res = new HashSet<>();
      for (Object o : list) {
        int abs = Math.abs((Integer) o - 0);
        res.add(abs);
      }
      return res.size();
    }


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
      6.1.5 进程和线程的区别，使用线程真的能节省时间？

      1、进程是资源分配的最小单位，线程是程序执行的最小单位（资源调度的最小单位）
      2、进程有自己的独立地址空间，每启动一个进程，系统就会为它分配地址空间，建立数据表来维护代码段、堆栈段和数据段，这种操作非常昂贵。
      而线程是共享进程中的数据的，使用相同的地址空间，因此CPU切换一个线程的花费远比进程要小很多，同时创建一个线程的开销也比进程要小很多。
      3、线程之间的通信更方便，同一进程下的线程共享全局变量、静态变量等数据，而进程之间的通信需要以通信的方式（IPC)进行。不过如何处理好同步与互斥是编写多线程程序的难点。
      4、但是多进程程序更健壮，多线程程序只要有一个线程死掉，整个进程也死掉了，而一个进程死掉并不会对另外一个进程造成影响，因为进程有自己独立的地址空间。


      进程：指在系统中正在运行的一个应用程序；程序一旦运行就是进程；或者更专业化来说：进程是指程序执行时的一个实例，即它是程序已经执行到课中程度的数据结构的汇集。从内核的观点看，进程的目的就是担当分配系统资源（CPU时间、内存等）的基本单位。
     线程：系统分配处理器时间资源的基本单元，或者说进程之内独立执行的一个单元执行流。进程——资源分配的最小单位，线程——程序执行的最小单位。


     线程进程的区别体现在4个方面：（高级）
    1、因为进程拥有独立的堆栈空间和数据段，所以每当启动一个新的进程必须分配给它独立的地址空间，建立众多的数据表来维护它的代码段、堆栈段和数据段，这对于多进程来说十分“奢侈”，系统开销比较大，而线程不一样，
    线程拥有独立的堆栈空间，但是共享数据段，它们彼此之间使用相同的地址空间，共享大部分数据，比进程更节俭，开销比较小，切换速度也比进程快，效率高，但是正由于进程之间独立的特点，使得进程安全性比较高，也因为进程有独立的地址空间，
    一个进程崩溃后，在保护模式下不会对其它进程产生影响，而线程只是一个进程中的不同执行路径。一个线程死掉就等于整个进程死掉。
    2、体现在通信机制上面，正因为进程之间互不干扰，相互独立，进程的通信机制****相对很复杂，譬如管道，信号，消息队列，共享内存，套接字等通信机制，而线程由于共享数据段所以通信机制很方便。
    3、体现在CPU系统上面，线程使得CPU系统更加有效，因为操作系统会保证当线程数不大于CPU数目时，不同的线程运行于不同的CPU上。
    4、体现在程序结构上，举一个简明易懂的列子：当我们使用进程的时候，我们不自主的使用if else嵌套来判断pid，使得程序结构繁琐，但是当我们使用线程的时候，基本上可以甩掉它，当然程序内部执行功能单元需要使用的时候还是要使用，所以线程对程序结构的改善有很大帮助。

      */

  /*
      6.1.7 水平触发边沿触发的区别？在边沿触发下，一个socket有500的数据，已读取200然后不再处理，是不是剩下的300就永远无法读取？
       1.1 水平触发
      基本概念

      读缓冲区不为空时, 读事件触发。
      写缓冲区不为满时, 写事件触发。
      处理流程

      accept新的连接, 监听读事件。
      读事件到达, 处理读事件。
      需要写入数据, 向fd中写数据, 一次无法写完, 开启写事件监听。
      写事件到达, 继续写入数据, 写完后关闭写事件。
      优缺点

      不会遗漏事件, 易编程。
      长连接需要写入的数据量大时, 会频繁开启关闭写事件

      1.2 边沿触发
      基本概念
      读缓冲区状态变化时, 读事件触发, 网卡接受到新数据。
      写缓冲区状态变化时, 写事件触发, 网卡发出了新数据。
      处理流程

      accept新的连接, 同时监听读写事件。
      读事件到达, 需要一直读取数据, 直到返回EAGAIN。
      写事件到达, 无数据处理则不处理, 有数据待写入则一直写入，直到写完或者返回EAGAIN。
      优缺点

      不需要频繁开启关闭事件, 效率较高。
      读写事件处理不当, 可能导致事件丢失, 编程教复杂。

      1.3 选择
      概述
      对于读事件而言，总体而言, 采用水平触发方式较好。应用程序在读取数据时，可能会一次无法读取全部数据，边沿触发在下一次可能不会触发。如果能够保证一次读取缓存的全部数据，可以采用边沿触发，效率更高, 但同时编程复杂度也高。
      对于写事件，当客户端服务端采用短连接或者采用长连接但发送的数据量比较少时(例如: Redis), 采用水平触发即可。当客户端与服务端是长连接并且数据写入的量比较大时(例如: nginx), 采用边沿触发, 因为边沿触发效率更高。
      目前，linux不支持读写事件分别设置不同的触发方式，具体采用哪种方式触发，需要根据具体需求。
      监听套接字事件设置

      监听套接字不需要监听写事件，只需要监听读事件。
      监听套接字一般采用水平触发方式。(nginx开启multi_accept时，会把监听套接字所有可读的事件全部读取，此时可以使用边沿触发。但为了保证连接不丢失，nginx仍然采用水平触发)
      通信套接字设置

      redis对于与客户端通信使用的套接字默认使用水平触发。
      nginx对于与客户端通信使用的套接字默认采用边沿触发。

      */

  /*
      6.1.8 有函数如下，输入1，返回什么？
      */

  /*
      6.1.9 设计http协议，A端发送 AAAA，至少让B端知道AAAA已发送完成。
      用 Content-Length 告诉B传输的数据长度
      Content-Length = chunked 的话，最后一个块大小为0，说明本次数据发送完成
      */

  /*
      6.2.0 流量总入口为api_gateway，api_gateway挂了会导致全部挂挂，用什么机制增大可用性？
      */

  /*
      6.2.1 mysql为什么要用b+树，不用平衡二叉树做索引结构？
      */

  /*
      6.2.2 创建数据库索引应该怎么考虑？
      普通索引 index
      全文索引 fulltext
      唯一索引 unique
      主键 primary key

      对 where、order_by、group_by 的字段优先考虑建立索引
      表的主键和外键必须要有索引
      区分度高的建立索引（同值较少）
      单表数据太少，不适合建索引
      必要时可以建立联合索引，遵循最左原则，例如index(a,b,c)，实际上是建立了index(a)，index(a,b)，index(a,b,c)三个索引
      NULL 值会导致索引失效

      */

  /*
      6.2.3 使用int 做primary key和使用string 有什么优劣？
      使用 INT
      优势
      存储空间小，只需要 4 byte
      插入和更新性能比 string 好，一定程度提高应用的性能
      join 操作性能好
      支持通过函数获取最新值
      建立索引占用空间小
      查询效率高
      缺点
      INT 类型数据范围有限制，如果存在大量的数据可能会超出 INT 的范围
      如果有合并表的操作，可能会出现主键重复

      使用string
      优势
      长度几乎没有限制
      适合对表水平拆分，方便扩展
      可以自己生成，不受整数的限制
      劣势
      占用空间较大
      join 的性能比 INT 低
      没有内置函数生成，需要在业务层先生成
      不利于外键关联

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
      6.2.7 不使用高级工具，只使用Linux自带的工具，你会如何debug?

      输出对应目录的 LOG 文件，通过 cat 和 grep 可以做到过滤
      strace 工具跟踪程序的系统调用
      gdb 调试工具
      tcpdump 可以抓网络数据包
      */

  /*
      6.2.8 如何预估一个mysql语句的性能？
      使用 explain 分析

      重点关注：

      type 如果等于 all ，说明是全表扫描，需要优化。其他取值有：ref、range、index 等，遵循：ALL < index < range ~ index_merge < ref < eq_ref < const < system
      key 使用的索引名称
      extra 可以看到本条语句是否使用了索引，是否是 filesort 排序等。可能的取值有：Using index、Using temporary、Using filesort、Using where 等
      rows 显示一共扫描了多少行，预估值
      */

  /*
      6.3.0 socket中，在tcp协议层面，数据分为10个报文发放。1-7次很顺利，第8次丢失。这次通信一定失败吗？如果第8次数据会重发，那在接收端是不是：先读取到1-7次的数据，然后读取到8-10次的数据?还是9-10次的数据会先到达？
      */

  /*
      6.3.1 free -h，buffers 和cached有什么不同
      Free中的buffer和cache （它们都是占用内存）基于内存的
      buffer ：作为buffer cache的内存，是块设备的读写缓冲区
      cache：作为page cache的内存， 文件系统的cache

      cached是cpu与内存间的，buffer是内存与磁盘间的，都是为了解决速度不对等的问题
      buffer是即将要被写入磁盘的，而cache是被从磁盘中读出来的
      */

  /*
      6.3.2 后台进程有什么特点，如果要你设计一个进程是后台进程，你会考虑什么

      linux后台进程也叫做守护进程，基本不和用户交互，独立于控制终端，周期性执行。
      他真正的父进程在 fork 出子进程后就先于子进程退出了，所以是 init 领养的孤儿进程。

      如果设计一个守护进程，需要考虑的是内存溢出或者泄露的问题。
      除此之外还需要考虑异常退出自动重启的问题。
      */

  /*
      6.3.3 僵尸进程是什么，如果产生一个僵尸进程，如何查找僵尸进程,孤儿进程是什么

      在unix/linux中，正常情况下，子进程是通过父进程创建的，子进程在创建新的进程。子进程的结束和父进程的运行是一个异步过程,即父进程永远无法预测子进程 到底什么时候结束。 当一个 进程完成它的工作终止之后，它的父进程需要调用wait()或者waitpid()系统调用取得子进程的终止状态。

　　孤儿进程：一个父进程退出，而它的一个或多个子进程还在运行，那么那些子进程将成为孤儿进程。孤儿进程将被init进程(进程号为1)所收养，并由init进程对它们完成状态收集工作。

　　僵尸进程：一个进程使用fork创建子进程，如果子进程退出，而父进程并没有调用wait或waitpid获取子进程的状态信息，那么子进程的进程描述符仍然保存在系统中。这种进程称之为僵死进程。

3、问题及危害

　　unix提供了一种机制可以保证只要父进程想知道子进程结束时的状态信息， 就可以得到。这种机制就是: 在每个进程退出的时候,内核释放该进程所有的资源,包括打开的文件,占用的内存等。 但是仍然为其保留一定的信息(包括进程号the process ID,退出状态the termination status of the process,运行时间the amount of CPU time taken by the process等)。
   直到父进程通过wait / waitpid来取时才释放。 但这样就导致了问题，如果进程不调用wait / waitpid的话， 那么保留的那段信息就不会释放，其进程号就会一直被占用，但是系统所能使用的进程号是有限的，如果大量的产生僵死进程，将因为没有可用的进程号而导致系统不能产生新的进程. 此即为僵尸进程的危害，应当避免。

　　孤儿进程是没有父进程的进程，孤儿进程这个重任就落到了init进程身上，init进程就好像是一个民政局，专门负责处理孤儿进程的善后工作。每当出现一个孤儿进程的时候，内核就把孤 儿进程的父进程设置为init，而init进程会循环地wait()它的已经退出的子进程。这样，当一个孤儿进程凄凉地结束了其生命周期的时候，init进程就会代表党和政府出面处理它的一切善后工作。
   因此孤儿进程并不会有什么危害。

　　任何一个子进程(init除外)在exit()之后，并非马上就消失掉，而是留下一个称为僵尸进程(Zombie)的数据结构，等待父进程处理。这是每个 子进程在结束时都要经过的阶段。如果子进程在exit()之后，父进程没有来得及处理，这时用ps命令就能看到子进程的状态是“Z”。如果父进程能及时 处理，可能用ps命令就来不及看到子进程的僵尸状态，但这并不等于子进程不经过僵尸状态。
   如果父进程在子进程结束之前退出，则子进程将由init接管。init将会以父进程的身份对僵尸状态的子进程进行处理。

　　僵尸进程危害场景：

　　例如有个进程，它定期的产 生一个子进程，这个子进程需要做的事情很少，做完它该做的事情之后就退出了，因此这个子进程的生命周期很短，但是，父进程只管生成新的子进程，至于子进程 退出之后的事情，则一概不闻不问，这样，系统运行上一段时间之后，系统中就会存在很多的僵死进程，倘若用ps命令查看的话，就会看到很多状态为Z的进程。 严格地来说，僵死进程并不是问题的根源，
   罪魁祸首是产生出大量僵死进程的那个父进程。因此，当我们寻求如何消灭系统中大量的僵死进程时，答案就是把产生大 量僵死进程的那个元凶枪毙掉（也就是通过kill发送SIGTERM或者SIGKILL信号啦）。枪毙了元凶进程之后，它产生的僵死进程就变成了孤儿进 程，这些孤儿进程会被init进程接管，init进程会wait()这些孤儿进程，释放它们占用的系统进程表中的资源，这样，
   这些已经僵死的孤儿进程 就能瞑目而去了。

   首先，我们可以用top命令来查看服务器当前是否有僵尸进程，可以看到第二行行尾有个 0 zombie，如果数字大于0，那么意味着服务器当前存在有僵尸进程

  可以用ps和grep命令寻找僵尸进程

  ps -A -ostat,ppid,pid,cmd | grep -e '^[Zz]'

  命令选项说明:

  -A 参数列出所有进程
  -o 自定义输出字段 我们设定显示字段为 stat（状态）, ppid（进程父id）, pid(进程id)，cmd（命令）这四个参数
  因为状态为 z或者Z的进程为僵尸进程，所以我们使用grep抓取stat状态为zZ进程

  运行结果参考如下
  Z 12334 12339 /path/cmd
  这时，我们可以使用 kill -HUP 12339来杀掉这个僵尸进程
  运行后，可以再次运行ps -A -ostat,ppid,pid,cmd | grep -e '^[Zz]'来确认是否将僵尸进程杀死
  如果kill 子进程的无效，可以尝试kill 其父进程来解决问题，例如上面例子父进程pid是 12334，那么我们就运行
  kill -HUP 12334来解决问题

  但是很多僵尸进程都很难kill掉.得找到原头再去处理

      */


  /*
      6.3.5 一个进程有20个线程，在某个线程中调用fork，新的进程会有20个线程吗？
      我们知道通过fork创建的一个子进程几乎但不完全与父进程相同。子进程得到与父进程用户级虚拟地址空间相同的（但是独立的）一份拷贝，包括文本、数据和bss段、堆以及用户栈等。子进程还获得与父进程任何打开文件描述符相同的拷贝，这就意味着子进程可以读写父进程中任何打开的文件，
      父进程和子进程之间最大的区别在于它们有着不同的PID。
但是有一点需要注意的是，在Linux中，fork的时候只复制当前线程到子进程，在fork(2)-Linux Man Page中有着这样一段相关的描述：
The child process is created with a single thread--the one that called fork(). The entire virtual address space of the parent is replicated in the child, including the states of mutexes, condition variables, and other pthreads objects;
the use of pthread_atfork(3) may be helpful for dealing with problems that this can cause.

也就是说除了调用fork的线程外，其他线程在子进程中“蒸发”了。
      */

  /*
      6.3.6 tcp/ip 流量控制和拥塞控制

 拥塞控制：拥塞控制是作用于网络的，它是防止过多的数据注入到网络中，避免出现网络负载过大的情况；常用的方法就是：（ 1 ）慢开始、拥塞避免（ 2 ）快重传、快恢复。

流量控制：流量控制是作用于接收者的，它是控制发送者的发送速度从而使接收者来得及接收，防止分组丢失的。
      */

  /*
      6.3.7 301/302有什么区别？应用上有什么异同。
      不同在于。 301表示旧地址A的资源已经被永久地移除了（这个资源不可访问了），搜索引擎在抓取新内容的同时也将旧的网址交换为重定向之后的网址；
      302表示旧地址A的资源还在（仍然可以访问），这个重定向只是临时地从旧地址A跳转到地址B，搜索引擎会抓取新的内容而保存旧的网址。
      */

  /*
      6.3.8 50X相关错误码的内涵是什么？
      500
      服务器内部错误

      501
      服务器无法识别请求方法

      502
      网关错误，从上游服务器收到无效的响应

      503
      服务不可用

      504
      网关超时
      
      505
      不支持 HTTP 请求协议版本
      */

  /*
      6.3.9 close wait和time wait是什么？如何排查？有什么意义？
      */

  /*
      6.4.0 http req和resp的中数据有哪些
      */

  /*
      6.4.1 什么是连接的半打开，半关闭状态
      */

  /*
      6.4.2 假如一个业务依赖单点redis，此redis故障将导致业务不可用，如何改进
      */

  /*
      6.4.3 redis sharding有哪些做法
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
