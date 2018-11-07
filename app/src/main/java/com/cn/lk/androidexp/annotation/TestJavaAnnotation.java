package com.cn.lk.androidexp.annotation;

import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;

import java.lang.reflect.Field;

/**
 * 测试java自带的注解，并加上一些注解的用法
 */
public class TestJavaAnnotation {
    /**
     * java本身的一些注解
     */
    @Deprecated
    //    @Override
    public void testInner() {

    }

    // 原本使用testInner会警告，加上次此句会去除警告
    @SuppressWarnings("deprecation")
    public void testInner2(@IdRes int id) {
        testInner();
    }

    // java8新增FunctionalInterface，函数式接口

    /**
     * @see Runnable
     */
    public void testInner3() {
        new Thread(() -> {
            // 可以直接这样写线程
        }).start();
    }


    /**
     * 获取自定义的注解
     */
    @MyJavaAnnotation(role = "呵呵")
    @MyJavaAnnotation(role = "哈哈")
    String tag = "asdsadsad";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String test(TestJavaAnnotation obj) throws NoSuchFieldException {
        String s = "";
        // 假设是对类的话
        //        Class c = obj.getClass();
        //        if (c.isAnnotationPresent(MyJavaAnnotation.class)) {
        //            MyJavaAnnotation ano = (MyJavaAnnotation) c.getAnnotation(MyJavaAnnotation
        // .class);
        //            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //                s += ano.role();
        //            }
        //        }


        Field f = obj.getClass().getDeclaredField("tag");
        //  Repeatable获取到的是集合
        if (f.isAnnotationPresent(MyJavaAnnotations.class)) {
            MyJavaAnnotations ano = f.getAnnotation(MyJavaAnnotations.class);
            for (MyJavaAnnotation mano : ano.value()) {
                s += mano.role();
            }
        }
        return s;
    }

}
