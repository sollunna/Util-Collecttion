1.公司怎么提交的实时任务，有多少 Job Manager？
```
我们使用 yarn session 模式提交任务。每次提交都会创建一个新的 Flink 集群，为每一个 job 提供一个 yarn-session，
任务之间互相独立，互不影响， 方便管理。任务执行完成之后创建的集群也会消失。线上命令脚本如下：
bin/yarn-session.sh -n 7 -s 8 -jm 3072 -tm 32768 -qu root.*.* -nm *-* -d

```

2.怎么做压力测试和监控？

```
我们一般碰到的压力来自以下几个方面：

一，产生数据流的速度如果过快，而下游的算子消费不过来的话，会产生背压。 背压的监控可以使用 Flink Web UI(localhost:8081) 来可视化监控，
一旦报警就能知道。一般情况下背压问题的产生可能是由于 sink 这个 操作符没有优化好，做一下 优化就可以了。比如如果是写入 ElasticSearch， 
那么可以改成批量写入，可以调 大 ElasticSearch 队列的大小等等策略。

二，设置 watermark 的最大延迟时间这个参数，如果设置的过大，可能会造成内存的压力。可以设置最大延迟时间小一些，
然后把迟到元素发送到侧输出流中去。 晚一点更新结果。或者使用类似于 RocksDB 这样的状态后端， RocksDB 会开辟堆外存储空间，但 IO 速度会变慢，
需要权衡。

三，还有就是滑动窗口的长度如果过长，而滑动距离很短的话，Flink 的性能会下降的很厉害。我们主要通过时间分片的方法，
将每个元素只存入一个“重叠窗 口”，这样就可以减少窗口处理中状态的写入。（详情链接：Flink 滑动窗口优化）

四，状态后端使用 RocksDB，还没有碰到被撑爆的问题


```
3.为什么使用 Flink 替代 Spark？
```
主要考虑的是 flink 的低延迟、高吞吐量和对流式数据应用场景更好的支持；另外，flink 可以很好地处理乱序数据，而且可以保证 exactly-once 
的状态一致性。
```

4.如何理解Flink的checkpoint

```
Checkpoint是Flink实现容错机制最核心的功能，它能够根据配置周期性地基于Stream中各个Operator/task的状态来生成快照，
从而将这些状态数据定期持久化存储下来，当Flink程序一旦意外崩溃时，重新运行程序时可以有选择地从这些快照进行恢复，
从而修正因为故障带来的程序数据异常。他可以存在内存，文件系统，或者 RocksDB。

```
5.exactly-once 的保证,exactly-once 如何实现
```
如果下级存储不支持事务，Flink 怎么保证 exactly-once？

端到端的 exactly-once 对 sink 要求比较高，具体实现主要有幂等写入和 事务性写入两种方式。幂等写入的场景依赖于业务逻辑
更常见的是用事务性写入。 而事务性写入又有预写日志（WAL）和两阶段提交（2PC）两种方式。
如果外部系统不支持事务，那么可以用预写日志的方式，把结果数据先当成状态保存，然后在收到 checkpoint 完成的通知时，一次性写入 sink 系统。


Flink 依靠 checkpoint 机制来实现 exactly-once 语义，如果要实现端到端 的 exactly-once，还需要外部 source 和 sink 满足一定的条件。
状态的存储通过状态 后端来管理，Flink 中可以配置不同的状态后端。

Flink通过状态和两次提交协议来保证了端到端的exactly-once语义。

详细请看：https://www.jianshu.com/p/9d875f6e54f2
```

6.说一下 Flink 状态机制？
```
Flink 内置的很多算子，包括源 source，数据存储 sink 都是有状态的。在 Flink 中，状态始终与特定算子相关联。Flink 会以 checkpoint 
的形式对各个任务的 状态进行快照，用于保证故障恢复时的状态一致性。Flink 通过状态后端来管理状态 和 checkpoint 的存储，
状态后端也可以有不同的配置选择。

```

7.海量 key 去重
```
怎么去重？考虑一个实时场景：双十一场景，滑动窗口长度为 1 小时， 滑动距离为 10 秒钟，亿级用户，怎样计算 UV？

使用类似于 scala 的 set 数据结构或者 redis 的 set 显然是不行的， 因为可能有上亿个 Key，内存放不下。
所以可以考虑使用布隆过滤器（Bloom Filter） 来去重。
```
8.checkpoint 与 spark 比较

```
Flink 的 checkpoint 机制对比 spark 有什么不同和优势？
spark streaming 的 checkpoint 仅仅是针对 driver 的故障恢复做了数据和元数据的checkpoint。而 flink 的 checkpoint 
机制要复杂了很多，它采用的是轻量级的分布式快照，实现了每个算子的快照，及流动中的数据的快照。

```
9.watermark 机制
```
在使用 EventTime 处理 Stream 数据的时候会遇到数据乱序的问题，流处理从 Event（事 件）产生，流经 Source，再到 Operator，
这中间需要一定的时间。虽然大部分情况下，传输到 Operator 的数据都是按照事件产生的时间顺序来的，但是也不排除由于网络延迟等原因而导致乱序的产生，
特别是使用 Kafka 的时候，多个分区之间的数据无法保证有序。因此， 在进行 Window 计算的时候，不能无限期地等下去，必须要有个机制来保证在特定的时间
后， 必须触发 Window 进行计算，这个特别的机制就是 Watermark（水位线）。Watermark是用于处理乱序事件的。

在 Flink 的窗口处理过程中，如果确定全部数据到达，就可以对 Window 的所有数据做窗口计算操作（如汇总、分组等），如果数据没有全部到达，
则继续等待该窗口中的数据全部到达才开始处理。这种情况下就需要用到水位线(WaterMarks)机制，它能够衡量数据处理进度（表达数据到达的完整性），
保证事件数据（全部）到达 Flink 系统，或者在乱序及延迟到达时，也能够像预期一样计算出正确并且连续的结果。

```
10.CEP
```
Flink CEP 编程中当状态没有到达的时候会将数据保存在哪里？
在流式处理中，CEP 当然是要支持 EventTime 的，那么相对应的也要支持数据的迟到现象，也就是 watermark的处理逻辑。CEP对未匹配成功的事件序 列的处理，
和迟到数据是类似的。在 Flink CEP 的处理逻辑中，状态没有满足的和迟到的数据，都会存储在一个 Map 数据结构中，也就是说，如果我们限定判断事件 序列的时长为5 分钟，
那么内存中就会存储 5 分钟的数据，这在我看来，也是对内存的极大损伤之一。

```

11.三种时间语义
```
Flink 三种时间语义是什么，分别说出应用场景
Event Time：这是实际应用最常见的时间语义，指的是事件创建的时间，往往跟watermark结合使用

Processing Time：指每一个执行基于时间操作的算子的本地系统时间，与机器相关。适用场景：没有事件时间的情况下，或者对实时性要求超高的情况

```

12.数据高峰的处理  
```
使用大容量的 Kafka 把数据先放到消息队列里面作为数据源，再使用 Flink 进行消费，不过这样会影响到一点实时性。

```
13.Flink的并行度有了解吗？Flink中设置并行度需要注意什么？
```
Flink程序由多个任务（Source、Transformation、Sink）组成。任务被分成多个并行实例来执行，每个并行实例处理任务的输入数据的子集。任务的并行实例的数量称之为并行度。

Flink中人物的并行度可以从多个不同层面设置：

操作算子层面(Operator Level)、执行环境层面(Execution Environment Level)、客户端层面(Client Level)、系统层面(System Level)。

Flink可以设置好几个level的parallelism，其中包括Operator Level、Execution Environment Level、Client Level、System Level

在flink-conf.yaml中通过parallelism.default配置项给所有execution environments指定系统级的默认parallelism；在ExecutionEnvironment里头可以通过setParallelism来给
operators、data sources、data sinks设置默认的parallelism；如果operators、data sources、data sinks自己有设置parallelism则会覆盖ExecutionEnvironment设置的
parallelism。 
```
14.Flink支持哪几种重启策略？分别如何配置？ 
```
重启策略种类：

固定延迟重启策略（Fixed Delay Restart Strategy）

故障率重启策略（Failure Rate Restart Strategy）

无重启策略（No Restart Strategy）

Fallback重启策略（Fallback Restart Strategy）

详细参考：https://www.jianshu.com/p/22409ccc7905
```
15.Flink的分布式缓存有什么作用？如何使用？ 
```
Flink提供了一个分布式缓存，类似于hadoop，可以使用户在并行函数中很方便的读取本地文件，并把它放在taskmanager节点中，防止task重复拉取。

此缓存的工作机制如下：程序注册一个文件或者目录(本地或者远程文件系统，例如hdfs或者s3)，通过ExecutionEnvironment注册缓存文件并为它起一个名称。

当程序执行，Flink自动将文件或者目录复制到所有taskmanager节点的本地文件系统，仅会执行一次。用户可以通过这个指定的名称查找文件或者目录，

然后从taskmanager节点的本地文件系统访问它。

详细参考：https://www.jianshu.com/p/7770f9aec75d
```
16.Flink中的广播变量，使用广播变量需要注意什么事项？
```
在Flink中，同一个算子可能存在若干个不同的并行实例，计算过程可能不在同一个Slot中进行，不同算子之间更是如此，因此不同算子的计算数据之间不能像Java数组之间一样互相访问，

而广播变量Broadcast便是解决这种情况的。

我们可以把广播变量理解为是一个公共的共享变量，我们可以把一个dataset 数据集广播出去，然后不同的task在节点上都能够获取到，这个数据在每个节点上只会存在一份。

https://www.jianshu.com/p/3b6698ec10d8 

```
17.Flink 中的 State Backends是什么？有什么作用？分成哪几类？说说他们各自的优缺点？
``` 
Flink流计算中可能有各种方式来保存状态：
窗口操作
使用了KV操作的函数
继承了CheckpointedFunction的函数
当开始做checkpointing的时候，状态会被持久化到checkpoints里来规避数据丢失和状态恢复。选择的状态存储策略不同，会导致状态持久化如何和checkpoints交互。
Flink内部提供了这些状态后端:
MemoryStateBackend
FsStateBackend
RocksDBStateBackend
如果没有其他配置，系统将使用MemoryStateBackend。
详细参考：https://www.cnblogs.com/029zz010buct/p/9403283.html
```
18.Flink中的时间种类有哪些？各自介绍一下？
```
Flink中的时间与现实世界中的时间是不一致的，在flink中被划分为事件时间，摄入时间，处理时间三种。

如果以EventTime为基准来定义时间窗口将形成EventTimeWindow,要求消息本身就应该携带EventTime

如果以IngesingtTime为基准来定义时间窗口将形成IngestingTimeWindow,以source的systemTime为准。

如果以ProcessingTime基准来定义时间窗口将形成ProcessingTimeWindow，以operator的systemTime为准。

参考：https://www.jianshu.com/p/0a135391ff41
```
19.Flink的table和SQL熟悉吗？Table API和SQL中TableEnvironment这个类有什么作用
``` 
TableEnvironment是Table API和SQL集成的核心概念。它负责：

A)在内部catalog中注册表

B)注册外部catalog

C)执行SQL查询

D)注册用户定义（标量，表或聚合）函数

E)将DataStream或DataSet转换为表

F)持有对ExecutionEnvironment或StreamExecutionEnvironment的引用 
```
20.Flink如何实现SQL解析的呢？ 
``` 
StreamSQL API的执行原理如下：

1、用户使用对外提供Stream SQL的语法开发业务应用；

2、用calcite对StreamSQL进行语法检验，语法检验通过后，转换成calcite的逻辑树节点；最终形成calcite的逻辑计划；

3、采用Flink自定义的优化规则和calcite火山模型、启发式模型共同对逻辑树进行优化，生成最优的Flink物理计划；

4、对物理计划采用janino codegen生成代码，生成用低阶API DataStream 描述的流应用，提交到Flink平台执行

详细参考：https://cloud.tencent.com/developer/article/1471612

```
21.Flink是如何做到批处理与流处理统一的？
```
Flink设计者认为：有限流处理是无限流处理的一种特殊情况，它只不过在某个时间点停止而已。Flink通过一个底层引擎同时支持流处理和批处理。

详细参考：https://cloud.tencent.com/developer/article/1501348

```
22.Flink中的数据传输模式是怎么样的？
```
在一个运行的application中，它的tasks在持续交换数据。TaskManager负责做数据传输。

TaskManager的网络组件首先从缓冲buffer中收集records，然后再发送。也就是说，records并不是一个接一个的发送，而是先放入缓冲，然后再以batch的形式发送。

这个技术可以高效使用网络资源，并达到高吞吐。类似于网络或磁盘 I/O 协议中使用的缓冲技术。

详细参考：https://www.cnblogs.com/029zz010buct/p/10156836.html
```
23.Flink的容错机制
```
Flink基于分布式快照与可部分重发的数据源实现了容错。用户可自定义对整个Job进行快照的时间间隔，当任务失败时，Flink会将整个Job恢复到最近一次快照，

并从数据源重发快照之后的数据。
```

24. Flink中的分布式快照机制是怎么样的
```
Flink容错机制的核心就是持续创建分布式数据流及其状态的一致快照。这些快照在系统遇到故障时，充当可以回退的一致性检查点（checkpoint）。

Lightweight Asynchronous Snapshots for Distributed Dataflows 描述了Flink创建快照的机制。此论文是受分布式快照算法 Chandy-Lamport启发，

并针对Flink执行模型量身定制。

可以参考：

https://zhuanlan.zhihu.com/p/43536305

https://blog.csdn.net/u014589856/article/details/94346801

```
25.Flink中的内存管理是如何做的？
```
Flink 并不是将大量对象存在堆上，而是将对象都序列化到一个预分配的内存块上，这个内存块叫做 MemorySegment，它代表了一段固定长度的内存（默认大小为 32KB），

也是 Flink 中最小的内存分配单元，并且提供了非常高效的读写方法。每条记录都会以序列化的形式存储在一个或多个MemorySegment中。

Network Buffers: 一定数量的32KB大小的缓存，主要用于数据的网络传输。在 TaskManager启动的时候就会分配。默认数量是2048个，可以通过 taskmanager.network.numberOfBuffers来配置。

Memory Manager Pool:这是一个由MemoryManager管理的，由众多MemorySegment组成的超大集合。Flink中的算法（如 sort/shuffle/join）会向这个内存池申请MemorySegment，将序列化后的数据存于其中，

使用完后释放回内存池。默认情况下，池子占了堆内存的70% 的大小。

Remaining (Free) Heap: 这部分的内存是留给用户代码以及TaskManager 的数据结构使用的，可以把这里看成的新生代。

Flink大量使用堆外内存。

详细参考：

https://www.cnblogs.com/ooffff/p/9508271.html
```
26.Flink的基础编程模型了解吗？ 
``` 
Flink 程序的基础构建单元是流（streams）与转换（transformations）。DataSet API 中使用的数据集也是一种流。数据流（stream）就是一组永远不会停止的数据记录流，

而转换（transformation）是将一个或多个流作为输入，并生成一个或多个输出流的操作。

执行时，Flink程序映射到 streaming dataflows，由流（streams）和转换操作（transformation operators）组成。每个 dataflow 从一个或多个源（source）开始，

在一个或多个接收器（sink）中结束。

详细参考：https://www.cnblogs.com/cxhfuujust/p/10925843.html

```
27.说说Flink架构中的角色和作用？
```
JobManager：

JobManager是Flink系统的协调者，它负责接收Flink Job，调度组成Job的多个Task的执行。同时，JobManager还负责收集Job的状态信息，并管理Flink集群中从节点TaskManager。

TaskManager：

TaskManager也是一个Actor，它是实际负责执行计算的Worker，在其上执行Flink Job的一组Task。每个TaskManager负责管理其所在节点上的资源信息，如内存、磁盘、网络，在启动的时候将资源的状态向JobManager汇报。

Client：

当用户提交一个Flink程序时，会首先创建一个Client，该Client首先会对用户提交的Flink程序进行预处理，并提交到Flink集群中处理，所以Client需要从用户提交的Flink程序配置中获取JobManager的地址，并建立到JobManager的连接，

将Flink Job提交给JobManager。Client会将用户提交的Flink程序组装一个JobGraph， 并且是以JobGraph的形式提交的。一个JobGraph是一个Flink Dataflow，它由多个JobVertex组成的DAG。其中，一个JobGraph包含了一个Flink程序的如下信息：

JobID、Job名称、配置信息、一组JobVertex等。

```
