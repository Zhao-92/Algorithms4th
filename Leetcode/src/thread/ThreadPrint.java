/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package thread;

/**
 * @Author Created by Xiaoqi.zcl
 * @Date 2017/8/8
 * @Time 下午8:11
 */

public class ThreadPrint implements Runnable{
    Object pre;
    Object self;
    String name;

    ThreadPrint(Object pre, Object self, String name) {
        this.name = name;
        this.self = self;
        this.pre = pre;
    }

    @Override
    public void run() {
        int count = 10;
        while (count > 0 ) {
            synchronized (pre) {
                synchronized (self) {
                    System.out.print(name);
                    count--;

                    self.notify();
                }
                try {
                    pre.wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();

        Thread threada = new Thread(new ThreadPrint(c, a, "A"));
        Thread threadb = new Thread(new ThreadPrint(a, b, "B"));
        Thread threadc = new Thread(new ThreadPrint(b, c, "C"));

        threada.start();
        threada.sleep(1000);
        threadb.start();
        Thread.sleep(1000);
        threadc.start();
        Thread.sleep(1000);

    }
}