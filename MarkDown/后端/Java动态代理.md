### Java动态代理

---

#### 代理模式

代理模式是一种常用的设计模式，其目的就是为其他对象提供一个代理以控制对某个真实对象的访问。代理类负责为委托类预处理消息，过滤消息并转发消息，以及进行消息被委托类执行后的后续处理。通过代理层这一中间层，有效的控制对于真实委托类对象的直接访问
#### 代理类&委托类

所谓动态代理，就是在程序运行时，运用反射机制动态创建代理类。

**委托类**：被代理的对象(target)，必须是一个接口，因为代理类的父类是java.lang.Proxy，java是单继承，所以必须是接口。

**代理类：**Proxy是所有由Proxy创建的动态代理类的父类，这也算是java动态代理的一处缺陷，java不支持多继承，所以无法实现对class的动态代理，只能对于Interface的代理。**而且该类还实现了其所代理的一组接口，这就是为什么它能够被安全地类型转换到其所代理的某接口的根本原因。**生成的代理类为public final，不能被继承




#### JDK源码实现（jdk1.6）

- **Java.lang.reflect.Proxy**

  动态代理机制的主类，提供一组静态方法为一组接口动态的生成对象和代理类

- **java.lang.reflect.InvocationHandler**

  调用处理器接口，自定义invokle方法，用于实现对于真正委托类的代理访问。当通过代理对象调用方法时，会先执行invokle

- **java.lang.ClassLoader**

  Proxy类与普通类的唯一区别就是**其字节码是由 JVM 在运行时动态生成的而非预存在于任何一个 .class 文件中**。 
  每次生成动态代理类对象时都需要指定一个类装载器对象：newProxyInstance()方法第一个参数

其中，Proxy类主要用来获取动态代理对象；InvocationHandler接口用来约束调用者实现。

#### 动态代理过程

- 通过实现 InvocationHandler 接口创建自己的调用处理器
- 通过为 Proxy 类指定 ClassLoader 对象和一组 interface 来创建动态代理类
- 通过反射机制获得动态代理类的构造函数，其唯一参数类型是调用处理器接口类型
- 通过构造函数创建动态代理类实例，构造时调用处理器对象作为参数被传入

```java
// InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
// 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用
InvocationHandler handler = new InvocationHandlerImpl(..); 

// 通过 Proxy 为包括 Interface 接口在内的一组接口动态创建代理类的类对象
Class clazz = Proxy.getProxyClass(classLoader, new Class[] { Interface.class, ... }); 

// 通过反射从生成的类对象获得构造函数对象
Constructor constructor = clazz.getConstructor(new Class[] { InvocationHandler.class });

// 通过构造函数对象创建动态代理类实例
Interface Proxy = (Interface)constructor.newInstance(new Object[] { handler })
```

为了简化对象创建过程，Proxy类中的newProxyInstance方法封装了2~4，只需两步即可完成代理对象的创建。

```java
// InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
InvocationHandler handler = new InvocationHandlerImpl(..); 

// 通过 Proxy 直接创建动态代理类实例
Interface proxy = (Interface)Proxy.newProxyInstance( classLoader, new Class[] { Interface.class }, handler );
```



#### Demo

HelloServiceProxy 用于创建代理类对象，实现代理类对象和委托类对象绑定，实现InvocationHandler接口的invoke方法

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ethan
 * @version $Id: HelloServiceProxy.java, v 0.1 2017年07月16日 上午11:47 ethan Exp $
 */
public class HelloServiceProxy implements InvocationHandler{

    private Object target;
    /**
     * 绑定委托对象并返回一个【代理占位】
     * @param target 真实对象
     * @return  代理对象【占位】
     */
    public  Object bind(Object target, Class[] interfaces) {
        this.target = target;
        //取得代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
            target.getClass().getInterfaces(), this);
    }

    @Override
    /**
     * 同过代理对象调用方法首先进入这个方法.
     * @param proxy --代理对象
     * @param method -- 方法,被调用方法.
     * @param args -- 方法的参数
     */
    public Object invoke(Object proxy , Method method, Object[] args) throws Throwable {
        System.err.println("############我是JDK动态代理################");
        Object result = null;
        //反射方法前调用
        System.err.println("我准备说hello。");
        //反射执行方法  相当于调用target.sayHelllo;
        result=method.invoke(target, args);
        //反射方法后调用.
        System.err.println("我说过hello了");
        return result;
    }
}
```

HelloService接口用来作为委托类

```java
/**
 * @author ethan
 * @version $Id: HelloService.java, v 0.1 2017年07月16日 上午11:49 ethan Exp $
 */
public interface HelloService {
    void sayHello(String str);
}
```

HelloServiceImpl是HelloService接口的实现类

```java
public class HelloServiceImpl implements HelloService{
    @Override
    public void sayHello(String str){
        System.out.println("Hello , " + str);
    }
}
```

委托类对象绑定代理类对象后，当委托类对象调用自身方法时，会调用代理类对象实现的invoke( )方法

```java
public class ProxyTest {
    public static void main(String[] args) {
      	//proxy是代理类对象
        HelloServiceProxy proxy = new HelloServiceProxy();
      	//service是委托类对象
        HelloService service = new HelloServiceImpl();

        //生成并绑定代理对象。
        service = (HelloService) proxy.bind(service, new Class[] {HelloService.class});

        //这里service经过绑定，就会进入invoke方法里面了。
        //当调用委托类的方法是会自动执行代理类中方法
        service.sayHello("张三");
    }
}
```

