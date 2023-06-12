package com.android.forum.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.baidu.aip.imageprocess.AipImageProcess

import com.baidu.aip.imagesearch.AipImageSearch

/**
 * @Description:
 * @Author: JIULANG
 * @Data: 2023/4/20 20:48
 */
class BaseApplication : Application() {
    companion object {
        @JvmStatic
        @SuppressLint("StaticFieldLeak")
        lateinit var CONTEXT: Context
        lateinit var client: AipImageSearch
    }


    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext


        val APP_ID = "34477461";
        val API_KEY = "2Fuj46MGjUhbjIBBGgAU5bm3";
        val SECRET_KEY = "XKiGWAekqFmXyWKjRaHfTU8riOrXBSSG";

        // 初始化一个AipImageSearch
        client = AipImageSearch(APP_ID, API_KEY, SECRET_KEY)
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

    }

}