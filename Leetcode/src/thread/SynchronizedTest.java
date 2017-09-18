/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package thread;

/**
 * @Author Created by Xiaoqi.zcl
 * @Date 2017/8/12
 * @Time 下午7:48
 */

public class SynchronizedTest {
    private static int n = 1;
    public static void main(String[] args) {
        synchronized (SynchronizedTest.class) {
            n++;
        }
    }

    public synchronized void fun() {}
}