/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package aop;

/**
 * @author ethan
 * @version $Id: HelloServiceImpl.java, v 0.1 2017年07月16日 上午11:52 ethan Exp $
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public void sayHello(String str){
        System.out.println("Hello , " + str);
    }
}
