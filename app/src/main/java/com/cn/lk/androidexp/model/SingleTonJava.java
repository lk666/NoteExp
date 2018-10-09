package com.cn.lk.androidexp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * java单例模式，只考虑线程安全的
 * Created by lk on 2018/9/26.
 */

public class SingleTonJava implements Parcelable, Serializable, Cloneable {
    private String data;

    private SingleTonJava(String data) {
        this.data = data;
    }

    //    //------------------------------
    //    // 饿汉，类创建的同时就实例化了静态对象，适用于无参构造，如果一个类初始化需要耗费很多时间，
    //    // 或应用程序总是会使用到该单例，那建议使用饿汉模式，
    //    // 比如各种补丁加载、缓存信息、系统配置信息等
    //    //------------------------------
    //    private static SingleTonJava INSTANCE = new SingleTonJava();
    //
    //    public static SingleTonJava getInstance() {
    //        return INSTANCE;
    //    }

    //------------------------------
    // 懒汉双重校验锁（synchronized效率差，不考虑），延迟初始化，节省资源。对于不需一直使用、不一定会用到、
    // 或资源敏感的单例，如相机管理、音频管理等类，可以使用此种方式。
    // 此种方式可以支持参数化初始化，但只有第一个参数起效...
    //------------------------------
    private volatile static SingleTonJava INSTANCE;

    public static SingleTonJava getInstance(String data) {
        if (INSTANCE == null) {
            synchronized (SingleTonJava.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SingleTonJava(data);
                }
            }
        }
        return INSTANCE;
    }

    //
    //    //------------------------------
    //    // 内部静态类，也是无法使用参数化构造初始化。
    //    // classloader会在实际使用到SingleTonJavaHolder类时才去加载SingleTonJavaHolder
    //    //------------------------------
    //    private static class SingleTonJavaHolder {
    //        private static SingleTonJava INSTANCE = new SingleTonJava();
    //    }
    //
    //    public static SingleTonJava getInstance(String data) {
    //        return SingleTonJavaHolder.INSTANCE;
    //    }


    //------------------------------
    // 枚举单例，最简单，线程安全+防反射+防序列化+防克隆+懒加载，同样不能参数化构造
    //------------------------------

    /**
     * @see java.lang.Enum
     */
    enum SingleTonJav8a {
        INSTANCE;

        public void fun() {
        }
    }

    //    //------------------------------
    //    // 容器单例，将多种单例模式注入到一个统一的管理类中，在使用时根据key获取对应类型的对象。
    //    // Android系统的各种Service就是这模式
    //    //------------------------------
    //    private static Map<String, Object> map = new ConcurrentHashMap<>();
    //
    //    public static void registerInstance(String key, Object instance) {
    //        map.put(key, instance);
    //    }
    //
    //    public static Object getInstance(String key) {
    //        return map.get(key);
    //    }


    //------------------------------
    // 克隆处理
    //------------------------------

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    //------------------------------
    // Parcelable反序列化处理
    //------------------------------
    private SingleTonJava(Parcel in) {
        data = in.readString();
    }

    public static final Creator<SingleTonJava> CREATOR = new Creator<SingleTonJava>() {
        @Override
        public SingleTonJava createFromParcel(Parcel in) {
            if (INSTANCE == null) {
                synchronized (SingleTonJava.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new SingleTonJava(in);
                    }
                }
            }
            return INSTANCE;
        }

        @Override
        public SingleTonJava[] newArray(int size) {
            throw new Error("不能创建数组");
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(data);
    }

    //------------------------------
    // Serializable反序列化处理
    //------------------------------
    public Object readResolve() throws ObjectStreamException {
        return INSTANCE;
    }

    //------------------------------
    // 反射处理
    //------------------------------
    private static volatile boolean flag = true;
    private SingleTonJava() {
        if (INSTANCE != null) {
            flag = false;
        } else {
            throw new RuntimeException("已存在单例");
        }
        // 其他初始化工作
    }
}
