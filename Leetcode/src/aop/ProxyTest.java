/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package aop;

/**
 * @author ethan
 * @version $Id: ProxyTest.java, v 0.1 2017年07月16日 上午11:49 ethan Exp $
 */
public class ProxyTest {
    public static void main(String[] args) {
        HelloServiceProxy proxy = new HelloServiceProxy();
        HelloService service = new HelloServiceImpl();

        //绑定代理对象。
        service = (HelloService) proxy.bind(service, new Class[] {HelloService.class});

        //这里service经过绑定，就会进入invoke方法里面了。
        //当调用委托类的方法是会自动执行代理类中方法
        service.sayHello("张三");
    }
}