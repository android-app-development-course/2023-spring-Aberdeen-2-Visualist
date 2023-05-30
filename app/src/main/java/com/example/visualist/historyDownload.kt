package com.example.visualist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class historyDownload : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_download)

        //返回按钮
        val back = findViewById<ImageView>(R.id.imageView12)
        back.setOnClickListener{
            finish()
        }
    }
}