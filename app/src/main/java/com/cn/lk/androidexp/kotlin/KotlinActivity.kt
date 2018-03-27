package com.cn.lk.androidexp.kotlin

import com.cn.lk.androidexp.ActivityItem
import com.cn.lk.androidexp.SelectBaseActivity


class KotlinActivity : SelectBaseActivity(listOf(
        ActivityItem("基础语法", SimpleKotlinActivity::class.java)
))