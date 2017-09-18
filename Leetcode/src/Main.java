/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * @Author Created by Xiaoqi.zcl
 * @Date 2017/8/20
 * @Time 上午10:50
 */

public class Main {
    static double test1(int n, List<String> error) {
        try {
            return 10/n;
        } catch (Exception e) {
            String err = new String("test1");
            error.add(err);
            System.out.println("exception in test");
            e.printStackTrace();
            return 0.1f;

            StringBuilder sb = new StringBuilder();

        }
    }

    public static void main(String[] args) {

        double ans = 0;
        List<String> error = new ArrayList<>();
        try {
            ans = test1(0, error);

            System.out.println("test finish");
        } catch (Exception e) {
            System.out.println("exception in main");
            e.printStackTrace();
        }

        System.out.println(ans);
        System.out.println(error.get(0));
    }
}