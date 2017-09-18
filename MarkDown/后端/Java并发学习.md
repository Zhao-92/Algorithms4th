

# JAVA并发学习

---

## 【JAVA并发学习一】并发和多线程

### 一 什么是线程

现代操作系统在运行一个程序时，会为其创建一个进程。例如，启动一个Java程序，操作系统就会创建一个Java进程。线程概念是在进程基础上定义的，线程是现代操作系统能够调度的最小单元，它被包含在进程之中，是行程中的实际运作单位。 一条**线程**指的是进程中一个单一顺序的控制流，一個进程中可以並行多個**线程**，每条**线程**并行执行不同的任务。

一个Java程序从main( )方法开始执行，然后根据既定的代码逻辑执行，看似没有其他线程的参与，但实际上java程序天生就是多线程程序，因为执行main( )方法就是一个名称为mian( )进程。一个最普通java程序，至少具备以下几个线程：

```java
public class Main {
    public static void main(String[] args) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数再加一倍，防止枚举时有可能刚好有动态线程生成
        int slackSize = topGroup.activeCount() * 2;
        Thread[] slackThreads = new Thread[slackSize];
        // 获取根线程组下的所有线程，返回的actualSize便是最终的线程数
        int actualSize = topGroup.enumerate(slackThreads);
        Thread[] atualThreads = new Thread[actualSize];
        // 复制slackThreads中有效的值到atualThreads
        System.arraycopy(slackThreads, 0, atualThreads, 0, actualSize);
        System.out.println("Threads size is " + atualThreads.length);
        for (Thread thread : atualThreads) {
            System.out.println(thread.getName());
        }
    }
}
```

打印结果：

```
Threads size is 5

Reference Handler   //清除Reference的线程
Finalizer			//调用对象difalize方法的接口
Signal Dispatcher   //分发处理发给JVM信号的线程
main				//main线程，用户程序入口

```

### 二 为什么使用多线程

使用多线程的理由之一是和进程相比，它是一种非常**花销小，切换快**，更"节俭"的多任务操作方式。在Linux系统下，启动一个新的进程必须分配给它独立的地址空间，建立众多的数据表来维护它的代码段、堆栈段和数据段，这是一种"昂贵"的多任务工作方式。而运行于一个进程中的多个线程，它们彼此之间使用相同的地址空间，共享大部分数据，启动一个线程所花费的空间远远小于启动一个进程所花费的空间，而且，线程间彼此切换所需的时间也远远小于进程间切换所需要的时间。

使用多线程的理由之二是线程间**方便的通信机制**。对不同进程来说，它们具有独立的数据空间，要进行数据的传递只能通过通信的方式进行，这种方式不仅费时，而且很不方便。线程则不然，由于同一进程下的线程之间共享数据空间，所以一个线程的数据可以直接为其它线程所用，这不仅快捷，而且方便。当然，数据的共享也带来其他一些问题，有的变量不能同时被两个线程所修改，有的子程序中声明为static的数据更有可能给多线程程序带来灾难性的打击，这些正是编写多线程程序时最需要注意的地方。





## 【JAVA并发学习二】Java内存模型

在并发编程中，需要处理两个关键问题：**线程之间如何通信**及**线程之间如何同步**（这里的线程是指并发执行的活动实体）。Java在Java内存模型上解决了这两个问题

### 一 原子性、可见性、有序性

Java内存模型是围绕着在并发过程中如何处理原子性、可见性、有序性这3个特征展开的，对这三个概念的理解非常重要

#### 1.1 原子性

原子性Atomicity就是一个操作是不可以被分割的，这个操作要不全部执行完，要不就不执行。由Java内存模型来直接保证的原子性变量操作包括：

- read、load、assign、use、store、write，我们大致可以认为基本数据类型的访问读写是具备原子性的（long和double除外）
- lock和unlock，尽管虚拟机并没有把lock和unlock操作直接开放给用户使用，但是却提供了更高层次的字节码指令monitorenter和monitorexit来隐式地使用这两个操作，这两个指令反应到java代码中就是synchronized

#### 1.2 可见性

可见性Visibility是指一个线程修改了共享变量的值，其他线程能够立即得知这个修改。Java中volatile、synchronized、final可以实现可见性：

- volatile变量的特殊规则保证了新值能够立即同步到主内存，以及每次访问前都要从主内存刷新
- synchronized的可见性通过“对一个变量执行unlock操作之前，必须先把此变量同不会主内存中”这条规则获得的
- final关键字的可见性是指：被final修饰的字段在构造器中一旦初始化完成，并且构造器没有把“this”的引用传递出去，那在其他线程中就能看到final字段的值

#### 1.3 有序性

有序性可以总结为：“如果在本地线程内观察，所有操作都是有序的；如果在一个线程中观察另一个线程，所有操作都是有序的，如果在一个线程中观察另一个线程，所有的操作都是无序的”。前半句指的是“线程内表现为串行的语义”，后半句是指“指令重排序”现象和“本地内存和主内存同步延迟”现象。Java提供了几种方式保证线程间操作的有序性：

- volatile：通过内存屏障实现对指令重排序的禁止
- synchronized：通过“一个变量在同步一个时刻只允许一个线程对其进行lock操作”来实现的

#### 1.4 总结

可以发现对于上面三种特性，synchronized都可以满足，因为synchronized的“万能”，这也使得开发中对synchronized的使用有些泛滥，导致对性能的影响。所以最新版本的JVM虚拟机锁进行了优化

### 二 重排序

#### 2.1 重排序

在执行程序时，为了提高性能，编译器和处理器常常会对指令进行重排序。也就是说，程序执行的顺序可能和你写的顺序不同。重排序分为3种：

- 编译器优化的重排序：编译器在不改变单线程程序语义的前提下，可以重新安排语句的执行顺序
- 指令级并行的重排序。现代处理器采用了指令级并行技术来将多条指令重叠执行。如果不存在数据依赖性，处理器可以改变语句对应机器指令的执行顺序
- 内存系统的重排序。由于处理器使用缓存和读/写缓冲区，这使得加载和存储操作看上去可能是在乱序执行

其中第一种属于编译器排序，后两种属于处理器重排序

重排序的目的在于：在不改变程序执行结果的前提下，尽可能提高并行度。编译器和处理器在进行重排序时，肯定要保证重排序后的程序能够正确的运行且运行结果不变，那是通过什么样的规则来判断是否可以进行重排序？主要是**数据依赖性**、**as-if-serial**这两个规则进行判断的。

#### 2.2 数据依赖性

如果两行代码间存在数据依赖性，那么进行重排序后运行结果就会被改变，这是就不能进行重排序操作(两行代码交换执行顺序)。如：

```
a = 1;
b = a + 1;
```

#### 2.3 as-if-serial

as-if-serial语义的意思是：不管怎么重排序（编译器和处理器为了提高并行度），（单线程）程序的执行结果不能被改变。编译器、runtime和处理器都必须遵守as-if-serial语义。

遵守as-if-serial语义的编译器、处理器共同为编写单线程程序的程序员创建了一个幻觉：单线程程序是按程序的顺序来执行的。asif-serial语义使单线程程序员无需担心重排序会干扰他们，也无需担心内存可见性问题。

#### 2.4 重排序对多线程的影响

通过上面的介绍，可以知道是否可以进行重排序的判断依据都是对于单线程说的，所以在多线程的情况下，重排序可能会破坏多线程程序的语义

在单线程程序中，对存在控制依赖的操作重排序，不会改变执行结果（这也是as-if-serial语义允许对存在控制依赖的操作做重排序的原因）；但在多线程程序中，对存在控制依赖的操作重排序，可能会改变程序的执行结果

### 三 Java内存模型

Java内存模型Java Memory Model简称JMM，下面都以JMM相称

#### 3.1 JMM基础

开篇的地方提到过并发编程的两个关键问题是：线程间如何通信及线程间同步。

通信是指线程之间以何种机制来交换信息。在命令式编程中有两种方式：共享内存和消息传递。在共享内存的并发模型中，线程之间共享程序的公共状态，通过读-写内存中的公共状态进行隐式通信。在消息传递并发模型中，线程之间没有公共状态，需要进行显式的消息通信

同步是指程序中用于控制不同线程间操作发生相对顺序的机制。在共享内存的并发模型中必须通过显式的方式

Java并发模型采用的是共享内存的方式进行。

#### 3.2 JMM抽象结构

java中所有**实例域**、**静态域**、**数组元素**都存储在堆内存中，堆内存在线程之间共享。局部变量、方法定义参数和异常处理器参数存储在每个线程的虚拟栈的栈帧的局部变量表中，不会在线程间共享

Java线程间的通信通过JMM控制，JMM决定一个线程对共享变量的写入何时对另一个线程可见。JMM模型如下图

![Java并发学习_JMM模型](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_JMM模型.png)

JMM定义了线程和主内存之间的抽象关系：线程之间的共享变量存储在主内存（Main Memory）中，每个线程都有一个私有的本地内存（Local Memory），本地内存中存储了该线程以读/写共享变量的副本。本地内存是JMM的一个抽象概念，并不真实存在。它涵盖了缓存、写缓冲区、寄存器以及其他的硬件和编译器优化。

当线程A和线程B通信时，A先更改x的值，然后将x更新后的值写入主内存的共享变量中，B再从主内存中读取x的值，完成A到B的通信。过程如下图

![Java并发学习_线程间通信图](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_线程间通信图.png)

#### 3.2 主内存和本地内存交互

主内存和本地内存的交互指：一个变量从主内存拷贝到本地内存，从工作内存同步回主内存的过程。Java内存模型通过8中操作来完成，而且这八种操作都是原子的、不可分割的（double、long类型除外，因为是64位的数据）

- read：作用于主内存的变量，把一个变量从主内存读取出来
- load：作用于工作内存的变量，把read得到的值放入到工作内存的变量副本中
- use：作用于工作内存的变量，把工作内存中一个变量的值传递给执行引擎
- assign：作用于工作内存的变量，把从执行引擎得到的值赋给工作内存中的变量
- store：作用于工作内存的变量，把工作内存中变量值传入主内存中
- write：作用于主内存的变量，把store得到的值写入主内存中变量中

#### 3.3 先行发生happens-before

happens-before是JMM最核心的概念。如果JMM中所有的有序性都仅仅靠volatile和synchronized来完成，那有一些操作会很复杂，但是我们在编写Java并发代码的时候并没有感受到这一点，这是因为“并行发生”原则的存在。对应Java程序员来说，理解happens-before是理解JMM的关键

happens-before的概念来阐述操作之间的内存可见性。在JMM中，如果一个操作执行的结果需要对另一个操作可见，那么这两个操作之间必须要存在happens-before关系，但两个存在happens-before关系的操作不一定需要操作可见。这里提到的两个操作既可以是在一个线程之内，也可以是在不同线程之间。

以下规则会产生happens-before关系：

- 程序顺序规则：一个线程中的每个操作，happens-before于该线程中的任意后续操作
- 监视器锁规则：对一个锁的解锁，happens-before于随后对这个锁的加锁
- volatile变量规则：对一个volatile域的写，happens-before于任意后续对这个volatile域的读
- 传递性：如果A happens-before B，且B happens-before C，那么A happens-before C
- start规则：如果线程A执行操作ThreadB.start()（启动线程B），那么A线程的ThreadB.start()操作happens-before于线程B中的任意操作
- join规则：如果线程A执行操作ThreadB.join()并成功返回，那么线程B中的任意操作happens-before于线程A从ThreadB.join()操作成功返回

对于Java程序员来说，happens-before规则简单易懂，它避免Java程序员为了理解JMM提供的内存可见性保证而去学习复杂的重排序规则以及这些规则的具体实现方法。

在进行JMM设计时，需要考虑两个关键因素：

- 对于程序员：希望内存模型易于理解、易于编程，提供足够强的可见性保证
- 对于编译器和处理器：希望内存模型对他们的束缚越少越好，这样他们就可以做尽可能多的优化来提高性能

这两个因素是相互对立的，在真正设计JMM时，把happens-before对重排序的限制分为两类：会改变程序执行结果的重排序、不会改变程序执行结果的重排序。不影响执行结果的都允许进行重排序操作

![Java并发学习_JMM设计](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_JMM设计.png)

JMM向程序员提供的happens-before规则能满足程序员的需求。JMM的happens-before规则不但简单易懂，而且也向程序员提供了足够强的内存可见性保证

JMM对编译器和处理器的束缚已经尽可能少。从上面的分析可以看出，JMM其实是在遵循一个基本原则：只要不改变程序的执行结果（指的是单线程程序和正确同步的多线程程序），编译器和处理器怎么优化都行。例如

```java
double pi = 3.14;              //A
double r = 1.2;                //B
double area = pi * r * r;      //C
```

按照happens-before规则会生成三条规则：

- A happens-before B
- B happens-before C
- A happens-before C

其中第一个就属于不必要的，后面两个就属于必需的。这时JVM就允许对A进行重排序

#### 3.4 JMM的内存可见性保证

按程序类型，Java程序的内存可见性保证可以分为下列3类。

- 单线程程序。单线程程序不会出现内存可见性问题。编译器、runtime和处理器会共同确保单线程程序的执行结果与该程序在顺序一致性模型中的执行结果相同。
- 正确同步的多线程程序。正确同步的多线程程序的执行将具有顺序一致性（程序的执行结果与该程序在顺序一致性内存模型中的执行结果相同）。这是JMM关注的重点，JMM通过限制编译器和处理器的重排序来为程序员提供内存可见性保证。
- 未同步/未正确同步的多线程程序。JMM为它们提供了最小安全性保障：线程执行时读取到的值，要么是之前某个线程写入的值，要么是默认值（0、null、false）









## 【JAVA并发学习三】创建线程对象

### 一 创建线程类

在Java中实现多线程，主要依靠java.lang.Thread类，每一个Thread对象都是一个线程，创建一个新的线程类的方法有两种：继承Thread类、实现Runable接口

#### 1.1 继承Thread类 

通过继承Thread类，并重写run( )方法，创建新的线程对象。其中run( )方法中就是线程将会执行的代码，start( )方法用于启动线程。当调用start( )方法后并不会马上开始执行多线程代码，而是让该线程进入可运行状态(Runnable)，至于具体什么时候真正运行，需要操作系统的线程调度来决定。

```java
public static void main(String[] args) {
    ThreadTest threadTest = new ThreadTest();
    threadTest.start();
}

class ThreadTest extends Thread {
    int num = 0;

    @Override
    public void run() {
      num++;
      System.out.println(num);
    }
}
```

其实上面代码中的main( )方法就是一个单独的线程，每个java程序都会有两个基本线程：主线程main和GC线程

#### 1.2 实现Runnale接口

实现Runnable接口的run( )方法，并通过Thread类的构造方法Thread( Runnable )生成线程对象，然后调用start( )方法启动线程

```java
public static void main(String[] args) {
    Runnable runnable = new ThreadTest();
    Thread thread = new Thread(runnable);
    thread.start();
}

class ThreadTest implements Runnable {
    int num = 0;

    @Override
    public void run() {
        num++;
        System.out.println(num);
    }
}
```

#### 1.3 Thread和Runnable两种方式区别

两种方式中推荐使用实现Runnable接口的方式，他的优势在于：

- 适合多个相同的程序代码的线程去处理同一个资源，创建一个Runnable对应多个Thread
- 可以解决java单继承的限制，继承Thread时无法继承其他类
- 增加程序健壮性，代码可以被多个线程共享，代码和数据独立
- 线程池只能放入Runnable和callable类线程，无法放入继承Thread的类

### 二 线程状态转换

- New：新创建一个线程对象
- Runnable：可运行状态，对应操作系统的就绪状态
- Running：正在运行，对应操作系统的运行状态
- Blocked：阻塞状态，当线程因为某种原因（等待IO、wait( )、获取同步锁失败）放弃CPU的使用权，暂时停止运行。直到线程再次进入就绪状态被操作系统线程调度选中才能获得CPU使用权。阻塞状态分为下面三种：
  - 等待阻塞：运行wait( )方法后进入等待阻塞，进入等待队列
  - 同步阻塞：运行的线程尝试获取一个对象的同步锁时，如果该对象的锁被其他线程占用，则JVM会把该线程放入锁池中，进入同步阻塞状态
  - 其他阻塞：运行的线程执行sleep( )或join( )方法、或者等待IO时，JVM会把线程设为阻塞状态。

![JAVA并发学习状态图](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/JAVA并发学习状态图.png)

这张图非常重要，说明了一个线程整个生命周期中状态的转换方式

### 三 线程调度

所谓的线程调度就是，通过不同的方法使线程完成第二节状态图中状态的变化，主要通过调用不同的API来实现

#### 3.1 sleep( )方法

Thread.sleep( long millis )方法是Thread类的静态方法，使当前进程进入阻塞状态，millis为睡眠的时间，以毫秒为单位，时间截止后线程进入Runnable状态。

需要注意的是，执行sleep方法后，线程**不会释放已经持有的锁**，所以这个线程会一直持有已经获取的锁，即使该线程没有在运行

#### 3.2 join( )方法

join( )是Thread的普通方法，他的作用是“插入新线程，并等他执行完”。例如线程t1正在运行，这时候在t1线程中生成子线程t2，执行t2.join( )方法后，JVM会把t1线程设置为阻塞状态，在t2线程执行完后，JVM才会把t1线程从阻塞状态设为Runnable状态

使用join( )方法的原因是在于，如果子线程里要进行大量耗时的运算，主线程可能往往在自线程结束之前结束。所以需要父线程等待子线程执行结束

#### 3.3 wait( )方法

wait( long timeout)方法是Object类的普通方法。timeout为等待时间，单位为毫秒，wait方法也可以无参，此时默认默认执行wait( 0 )

有两点需要注意：

- wait方法会把当前线程持有的锁释放
- wait方法需要和notify( )、notifyAll( )方法关联执行，而且必须在synchronized(Obj) {…}代码块中执行。执行wait( )方法后线程进入等待阻塞状态，需要notify方法唤醒后才能继续获取对象锁，再次进入Runnale状态。但有一点需要注意的是notify()调用后，并不是马上就释放对象锁的，而是在相应的synchronized(){}语句块执行结束，自动释放锁后，JVM会在wait()对象锁的线程中随机选取一线程，赋予其对象锁，唤醒线程，继续执行。这样就提供了在线程间同步、唤醒的操作。

这里写一个Demo练手：建立三个线程，A线程打印10次A，B线程打印10次B,C线程打印10次C，要求线程同时运行，交替打印10次ABC。

```java
public class ThreadWaitTest implements Runnable {

    private String name;
    private Object prev;
    private Object self;

    private ThreadWaitTest(String name, Object prev, Object self) {
        this.name = name;
        this.prev = prev;
        this.self = self;
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0) {
            synchronized (prev) {
                synchronized (self) {
                    System.out.print(name);
                    count--;

                    self.notify();
                }
                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        ThreadWaitTest pa = new ThreadWaitTest("A", c, a);
        ThreadWaitTest pb = new ThreadWaitTest("B", a, b);
        ThreadWaitTest pc = new ThreadWaitTest("C", b, c);


        new Thread(pa).start();
        Thread.sleep(1000);  //确保按顺序A、B、C执行
        new Thread(pb).start();
        Thread.sleep(1000);
        new Thread(pc).start();
        Thread.sleep(1000);
    }

```

#### 3.4 notify( )方法

notify( )方法是Object类的普通方法，用于唤醒此对象监视器上等待的单个线程。如果当前对象上有多个线程在等待，则会选择唤醒其中一个线程，这种选择是任意性的

#### 3.5 yield( )方法

yield( )方法是Thread类的静态方法，暂停当前正在执行的线程，将其设为Runnable状态，把执行机会让给相同或者更高优先级的线程。当然具体让给哪个进程执行，是由操作系统线程调度器决定，有可能线程调度器再次选择当前进程获得CPU执行权限

#### 3.6 interrupt( )方法

interrupt( )方法是Thread类的普通方法，不要以为它是中断某个线程！它只是线线程发送一个**中断信号**，让线程在无限等待时（如死锁时）能抛出抛出，从而结束线程，但是如果你吃掉了这个异常，那么这个线程还是不会中断的！



----

参考资料

http://blog.csdn.net/evankaka/article/details/44153709#t2



## 【JAVA并发学习四】volatile分析

关键字volatile可以说是Java虚拟机提供的最轻量级的同步机制，但是它并不容易完全正确、完整的理解。了解volatile变量的语义对后面了解多线程操作的其他特性很有意义。

### 一 理解volatile

volatile用来定义变量用的，当一个变量被定义为volatile后，他将具备两种特性：

- 保证可见性，但不保证原子性：保证此变量对所有线程的可见性，这里可见性指当一条线程修改了这个变量的值，新值对于其他线程来说是可以**立即**得知的，但是不保证原子性例如自增操作（num++）
- 禁止指令重排序优化

这两个特性保证了volatile实现最轻量级的同步机制，下面会仔细讲解，这两个特性是如何实现的

### 二 保证可见性

JMM通过两个操作实现volatile变量的可见性：

- 对volatile变量进行操作后，会立即将变量的变动写入主内存中
- 本地内存中访问volatile变量之前，都必须要先从主内存中更新该变量的值

### 三 不保证原子性

当volatile变量参与的运算是非原子性时，导致volatile变量的运算在并发下一样是不安全的。例如对于volatile int num变量，进行num++操作，由于自增操作不具有原子性，所以导致对于volatile变量的自增操作时线程不安全的

### 四 禁止重排序

volatile通过**内存屏障**实现对重排序的禁止，我们通过一个双锁检测实现单例模式的例子来讲解：

```java
public class Singleton {

    private volatile static Singleton instance;

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
    }
}
```

这段代码中instanse是volatile变量，我们先假设下如果instance不是volatile变量时会发生什么。结论是在多线程并发情况下会发生错误，具体原因就在于instance = new Singleton();这行代码可以分为三行

```java
memory = allocate();           //1：分配对象所需的堆空间
ctorInstance(memory);          //2：初始化对象
instance = memory;             //3：让instance指向对象的内存地址
```

JVM在真正执行上面代码时，可能会进行重排序，如果将2、3执行顺序进行调换。有个A、B两个线程，A线程执行完1、3后CPU的执行权被收回从而进入Runnable状态，B线程开始运行，此时instace变量为null，B创建了一个Single对象。B执行完A继续执行2，这是A也创建了另一个Single对象，导致出错。

可以发现出错的原因在于2、3的执行顺序被重排序了。要防止这个错误可以通过禁止2、3重排序，volatile就可以做到。当instance是volatile变量时，会在3的位置设置内存屏障，保证在3之前的代码在3执行之前都已经执行完毕，3之后的代码在3执行后才能执行。

通过查看编译后的文件，可以发现第三句编译成了 0x01a3de24: lock addl $0x0,(%esp)，关键就在于这个lock前缀，他的作用在于是的本CPU的Cache写入内存，并使得别的CPU中的Cache无效，使得volatile变量instance的修改被其他CPU立即可见。当把Cache中修个的变量同步至内存时，意味着所有之前的操作都已经执行完成，这样便形成了“指令重排序无法超越内存屏障”的效果

### 五 总结

volatile可以说是Java提供的最轻量级的同步机制，他可以保证变量的可见性，但不保证原子性，同时能够禁止重排序



## 【JAVA并发学习五】synchronized分析

synchronized关键字是Java中实现线程同步主要方式之一。在java中每一个对象都有一个监视器（相当于一个隐式的锁），这个synchronized就是就是在这个监视器上实现的

### 一 synchronized使用方法及作用域 

synchronized一共有三种使用方法，在讨论他的使用方法时，必须要关注他的**作用域**，所谓作用域就是他**获取的锁对象的范围**

#### 1.1 普通方法

在普通方法前使用synchronized关键字，可以防止多个线程访问这个对象的synchronized方法，这时作用域就是当前的对象，如果一个对象有多个synchronized对象，只要有一个线程访问了这个对象中的一个synchronized方法，其他线程不能同时访问这个对象中任何一个synchronized方法。

这里要注意的是，synchronized关键字是不能继承的，也就是说，基类的方法synchronized f( ){ } 在继承类中并不自动是synchronized f( ){ }，而是变成了f( ){ }。

#### 1.2 静态方法

在静态方法前使用synchronized关键字，可以防止多个线程同时访问这个类中的synchronized static 方法。它可以对类的所有对象实例起作用。 相当于获得了这个类对应的Class对象的锁

#### 1.3 代码块

synchronized关键字还可以使用在代码块中，表示只对这个区块的资源实行互斥访问。他的作用域就是当前的obj对象

```java
synchronized(obj) {
  	xxxxxxx
}
```

### 二 实现原理

首先，我们先看一个例子

```java
public class SynchronizedTest {
  	private static int n;
  	public static void main(String[] args) {
      	synchronzied (SynchronizedTest.class) {
          	n++;
      	}
  	}
}
```

然后使用javap-v SynchronizedTest.class命令查看class文件，会发现以下几行

```java
public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=3, args_size=1
         0: ldc           #2                  
         2: dup
         3: astore_1
         4: monitorenter					//获取这个对象的监视器
         5: getstatic     #3                  
         8: iconst_1
         9: iadd
        10: putstatic     #3                  
        13: aload_1
        14: monitorexit						//释放对象的监视器
        15: goto          23
        18: astore_2
        19: aload_1
        20: monitorexit
        21: aload_2
        22: athrow
        23: return
                  
public synchronized void fun();
    descriptor: ()V
    flags: ACC_PUBLIC, ACC_SYNCHRONIZED    //将同步方法加锁
    Code:
```

可以发现对于同步代码块和同步方法的实现方式是不同的

#### 2.1 同步方法

synchronized方法则会被翻译成普通的方法调用和返回指令，在VM字节码层面并没有任何特别的指令来实现被synchronized修饰的方法，而是在Class文件的**方法表**中将该方法的access_flags字段中的synchronized标志位置1，表示该方法是同步方法并使用调用该方法的对象或该方法所属的Class在JVM的内部对象表示class做为锁对象

#### 2.2 同步代码块

monitorenter指令插入到同步代码块的开始位置，monitorexit指令插入到同步代码块的结束位置，JVM需要保证每一个monitorenter都有一个monitorexit与之相对应。任何对象都有一个monitor与之相关联，当且一个monitor被持有之后，他将处于锁定状态。线程执行到monitorenter指令时，将会尝试获取对象所对应的monitor所有权，即尝试获取对象的锁；

其中4和14是重点，当程序运行到synchronized时会根据对应的作用域，去获取对象的监视器。当synchronized执行完毕（也就是执行到反括号处）后会释放监视器。java中每个对象都有自己的监视器，当这个对象由同步块或者这个对象的同步方法调用时，执行方法的线程必须先获取到该对象的监视器才能进入同步块或者同步方法。如果这是监视器被其他线程所持有时，该线程会进入同步队列，并进入阻塞状态。如下图所示：

![Java并发学习_对象、监视器、同步队列和执行线程间](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_对象、监视器、同步队列和执行线程间.png)

#### 2.3 synchronized的锁是什么

我们知道利用synchronized实现同步的基础是：Java中每个对象都可以作为锁。当线程访问同步代码时，首先必须得到锁，退出或抛出异常时必须释放锁。那这个锁到底在哪，锁里都存着什么东西？

这个每个对象都有的锁，就存在Java对象头里。Hotspot虚拟机的对象头主要包括两部分数据：Mark Word（标记字段）、Klass Pointer（类型指针）。其中Klass Point是是对象指向它的类元数据的指针，虚拟机通过这个指针来确定这个对象是哪个类的实例，Mark Word用于存储对象自身的运行时数据，锁信息就存在这里。Mark Word被设计成一个非固定的数据结构以便在极小的空间内存存储尽量多的数据，它会根据对象的状态复用自己的存储空间，也就是说，Mark Word会随着程序的运行发生变化，变化状态如下（32位虚拟机）： 

![Java并发学习_对象头](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_对象头.png)



### 三 锁优化



---

参考资料

http://blog.csdn.net/u012465296/article/details/53022317



## 【JAVA并发学习六】线程间通信

线程开始运行，拥有自己的栈空间，就如同一个脚本一样，按照既定的代码一步一步地执行，直到终止。线程间通信就是通过一些手段让一个进程中的多个线程能够相互配合的完成工作。

### 一 volatile和synchronized关键字

Java支持多个线程同时访问一个对象或者对象的成员变量，由于每个线程可以拥有这个变量的拷贝（虽然对象以及成员变量分配的内存是在共享内存中的，但是每个执行的线程还是可以拥有一份拷贝，这样做的目的是加速程序的执行，这是现代多核处理器的一个显著特性），所以程序在执行过程中，一个线程看到的变量并不一定是最新的

#### 1.1 volatile

关键字volatile可以用来修饰字段（成员变量），就是告知程序任何对该变量的访问均需要从共享内存中获取，而对它的改变必须同步刷新回共享内存，它能保证所有线程对变量访问的可见性

#### 1.2 synchronized

关键字synchronized可以修饰方法或者以同步块的形式来进行使用，它主要确保多个线程在同一个时刻，只能有一个线程处于方法或者同步块中，它保证了线程对变量访问的可见性和排他性

### 二 等待/通知机制

等待/通知机制就是一个线程修改了一个对象的值，而另一个线程感知到了变化，然后进行相应的操作，整个过程开始于一个线程，而最终执行又是另一个线程。前者是生产者，后者就是消费者，这种模式隔离了“做什么”（what）和“怎么做”（How），在功能层面上实现了解耦，体系结构上具备了良好的伸缩性

等待/通知机制有两种实现方式：Object对象和Lock接口

#### 2.1 基于Object对象实现

等待/通知机制，是指一个线程A调用了对象O的wait()方法进入等待状态，而另一个线程B调用了对象O的notify()或者notifyAll()方法，线程A收到通知后从对象O的wait()方法返回，进而执行后续操作。上述两个线程通过对象O来完成交互，而对象上的wait()和notify/notifyAll( )的关系就如同开关信号一样，用来完成等待方和通知方之间的交互工作

Object类中有几个普通方法用来实现等待/通知机制：

- wait( ) 

  使线程进入WAITING状态，调用wait( )方法后会释放对象的锁；相当于调用了wait( 0 )。调用wait( )后，线程进入等待队列WaitQueue

- wait(long) 

  超时等待，时间单位是毫秒

- wait(long, int) 对于超时时间的控制可以达到纳秒级别

- notify( ) 

  A线程调用norify( )方法，将等待队列中的一个等待线程B从等待队列中移到同步队列中，线程状态从WAITING变为BLOCKED，此时线程B还没有从wait( )方法返回，需要等到线程A释放锁后，B才有机会从同步队列出来（此时wait( )方法返回）

- notifyAll( ) 

  将等待队列中所有的线程全部移到同步队列

状态转换过程如下图所示：



#### 2.2 基于current包中Condition类



### 三 管道输入/输出流

管道输入/输出流主要用于线程之间的数据传输，而传输的媒介为内存。管道输入/输出流主要包括了如下4种具体实现：PipedOutputStream、PipedInputStream、PipedReader和PipedWriter，前两种面向字节，而后两种面向字符。

### 四 Thread对象join( )方法

如果一个线程A执行了thread.join()语句，其含义是：当前线程A等待thread线程终止之后才从thread.join()返回。线程Thread除了提供join()方法之外，还提供了join(long millis)和join(long millis,int nanos)两个具备超时特性的方法。这两个超时方法表示，如果线程thread在给定的超时时间里没有终止，那么将会从该超时方法中返回。 

join( )方法是基于wait( )方法实现的

### 五 ThreadLocal

ThreadLocal，即线程变量，是一个以ThreadLocal对象为键、任意对象为值的存储结构。这个结构被附带在线程上，也就是说一个线程可以根据一个ThreadLocal对象查询到绑定在这个线程上的一个值





## 【JAVA并发学习七】Lock接口和队列同步器AQS

我们经常提到锁的概念，在Java一共有两种锁：synchronized锁和Lock锁

- synchronized锁

  这种锁通过synchronized关键字获得/释放，它存在于Java堆中对象的对象头中，所以这种锁是每一个Java对象都有的

- Lock锁

  Lock锁是一个实现Lock接口的类，在使用Lock锁的类中创建一个Lock的对象作为静态私有属性，进行使用

### 一 Java.Util.concurrent包

[Java](http://lib.csdn.net/base/java).util.concurrent 包含许多线程安全、[测试](http://lib.csdn.net/base/softwaretest)良好、高性能的并发构建块。可以说，创建java.util.concurrent 的目的就是要实现 Collection 框架对[数据结构](http://lib.csdn.net/base/datastructure)所执行的并发操作。通过提供一组可靠的、高性能并发构建块，开发人员可以提高并发类的线程安全、可伸缩性、性能、可读性和可靠性。

此包包含locks,concurrent,atomic 三个包。

- Atomic：原子数据的构建。
- Locks：基本的锁的实现，最重要的AQS框架和lockSupport
- Concurrent：构建的一些高级的工具，如线程池，并发队列等。

其中都用到了CAS（compare-and-swap）操作。CAS 是一种低级别的、细粒度的技术，它允许多个线程更新一个内存位置，同时能够检测其他线程的冲突并进行恢复。它是许多高性能并发算法的基础。在 JDK 5.0 之前，Java 语言中用于协调线程之间的访问的惟一原语是同步，同步是更重量级和粗粒度的。公开 CAS 可以开发高度可伸缩的并发 Java 类。这些更改主要由 JDK 库类使用，而不是由开发人员使用。

CAS操作都封装在java 不公开的类库中，sun.misc.Unsafe。此类包含了对原子操作的封装，具体用本地代码实现。本地的C代码直接利用到了硬件上的原子操作。

### 二 Lock接口

锁是用来控制多个线程访问共享资源的方式，在Java SE5之后并发包中新增了Lock接口来实现锁功能，它提供了与synchronized关键字相似的同步功能，只是在使用时需要显示地获取和释放锁。虽然它缺少了（通过synchronized块或者方法所提供的）隐式获取释放锁的便捷性，但是却拥有了锁获取与释放的可操作性、可中断的获取锁以及超时获取锁等多种synchronized关键字所不具备的同步特性。

与synchronized相比，Lock的有优点有：

- 尝试非阻塞地获取锁

  synchronized是通过阻塞的方式获取锁（改变对象头），而Lock可以非阻塞式地获取锁，通过自旋（死循环）的方式实现非阻塞

- 获得锁的线程能够响应中断

  当获取到锁的线程被中断时，中断异常将会被抛出，同时锁会被释放。通过Thread.interrupted( )实现终端

- 超时获取锁

  在指定的截止时间之前获取锁，如果截止时间到了仍旧无法获取锁，则返回

Lock是一个接口，它定义了锁获取和释放的基本操作，主要API有lock( )、tryLock( )、unLock( )

### 三 队列同步器AQS

队列同步器AbstractQueuedSynchronizer，是用来构建锁或者其他同步组件的基础框架。它使用了一个int成员变量**state**表示同步状态，通过内置的**FIFO队列**来完成资源获取线程的排队工作。

#### 3.1 同步器和锁的关系

同步器是实现锁的关键，在锁的实现中聚合同步器，利用同步器实现锁的语义。可以这样理解二者之间的关系：锁是面向使用者的，它定义了使用者与锁交互的接口（比如可以允许两个线程并行访问），隐藏了实现细节；同步器面向的是锁的实现者，它简化了锁的实现方式，屏蔽了同步状态管理、线程的排队、等待与唤醒等底层操作。锁和同步器很好地隔离了使用者和实现者所需关注的领域。

#### 3.2 state

在同步器中定义了一个非常关键的属性state，它的值就代表了所的状态：等于0时说明没有任何线程持有这个锁，此时锁时可用的；大于0时代表锁已经被线程占用，具体的数字代表同一个线程重入的次数或者同时占用这个锁的线程数量

```java
/**
 * The synchronization state.
 */
private volatile int state;
```

可以看到state是用volatile关键字修饰的，保证对state状态的改变是保证可见性的，并且通过CAS保证state状态改变的原子性

#### 3.3 同步队列和Node

同步器依赖内部的同步队列（一个FIFO双向队列）来完成同步状态的管理，这个FIFO双向队列就是基于Node实现的，每一个Node对一个线程。当前线程获取同步状态失败时，同步器会将当前线程以及等待状态等信息构造成为一个节点（Node）并将其加入同步队列，同时会阻塞当前线程，当同步状态释放时，会把首节点中的线程唤醒，使其再次尝试获取同步状态

##### （1）Node

Node节点结构如下：

```java
volatile Thread thread;        //该节点代表的线程
volatile Node prev;            //上一节点
volatile Node next;            //下一节点
Node nextWaiter;               //等待队列中的后继节点
volatile int waitStatus;       //等待状态
```

##### （2）同步队列

在AQS中定义了同步队列的头和尾，同步队列的结构如下图。

```java
private transient volatile Node head;
private transient volatile Node tail;
```

![Java并发学习_同步队列](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_同步队列.png)

当一个线程获取同步状态（锁）失败时，将生成一个新的Node节点，并插入同步队列的尾部，加入队列的过程通过CAS操作保证线程安全

![Java并发学习_同步队列加入新节点](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_同步队列加入新节点.png)

同步队列遵循FIFO，首节点时正在获取同步状态的线程，首节点的线程释放同步状态时，将会唤醒后集节点，后继节点在获取同步状态成功后，会将自己设置为首节点。设置首节点是通过获取同步状态成功的线程来完成的，由于只有一个线程能够成功获取到同步状态，因此设置头节点的方法并不需要使用CAS来保证，它只需要将首节点设置成为原首节点的后继节点并断开原首节点的next引用即可。

![Java并发学习_同步队列设置首节点](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_同步队列设置首节点.png)

#### 3.4 同步状态获取与释放 

在AQS中一共有三种同步状态获取与释放的方式：独占式同步状态获取与释放、共享式同步状态获取和超时获取同步状态

##### （1）独占式同步状态获取与释放

独占式就是同一个时刻只能有一个线程获取到锁，而其他获取锁的线程只能处于同步队列中等待。

通过调用同步器的acquire(int arg)方法可以获取同步状态，该方法对中断不敏感，也就是
由于线程获取同步状态失败后进入同步队列中，后续对线程进行中断操作时，线程不会从同
步队列中移出

整个获取过程涉及到的方法如下：

- acquire(int arg)   

  获取同步状态，未获得时创建新节点加入同步队列中，并进入acquireQueued方法的自旋（死循环）中不断尝试获取同步状态。当线程从acquire方法返回时，说明当前线程已经获取了锁（但当前线程不一定生成了Node节点，因为可能跳过执行addWaiter方法）

- tryAcquire(int arg)

  继承AQS的子类会重写tryAcquire方法，判断是否能够获取同步状态，获取成功后更改state值

- addWaiter(Node node)

  将新节点加入同步队列尾部，通过enq(final Node node)方法中的“死循环”来保证节点的正确添加

- acquireQueued(Node node, int arg)

  获取同步状态失败后，不停的自旋尝试获取同步状态，检查prev节点是否头节点和调用tryAcquire(int arg)尝试获取同步状态

- release(int arg)

  用于释放同步状态，并唤醒其后继节点。通过LockSupper类的unpark(Thread thread)方法唤醒后继节点

整个获取的过程如下图所示

![Java并发学习_独占式同步状态获取](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_独占式同步状态获取.png)

acquire方法和release方法

```java
public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}

public final boolean release(int arg) {
    if (tryRelease(arg)) {
        Node h = head;
        if (h != null && h.waitStatus != 0)
            unparkSuccessor(h);
        return true;
    }
    return false;
}
```

总结：在获取同步状态时，同步器维护一个同步队列，获取状态失败的线程都会被加入到队列中并在队列中进行自旋；移出队列（或停止自旋）的条件是前驱节点为头节点且成功获取了同步状态。在释放同步状态时，同步器调用tryRelease(int arg)方法释放同步状态，然后唤醒头节点的后继节点

##### （2）共享式同步状态获取与释放

共享式获取与独占式获取最主要的区别在于同一时刻能否有多个线程同时获取到同步状态。允许多个线程同时访问资源的场景有很多：以文件的读写为例，如果一个程序在对文件进行读操作，那么这一时刻对于该文件的写操作均被阻塞，而读操作能够同时进行，而且允许多个线程同时读取。

共享式同步主要涉及的方法与独占式是对应的

- acquireShared(int arg)   

  获取同步状态，未获得时创建新节点加入同步队列中。当线程从acquire方法返回时，说明当前线程已经获取了锁

- tryAcquireShared(int arg)

  继承AQS的子类会重写tryAcquireShared方法，判断是否能够获取同步状态，获取成功后更改state值

- doAcquireShared(Node node, int arg)

  获取同步状态失败后，在同步队列中添加新节点。不停的自旋尝试获取同步状态，检查prev节点是否头节点和调用tryAcquireShared(int arg)尝试获取同步状态

- addWaiter(Node node)

  将新节点加入同步队列尾部，通过enq(final Node node)方法中的“死循环”来保证节点的正确添加

- releaseShared(int arg)

  用于释放同步状态，并唤醒其后继节点。通过LockSupper类的unpark(Thread thread)方法唤醒后继节点

##### （3）独占式超时获取同步状态

通过调用同步器的doAcquireNanos(int arg,long nanosTimeout)方法可以超时获取同步状态，即在指定的时间段内获取同步状态，如果获取到同步状态则返回true，否则，返回false。该方法提供了传统Java同步操作（比如synchronized关键字）所不具备的特性。超时获取同步状态的过程图如下

![Java并发学习_独占式超时获取同步状态](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_独占式超时获取同步状态.png)

总结来说独占式超时获取同步状态有一下的特点：

- 独占式
- 超时返回
- 获取同步状态过程支持中断



## 【JAVA并发学习八】重入锁ReentrantLock

重入锁ReentrantLock，顾名思义，就是支持重进入的锁，它表示该锁能够支持一个线程对
资源的重复加锁。synchronized也支持重入，不过两个的实现方式不同。synchronized关键字隐式的支持重进入，比如一个synchronized修饰的递归方法，在方法执行时，执行线程在获取了锁之后仍能连续多次地获得该锁。除此之外，该锁的还支持获取锁时的公平和非公平性选择。

### 一 实现重入

#### 1.1 特性

重进入是指任意线程在获取到锁之后能够再次获取该锁而不会被锁所阻塞，该特性的实
现需要解决以下两个问题

- 线程再次获取锁

  锁需要判断获取锁的线程是否当前占据锁的线程，每进入一次state加1

- 锁的最终释放

  线程重复n次获取了锁，随后在第n次释放该锁后，其他线程能够获取到该锁。锁的最终释放要求锁对于获取进行计数自增，计数表示当前锁被重复获取的次数，而锁被释放时，计数自减，当计数等于0时表示锁已经成功释放

#### 1.2 ReentrantLock类结构



#### 1.3 实现

ReentrantLock类通过重写tryAcquire( )和tryRelease( )方法，比如ReentrantLock中的公平锁NonfairSync中tryAcquire( )方法为例

```java
final void lock() {
    if (compareAndSetState(0, 1))
        setExclusiveOwnerThread(Thread.currentThread());
    else
        acquire(1);
}

public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}

protected final boolean tryAcquire(int acquires) {
    return nonfairTryAcquire(acquires);
}
	
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
  	//state不为0时，判断获取锁的线程是不是当前线程
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}

protected final boolean tryRelease(int releases) {
    int c = getState() - releases;
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    boolean free = false;
    if (c == 0) {
        free = true;
        setExclusiveOwnerThread(null);
    }
    setState(c);
    return free;
}       
```

### 二 公平与非公平锁区别

如果在绝对时间上，先对锁进行获取的请求一定先被满足，那么这个锁是公平的，反之，是不公平的。公平的获取锁，也就是等待时间最长的线程最优先获取锁，也可以说锁获取是顺序的

公平性与否是针对获取锁而言的，如果一个锁是公平的，那么锁的获取顺序就应该符合
请求的绝对时间顺序，也就是FIFO

#### 2.1 实现区别

公平锁通过在重写tryAcquire( )方法，hasQueuedPredecessors( )来判断该节点是否有先驱节点

```java
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (!hasQueuedPredecessors() &&
            compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```

#### 2.2 性能区别

公平性锁保证了锁的获取按照FIFO原则，而代价是进行大量的线程切换。非公平性锁虽
然可能造成线程“饥饿”，但极少的线程切换，保证了其更大的吞吐量。我们做一个实验来验证

```java
package thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Created by Xiaoqi.zcl
 * @Date 2017/8/19
 * @Time 下午7:34
 */

public class FairAndUnfairTest {
    private static ReentrantLock2 fairLock = new ReentrantLock2(true);
    private static ReentrantLock2 unfairLock = new ReentrantLock2(false);
    
    public static void main(String[] args) {
        //公平锁
        testLock(fairLock);

        //非公平锁
        //testLock(unfairLock);
    }

    private static void testLock(ReentrantLock2 lock) {
        Job job1 = new Job(lock, "1");
        Job job2 = new Job(lock, "2");
        Job job3 = new Job(lock, "3");
        Job job4 = new Job(lock, "4");
        Job job5 = new Job(lock, "5");

        job1.start();
        job2.start();
        job3.start();
        job4.start();
        job5.start();
    }

    private static class Job extends Thread {
        private ReentrantLock2 lock;

        public Job(ReentrantLock2 lock, String name) {
            this.lock = lock;
            setName(name);
        }

        public void run() {
            for (int i=1; i<3; i++) {
                lock.lock();
                System.out.println("获得锁的线程：" + Thread.currentThread().getName() +
                    "    同步队列中的线程：" + lock.getQueuedThreadNames());
                lock.unlock();
            }
        }
    }


    private static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public String getQueuedThreadNames() {
            ArrayList<Thread> queuedThreads = (ArrayList<Thread>)super.getQueuedThreads();
            String blockThreads = "";
            for (int i=queuedThreads.size()-1; i>=0; i--) {
                blockThreads += queuedThreads.get(i).getName() + " ";
            }
            return blockThreads;
        }
    }
}
```

我们执行main( )方法，分别进行公平锁和非公平锁的测试，

```
公平锁打印结果：
获得锁的线程：1    同步队列中的线程：2 3 4 5
获得锁的线程：2    同步队列中的线程：3 4 5 1 
获得锁的线程：3    同步队列中的线程：4 5 1 2 
获得锁的线程：4    同步队列中的线程：5 1 2 3 
获得锁的线程：5    同步队列中的线程：1 2 3 4 
获得锁的线程：1    同步队列中的线程：2 3 4 5 
获得锁的线程：2    同步队列中的线程：3 4 5 
获得锁的线程：3    同步队列中的线程：4 5 
获得锁的线程：4    同步队列中的线程：5 
获得锁的线程：5    同步队列中的线程：

非公平锁打印结果
获得锁的线程：1    同步队列中的线程：2 3 4 5
获得锁的线程：1    同步队列中的线程：2 3 4 5
获得锁的线程：2    同步队列中的线程：3 4 5
获得锁的线程：2    同步队列中的线程：3 4 5
获得锁的线程：3    同步队列中的线程：4 5
获得锁的线程：3    同步队列中的线程：4 5
获得锁的线程：4    同步队列中的线程：5
获得锁的线程：4    同步队列中的线程：5
获得锁的线程：5    同步队列中的线程：
获得锁的线程：5    同步队列中的线程：
```

观察结果，公平性锁每次都是严格从同步队列中的第一个节点获取到锁，而非公平性锁出现了一个线程连续获取锁的情况。为什么会出现线程连续获取锁的情况呢？回顾nonfairTryAcquire(int acquires)方法，当一个线程请求锁时，只要获取了同步状态即成功获取锁。在这个前提下，刚释放锁的线程再次获取同步状态的几率会非常大，使得其他线程只能在同步队列中等待。

tips：

- 其实对于非公平锁，进入到同步队列里的线程还是会按照FIFO的顺序进行同步，这是因为AQS中的acquireQueued( )方法中final Node p = node.predecessor();if (p == head && tryAcquire(arg))会判断当前Node中prev节点是否是头节点。所以非公平锁的非公平体现在tnonfairTryAcquire(int acquires)方法执行中
- 上面的代码可能每次的运行结果不一样，这是因为CPU的执行速度不一样，在执行job1.start();job2.start();时，job1线程已经完全执行完了，但是job2线程还没开始，可以把run( )中的for循环次数调多点，就能看到效果了。 



## 【JAVA并发学习九】读写锁

ReentrantLock是排他锁，在同一时刻只允许一个线程进行访问，而读写锁在同一时刻可以允许多个读线程访问，但是在写线程访问时，所有的读线程和其他写线程均被阻塞。读写锁维护了一对锁，一个读锁和一个写锁，通过分离读锁和写锁，使得并发性相比一般的排他锁有了很大提升

### 一 读写锁的逻辑

通过维护读、写两个锁，来完成读写的同步控制。读写操作和读写锁的逻辑是这样的，在读操作时获取读锁，写操作时获取写锁，读锁可以多个线程同时访问，写锁只能同时又一个线程访问。当写锁被获取到时，后续（非当前写操作线程）的读写操作都会被阻塞，写锁释放之后，所有操作继续执行，编程方式相对于使用等待通知机制的实现方式而言，变得简单明了

### 二 读写锁的结构和接口

一般情况下，读写锁的性能都会比排它锁好，因为大多数场景读是多于写的。在读多于写的情况下，读写锁能够提供比排它锁更好的并发性和吞吐量。Java并发包提供读写锁的实现是ReentrantReadWriteLock

#### 2.1 ReentrantReadWriteLock结构



#### 2.2 接口

ReadWriteLock仅定义了获取读锁和写锁的两个方法，即readLock()方法和writeLock()方法，而其实现——ReentrantReadWriteLock，除了接口方法之外，还提供了一些便于外界监控其内部工作状态的方法

![Java并发学习_读写锁API](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_读写锁API.png)

### 三 读写锁的实现

接下来分析ReentrantReadWriteLock的实现，主要包括：读写状态的设计、写锁的获取与释
放、读锁的获取与释放以及锁降级

#### 3.1 读写状态设计

读写锁同样依赖AQS实现，通过AQS中的state进行锁状态标记，不同于ReentrantLock，读写锁需要维护读、写两个状态。实现的方式就是将int类型的state进行“按位切割使用”，将state分别两部分，低16位代表写状态，高16位代表读状态，源码中通过位运算可以实现高效的读写锁状态控制

![Java并发学习_读写锁状态](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_读写锁状态.png)

#### 3.2 写锁状态获取与释放

写锁是一个支持重入的排它锁。写锁的获取，ReentrantReadWriteLock类中有private final ReentrantReadWriteLock.WriteLock writerLock;属性，调用writerLock.lock( )方法，通过ReentrantWriteLock中Sync类中tryAcquire( )方法实现

```java
public void lock() {
    sync.acquire(1);
}

public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}

protected final boolean tryAcquire(int acquires) {
    Thread current = Thread.currentThread();
    int c = getState();
    int w = exclusiveCount(c);
    if (c != 0) {
        // 存在读锁或者当前获取线程不是已经获取写锁的线程
        if (w == 0 || current != getExclusiveOwnerThread())
            return false;
        if (w + exclusiveCount(acquires) > MAX_COUNT)
            throw new Error("Maximum lock count exceeded");
        setState(c + acquires);
        return true;
    }
    if (writerShouldBlock() ||
        !compareAndSetState(c, c + acquires))
        return false;
    setExclusiveOwnerThread(current);
    return true;
}
```

该方法除了重入条件（当前线程为获取了写锁的线程）之外，增加了一个读锁是否存在的
判断。如果存在读锁，则写锁不能被获取，原因在于：读写锁要确保写锁的操作对读锁可见，如果允许读锁在已被获取的情况下对写锁的获取，那么正在运行的其他读线程就无法感知到当前写线程的操作。因此，只有等待其他读线程都释放了读锁，写锁才能被当前线程获取，而写锁一旦被获取，则其他读写线程的后续访问均被阻塞。

#### 3.3 读锁状态获取

读锁是一个支持重进入的共享锁，它能够被多个线程同时获取，在没有其他写线程访问（或者写状态为0）时，读锁总会被成功地获取，而所做的也只是（线程安全的）增加读状态。如果当前线程已经获取了读锁，则增加读状态。如果当前线程在获取读锁时，写锁已被其他线程获取，则进入等待状态

```java
public void lock() {
    sync.acquireShared(1);
}

public final void acquireShared(int arg) {
    if (tryAcquireShared(arg) < 0)
        doAcquireShared(arg);
}

protected final int tryAcquireShared(int unused) {
    for (;;) {
        int c = getState();
        int nextc = c + (1 << 16);
        if (nextc < c)
            throw new Error("Maximum lock count exceeded");
        if (exclusiveCount(c) != 0 && owner != Thread.currentThread())
            return -1;
        if (compareAndSetState(c, nextc))
            return 1;
    }
}
```

在tryAcquireShared(int unused)方法中，如果其他线程已经获取了写锁，则当前线程获取读锁失败，进入等待状态。如果当前线程获取了写锁或者写锁未被获取，则当前线程（线程安全，依靠CAS保证）增加读状态，成功获取读锁

#### 3.4 锁降级

锁降级指的是写锁降级成为读锁。如果当前线程拥有写锁，然后将其释放，最后再获取读锁，这种分段完成的过程不能称之为锁降级。锁降级是指把持住（当前拥有的）写锁，再获取到读锁，随后释放（先前拥有的）写锁的过程



## 【JAVA并发学习十】Codition接口

任意一个Java对象，都拥有一组监视器方法（定义在java.lang.Object上），主要包括wait()、wait(long timeout)、notify()以及notifyAll()方法，这些方法与synchronized同步关键字配合，可以实现等待/通知模式。

Condition接口也提供了类似Object的监视器方法，与Lock配合可以实现等待/通知模式，但是这两者在使用方式以及功能特性上还是有差别的。

### 一 Object的监视器方法与Condition对比

在AQS中有一个子类ConditionObject实现了java.util.concurrent.locks.Condition接口。我们先来对比下Object监视器和Condition的区别

![Java并发学习_condition对比](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_condition对比.png)

### 二 Condition接口

#### 2.1 Condition对象的获取

Condition对象是由Lock对象调用Lock对象的newCondition()方法创建出来的，这个newCondition( )会调用AQS中ConditionObject类的构造函数，最终生成一个ConditionObject对象。AQS中子类ConditionObject实现了java.util.concurrent.locks.Condition接口。所以说Condition是依赖Lock存在的

#### 2.2 Condition接口方法

Condition中主要有wait和notify两种类型的方法，功能上和Object类的方法基本相同

![Java并发学习_Condition方法](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_Condition方法.png)

### 三 Condition实现

ConditionObject是同步器AbstractQueuedSynchronizer的内部类，因为Condition的操作需要获取相关联的锁，所以作为同步器的内部类也较为合理。每个Condition对象都包含着一个**等待队列**（也是基于AQS中Node实现的），该队列是Condition对象实现等待/通知功能的关键

#### 3.1 等待队列

等待队列是一个FIFO的队列，在队列中的每个节点都包含了一个线程引用，该线程就是在Condition对象上等待的线程，如果一个线程调用了Condition.await()方法，那么该线程将会释放锁、构造成节点加入等待队列并进入等待状态。事实上，节点的定义复用了同步器中节点的定义，也就是说，同步队列和等待队列中节点类型都是同步器的静态内部类AbstractQueuedSynchronizer.Node

在Object的监视器模型上，一个对象拥有一个同步队列和等待队列，而并发包中的Lock（更确切地说是同步器）拥有一个同步队列和**多个**等待队列

![Java并发学习_等待队列](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_等待队列.png)

#### 3.2 等待

调用Condition的await()方法（或者以await开头的方法），会使当前线程进入等待队列并释放锁，同时线程状态变为等待状态。当从await( )方法返回时，当前线程**一定获取了Condition相关联的锁**

如果从队列（同步队列和等待队列）的角度看await( )方法，当调用await()方法时，相当于同步队列的首节点（获取了锁的节点）移动到Condition的等待队列中

![Java并发学习_加入等待队列](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_加入等待队列.png)

如图所示，同步队列的首节点并不会直接加入等待队列，而是通过addConditionWaiter()方法把当前线程构造成一个新的节点并将其加入等待队列中

#### 3.3 通知

调用Condition的signal()方法，将会唤醒在等待队列中等待时间最长的节点（首节点），在唤醒节点之前，会将节点移到同步队列中。

调用该方法的前置条件是当前线程必须获取了锁，可以看到signal()方法进行了isHeldExclusively()检查，也就是当前线程必须是获取了锁的线程。接着获取等待队列的首节点，将其移动到同步队列并使用LockSupport唤醒节点中的线程。

![Java并发学习_等待队列唤醒](/Users/ethan/Study/Github/HZ-study/MarkDown/后端/Java并发学习_等待队列唤醒.png)

被唤醒后的线程，将从await()方法中的while循环中退出（isOnSyncQueue(Node node)方法返回true，节点已经在同步队列中），进而调用同步器的acquireQueued()方法加入到获取同步状态的竞争中。

成功获取同步状态（或者说锁）之后，被唤醒的线程将从先前调用的await()方法返回，此时该线程已经成功地获取了锁

Condition的signalAll()方法，相当于对等待队列中的每个节点均执行一次signal()方法，效果就是将等待队列中所有节点全部移动到同步队列中，并唤醒每个节点的线程



















## 





## 锁

Lock、ReentrantLock、Condition、ReadWriteLock、LockSupport



##  线程间数据共享的实现



 （十三）—–信号量 http://cmsblogs.com/?p=1318

----

Q:

- 对于普通变量合适会将本地内存中的备份同步至主内存中
- 普通的锁对象Lock和synchronized的锁有什么不同
- 内部类可以变相的继承两个父类
- 线程间数据共享？


---

resource

java多线程基础

http://blog.csdn.net/evankaka/article/details/44153709#t9



java.util.concurrent包

http://blog.csdn.net/windsunmoon/article/details/36903901