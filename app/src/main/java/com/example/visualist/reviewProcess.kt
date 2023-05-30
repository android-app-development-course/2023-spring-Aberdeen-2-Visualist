package com.example.visualist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class reviewProcess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_process)

        //返回按钮
        val back = findViewById<ImageView>(R.id.imageView12)
        back.setOnClickListener{
            finish()
        }
    }
}