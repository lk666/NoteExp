package com.cn.lk.androidexp.kotlin.weather

import com.google.gson.annotations.Expose

/**
 * Model
 * Created by lk on 2018/3/26.
 */
data class ModelWeatherInfo(val weatherinfo: WeatherinfoBean) {
    data class WeatherinfoBean(
            val city: String,
            val cityid: String,
            val temp: String,
            val WD: String,
            val WS: String,
            val SD: String,
            val WSE: String,
            val time: String,
            val isRadar: String,
            val Radar: String,
            val njd: String,
            val qy: String,

            @Expose(serialize = false)
            var flag:Boolean = false)
}