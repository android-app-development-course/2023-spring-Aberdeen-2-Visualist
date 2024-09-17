package com.android.forum.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import com.android.forum.store.DataStoreUtils

/**
 * @Description:
 * @Author: JIULANG
 * @Data: 2023/4/20 20:48
 */
class BaseApplication : Application(){
    companion object {
        @JvmStatic
        @SuppressLint("StaticFieldLeak")
        lateinit var CONTEXT: Context
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        DataStoreUtils.init(this)
    }

}