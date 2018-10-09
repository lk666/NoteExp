package com.cn.lk.androidexp.model

import com.cn.lk.androidexp.util.ViewUtil.toast

object Util {
    /**
     * 弹出弹窗
     */
    fun popDialog(msg:String){
        popDialog1(msg)
    }

    /**
     * 弹出弹窗1
     */
    fun popDialog1(msg:String){
        toast(msg)
    }

    /**
     * 弹出弹窗2
     */
    fun popDialog2(msg:String){
//        toast2(msg)
    }

}