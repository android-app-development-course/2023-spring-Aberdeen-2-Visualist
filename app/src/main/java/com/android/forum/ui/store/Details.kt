package com.android.forum.ui.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.android.forum.R

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details1)

        //toolbar返回键设置
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //按钮被点击后返回标识
        val addButton = findViewById<Button>(R.id.addCart)
        val buyButton = findViewById<Button>(R.id.buyDirectly)
        val successAddImageView = findViewById<ImageView>(R.id.successAddImageView)
        val successBuyImageView = findViewById<ImageView>(R.id.successBuyImageView)
        //添加成功
        addButton.setOnClickListener{
            //展示
            successAddImageView.visibility = View.VISIBLE
            // 延迟一定时间后隐藏标识
            val handler = Handler()
            val delayMillis: Long = 1500 // 设置延迟时间，单位为毫秒
            handler.postDelayed({
                successAddImageView.visibility = View.INVISIBLE
            }, delayMillis)
        }
        //购买成功
        buyButton.setOnClickListener{
            //展示
            successBuyImageView.visibility = View.VISIBLE
            // 延迟一定时间后隐藏标识
            val handler = Handler()
            val delayMillis: Long = 1500 // 设置延迟时间，单位为毫秒
            handler.postDelayed({
                successBuyImageView.visibility = View.INVISIBLE
            }, delayMillis)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        toolbar返回键逻辑实现
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}