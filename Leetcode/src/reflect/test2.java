/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @author ethan
 * @version $Id: test2.java, v 0.1 2017年07月12日 上午10:33 ethan Exp $
 */

class Son2{
    public double i;
    public int num;
    String str;
    private Map<Integer, String> map;

    public Son2() {}
    public Son2(double i) {this.i = i;}

    void test() {
        i++;
    }

    public void setter(double i) {this.i = i;}
    public double getter() {return i;}
}


public class test2 {
    public static void main(String[] args) throws Exception {
        //获得Son2类的Class对象。
        Class<?> cc = Son2.class;

        cc.getName();


        Field[] field2 = cc.getDeclaredFields();
        for (Field tmp : field2) {
            System.out.println(tmp);
        }

        Method[] method2 = cc.getDeclaredMethods();
        for (Method tmp : method2) {
            System.out.println(tmp);
        }

        Field f = cc.getDeclaredField("str");
        System.out.println(f);

        Constructor[] constructors = cc.getConstructors();
        for (Constructor tmp : constructors) {
            System.out.println(tmp);
        }

        Constructor constructor9 = cc.getConstructor();
        System.out.println(constructor9);
        Constructor constructor = cc.getConstructor(double.class);
        Son2 son = (Son2)constructor.newInstance(123);
        System.out.println(son.i);


        Constructor<?> constructor1 = cc.getConstructor();
        Son2 son2 = (Son2)constructor1.newInstance();
        System.out.println(son2.getter());


        Class<?> cc2 = Son2.class;
        Son2 son22 = (Son2)cc2.newInstance();
        System.out.println(son22);

        System.out.println(1);
        System.out.println(cc);
        System.out.println(2);
        System.out.println(cc.newInstance());


        Constructor<?> constructor2 = cc.getConstructor(double.class);
        Son2 son23 = (Son2)constructor2.newInstance(321);
        Field field = cc.getDeclaredField("i");
        field.setAccessible(true);
        field.set(son23, 213);
        System.out.println(field.get(son23));


        Constructor<?> constructor3 = cc.getConstructor(double.class);
        Son2 son24 = (Son2)constructor3.newInstance(231);
        System.out.println(cc.getMethod("getter").invoke(son24));


        Son2 son25 = (Son2)cc.newInstance();
        Method method = cc.getMethod("setter", double.class);
        method.invoke(son25, 123);
        System.out.println(son25.getter());


        System.out.println(Modifier.toString(cc.getModifiers()));
        System.out.println(Modifier.toString(constructor.getModifiers()));
        System.out.println(Modifier.toString(field.getModifiers()));
        System.out.println(Modifier.toString(method.getModifiers()));
        System.out.println(Modifier.isPublic(cc.getModifiers()));
        System.out.println(Modifier.isPublic(constructor.getModifiers()));
    }
}