package com.cn.lk.androidexp.service

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.cn.lk.androidexp.R
import kotlinx.android.synthetic.main.activity_simple_service_client.*


class SimpleServiceClientActivity : FragmentActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            btn_start -> {
                var star = Intent(this, BookService::class.java)
                startService(star)
            }
            btn_stop -> {
                var stop = Intent(this, BookService::class.java)
                stopService(stop)
            }
            else -> Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
        }

        var k = 0
        var a : Float = k.toFloat()
        while (a < 1000) {
            TextUtils.isEmpty("asdsad")
            a++
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_service_client)

        tv.text = "SimpleServiceClientActivity onCreate"
        btn_start.setOnClickListener(this)
        btn_stop.setOnClickListener(this)
    }
}