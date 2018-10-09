package com.cn.lk.androidexp.kotlin

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View
import android.widget.TextView
import com.cn.lk.androidexp.R
import kotlinx.android.synthetic.main.activity_bind_service_client.*


class SimpleKotlinActivity : FragmentActivity() {

    companion object {
        var TAG = "BinderCountService-Client"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_service_client)

        btn_start.visibility = View.GONE
        btn_stop.visibility = View.GONE
        btn_bind.visibility = View.GONE
        btn_unbind.visibility = View.GONE
        btn_get_count.visibility = View.GONE

        A(btn_bind).f()

        // copy
        val ori = Data(Data.DataBean("asdasd", "687654"))
        val copy = ori.data.copy(name = "8756456465465", id = "opiuoopi")
        tv.text = "${tv.text}\n------$ori----------$copy"
        ori.data.name = "1"
        tv.text = "${tv.text}\n------$ori"
    }

    inner class A<T : TextView>(var a: T) {

        // 内部类没有companion object
//        companion object {
//            val CODE = 1
//        }

        lateinit var b: T

        constructor(a: T, b: T) : this(a) {
            this.b = b
        }

        init {
            tv.text = "${tv.text}\ninit"
        }

        fun f() {
            tv.text = "${tv.text}\nfun------${a.text}------${SINGLE.getSome()}"
        }
    }

    object SINGLE {
        fun getSome() = "some"
    }

    data class Data(val data: DataBean) {
        data class DataBean(
                var name: String,
                val id: String
        )
    }
}