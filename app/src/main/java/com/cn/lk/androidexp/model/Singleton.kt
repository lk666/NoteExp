package com.cn.lk.androidexp.model

import android.os.Parcel
import android.os.Parcelable
import java.io.ObjectStreamException
import java.io.Serializable

/**
 * 单例模式
 * Created by lk on 2018/9/12.
 */
open class Singleton : Serializable, Cloneable, Parcelable {
    var data: String? = null

    //--------------------------------
    // 防反射
    //--------------------------------
    @Throws(RuntimeException::class)
    private constructor () {
        if (flag) {
            flag = false
        } else {
            throw RuntimeException("已存在单例")
        }
        // 其他初始化工作
    }

    private constructor(parcel: Parcel) : this(parcel.readString())

    private constructor(data: String) : this() {
        this.data = data
    }

    //--------------------------------
    // 防反射
    //--------------------------------
    @Throws(CloneNotSupportedException::class)
    override fun clone(): Any {
        throw CloneNotSupportedException()
    }

    //--------------------------------
    // 防Serializable反序列化
    //--------------------------------
    @Throws(ObjectStreamException::class)
    fun readResolve(): Any {
        if (INSTANCE == null) {
            synchronized(SingleTonJava::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Singleton()
                }
            }
        }
        return INSTANCE
    }

    //--------------------------------
    // 防Parcelable反序列化
    //--------------------------------
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(data)
    }

    override fun describeContents() = 0

    companion object {
        /**
         * 在JVM平台，使用 @JvmStatic注解，可以将伴生对象的成员生成为真正的静态方法和字段
         */
        @JvmField
        val CREATOR: Parcelable.Creator<Singleton> = object : Parcelable.Creator<Singleton> {
            override fun createFromParcel(parcel: Parcel): Singleton {
                if (INSTANCE == null) {
                    synchronized(Singleton::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = Singleton(parcel)
                        }
                    }
                }
                return INSTANCE
            }

            override fun newArray(size: Int): Array<Singleton?> {
                throw Error("不能创建数组")
            }
        }

        @Volatile
        var flag = true

//        //------------------------------
//        // 饿汉
//        //------------------------------
////        object Singleton
//        private val INSTANCE = Singleton()
//
//        fun getInstance() = INSTANCE

        //------------------------------
        // 懒汉DCL
        //------------------------------
        private lateinit var INSTANCE: Singleton

        fun getInstance(data: String): Singleton {
            if (INSTANCE == null) {
                synchronized(SingleTonJava::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Singleton(data)
                    }
                }
            }

            return INSTANCE
        }

//        //------------------------------
//        // 懒汉2
//        //------------------------------
//        val INSTANCE: Singleton by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
//            Singleton()
//        }
//
//        //------------------------------
//        // 内部静态类
//        //------------------------------
//        fun getInstance() = Holder.INSTANCE
//
//        //------------------------------
//        // 枚举
//        //------------------------------
//        enum class Singleton {
//            INSTANCE;
//            fun fun0() = 1
//        }
    }

    private object Holder {
        val INSTANCE = Singleton()
    }
}