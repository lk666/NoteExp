package com.cn.lk.androidexp.coroutines

class Test{
    fun <T> test0(b:suspend ()->T){

    }

    suspend fun suspendFun0():String {
        return "asdasdas"
    }

    fun test1(){
        val str = test0 { val s = suspendFun0()
        }
    }
}