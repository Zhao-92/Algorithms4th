/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package thread;

/**
 * @Author Created by Xiaoqi.zcl
 * @Date 2017/8/9
 * @Time 下午9:06
 */

class Thread2 implements Runnable{
    private String name;

    public Thread2(String name) {
        this.name=name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "运行  :  " + i);
            try {
                Thread.sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
public class Main {

    public static void main(String[] args) {
        Runnable r = new Thread2("C");
        new Thread(r).start();
        new Thread(r).start();
    }

}