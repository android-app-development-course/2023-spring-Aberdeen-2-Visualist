package com.example.visualist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class details_of_work : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_of_work)

        //返回按钮
        val back = findViewById<ImageView>(R.id.imageView19)
        back.setOnClickListener{
            finish()
        }
    }
}