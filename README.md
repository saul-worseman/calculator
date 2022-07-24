

# README

## 写在前面

+ 项目大概不可以在公司电脑上直接运行，我之前写的小原型好像跑不通，可能端口被管控了，所以我是使用自己pc写的，结果我会放在最后。
+ 项目尽可能使用了java原生的东西，这些东西一般在cpp里都有相应实现，避免使用了第三方组件。



## 项目要求

+ 实现一个计算器，支持两个数的加减乘除操作。

  这一点实现起来比较基础，用的java的`BigInteger`，模拟了字符串的加减乘除。最后结果见后。

+ 通过CS/BS模式实现客户端提供输入能力，服务端计算输入结果并发送回客户端显示。

  基本遵循client发消息，server处理消息并回传，除了我加了一点，client发的消息会在本地先进行一次正则检查，这样可以大大过滤掉很多不合理的消息，减缓服务器压力。

+ 客户端和服务端之间通过TCP进行消息传输，消息格式自定义

  使用java的socket编程，其实也就是对TCP的一个简单封装，消息格式定义如下。

  对于client：形如1+1，前后为operand，中间为operator，没有空格，封装到Request中，目前还没有别的想要处理的，所以本质和string无异。

  对于server： server会将这条信息进行一个简单处理，**变成一个计算器可接受的中间消息。**

  ```java
  public class MidMessage {
      OpeartorKind opeartorKind;
      List<BigInteger> operands = new ArrayList<>();
      List<String> operators = new ArrayList<>();
  }
  ```

  这样的中间消息格式，应该能够较好满足可扩展性，我会在后面阐述。

+ 服务端对于每个操作需要在日志中记录操作类型和操作数输入值。

  因为自己之前写东西，其实不太注意日志这一块，用了spring框架之后更是直接依赖框架日志，写c，c++一般都是边写边打桩输出一些东西，出问题后看日志定位问题其实比较薄弱，因为作业一般都是跑通就交了，之后不会维护了。这里我自行封装了一下原生Logger。他的逻辑我写到实现过程里。

  



## 实现过程

### 大体框架

整体构造如图，其中我把user和calculator单独拿出，讲的更清楚些。

![](https://www.plantuml.com/plantuml/png/PL19KiGm3Bph5Ve07vW3DG_9HTXDuXhP3fRCydiI6AQSS599wgMhh-RIl6SbweR6JyySDA4uNzWNC3xNHdG_ZS-m8Dn-8A-rPN4YKHzsJiOI0iBgyg8ODGpj7MtJM2NS7YwSYbTkUEimdugXnFttTo6wq_OGeg6hUFqLQOOHoLEHZJkZe8c3XNEA6MOoOofRkf7lzR7XO36M8CK-SAYX6tRlc6nL-Jg0e8PJ3qaLCekU5IxSsPRQDVwhaubG8X5Tjz6pVWC0)



### 通信过程

server对一个端口进行侦听，一旦有client连接会**另开一个线程进行处理，所以主线程不会阻塞**

目前实现方式采取的BIO。

### 日志处理

每个日志可以打印maxNum条日志，一旦打满，就从文件池（链表实现）里取出下一个文件名，并将其清空，然后把当前输出的文件名扔到末尾。这样可以循环在三个日志中进行打印。**老旧的日志会被擦掉，同时大量恶意的操作形成的日志也不会使系统崩溃。**

同时注意，因为每个处理client请求的线程都会打印日志，所以注意并发问题。

我这里使用了单例模式，确保全局只有一个`loggerAdapater`的情况下，在每次打印时，都检查日志条数，如果超过，则进行切换和清理。如果不切换，几乎什么事情也没有做。这是我能想到的一个比较细的粒度。

### 计算过程

将client给的request处理成midmessage后

```java
public class MidMessage {
    OpeartorKind opeartorKind;
    List<BigInteger> operands = new ArrayList<>();
    List<String> operators = new ArrayList<>();
}
```

根据`opeartorKind`决定是单双目或者混合运算，然后拿取operands和operators就可以进行处理。



## 可扩展性

### 对于其他运算的支持

对于单目操作：

+ 实现其正则匹配，从而client处可以验证通过。
+ server处转换成中间消息时，进行一些字符串处理
+ calculator处添加对应算法即可。

对于混合运算：

​	以上三点都要，只是在calculator处添加的算法要复杂一些，可以存入中缀，然后用后缀算，都可以。

对于连续加减乘除运算

​	以上三点都要，但是算法简单一些，只是取两个operand，取1个operator，算完拿结果再取一个operator和operand

### 对于“长短连接”的切换

目前是client算完一次就关闭连接。如果要反复运算的话，加一个循环，然后休止符进行判断即可。



## 改进之处

+ 目前不少配置都是写死的，需要在代码中修改，如果可以，将来需要使用配置文件。
+ 目前采用的阻塞io，未来可以使用NIO，比较难设计，目前动了一点。
+ 对于异常和日志处理的不太理想。

## 结果展示

为了防止公司电脑运行不起来，我演示了一下用12345分别进行5次操作，可以看到日志3结束在4+4，而日志一之前的内容已经被抹去，只有新加的5+5的日志。

![](https://s2.loli.net/2022/07/24/af7ZSmJRjPlk3gt.png)

![](https://s2.loli.net/2022/07/24/DazKWCeVrPH7EA6.png)