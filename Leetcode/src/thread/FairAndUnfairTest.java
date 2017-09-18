/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
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
        //testLock(fairLock);

        //非公平锁
        testLock(unfairLock);
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