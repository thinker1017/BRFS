# BRFS
  BRFS是一个主要针对海量小文件（快照、图片、语音等）存储而设计的高可用、高性能、易扩展的分布式文件系统。BRFS对存储的文件格式没有限制和要求，任何格式的文件都会以byte字节流的方式进行存储。此系统功能不仅包含了现有其他开源产品具备的权限控制、数据增删读、多副本备份、数据检验等基础功能，同时我们还设计了独特且高效的副本自动迁移和平衡策略、多应用数据隔离且独立配置，系统资源管理插件、集群状态可视化监控与报警等功能。

  一般来说，一个BRFS系统包含如下三个部分：
  1、FS_Server.jar：在集群每个节点上运行的核心服务模块。在模块运行时会启动若干ReginNode(管理节点)和DataNode（数据节点）进程来提供服务。其中ReginNode进程主要职责是管理存储域元信息、管理数据节点、把用户数据分配到不同的数据节点上进行处理；DataNode进程主要职责是用户数据文件的写入和读取、副本自动平衡恢复、执行定时任务（副本数校验、CRC校验、数据删除、数据归并）执行等。
  2、FS_ResouceManager.jar：系统资源管理模块，用于实时收集和监控集群各节点资源负载情况，以支持系统可根据节点负载情况分配资源，解决各节点资源利用和负载不均衡问题。BRFS系统内部默认提供了一组资源管理的策略，主要包含CPU、内存、I/O、磁盘容量等负载指标。目前此模块采用可热插拔的设计方式，但如果用户有特殊需求，可自定义此插件，自行实现集群资源的分配和管理。
  3、server.properties.example：用于后台服务运行时所有的关键控制参数的默认值配置，如果想变更参数值，可以复制一个名为server.properties的文件，并把需要修改的属性和值添加到此文件中即可，程序运行时server.properties文件中的配置的参数值会覆盖server.properties.example文件中参数的默认值。
  同时，用户如需调用BRFS服务，则需要在工程中引入FS_Client.jar，并在代码中调用相关的接口对BRFS系统进行操作；

BRFS分布式文件系统接收的数据形式可以是快照、图片或者任何以byte数组方式进行存储的数据文件。

# 性能
  集群规模：由2台物理机器组成集群，数据保存一副本；
  单台配置：CPU：4核、内存：18G、磁盘：STAT盘 4T 7.2K；
  网络：千兆网卡；
  压测结果指标：QPS、CPU、MEMORY、IO。
  压测方式：
    分别使用1个/2个/3个JMeter客户端对BRFS进行压测，每个JMeter客户端开启50个并发线程；
    分别使用1个/2个/8个JMeter客户端对BRFS进行压测，每个JMeter客户端开启50个并发线程；
  目标文件：随机生成1KB数据文件；

  写入性能:
    1个JMeter  qps:7541; cpu:59%; 内存:21%; IO:53%; 吞吐量:9MB
    2个JMeter  qps:12216; cpu:66%; 内存:26%; IO:53%; 吞吐量:15MB
    3个JMeter  qps:15767; cpu:72%; 内存:33%; IO:53%; 吞吐量:19MB

  读取性能:
    1个JMeter  qps:13295; cpu:21%; 内存:19%; IO:3%; 吞吐量:7MB
    2个JMeter  qps:40729; cpu:61%; 内存:19%; IO:5%; 吞吐量:20MB
    8个JMeter  qps:96000; cpu:80%; 内存:25%; IO:5%; 吞吐量:50MB

# 价值
  1、文件存储采用写时合并机制，帮助客户解决环境IO瓶颈的问题；
  2、文件副本自动平衡恢复，帮助客户解决数据的安全性的问题；
  3、硬件资源负载管理，帮助客户解决集群资源使用热点的问题；
  4、引入应用分区的概念，帮助客户解决不同业务数据个性化处理的问题；
  5、集群横向扩容，帮助客户解决集群扩容不方便的问题。

# 如何使用
  BRFS系统依赖第三方组件极少，除依赖JDK等基础组件外，其他组件只依赖Zookeeper服务进行集群状态同步，且核心服务只有两个Jar文件，因此安装部署极为简单，   以下是部署安装的具体步骤：
    1、安装基础组件，主要包括Zookeeper、JDK等；
    2、根据业务需要配置server.properties文件；
    3、启动各节点FS_Server.jar服务；
    4、通过测试客户端，测试读写功能是否正常。

# 后续
  BRFS分布式文件系统目前推出仅是第一个版本，并已在博睿公司所有产品线上实际运行，大大降低了博睿公司对于海量小文件的存储成本。同时，BRFS下个版本还将进行两方面优化，一是对大文件存储的支持和优化，不再区分大文件或是小文件，而定位为海量非结构化数据分布式存储系统；二是解决目前所有类似服务都存在的需要用户保存文件FID的问题，对于用户来说，存储海量文件的FID同样是个很大的开销，BRFS将开发一款全新的基于磁盘的key-value系统，以解决海量FID和文件元数据关联存储的问题，请持续关注。
