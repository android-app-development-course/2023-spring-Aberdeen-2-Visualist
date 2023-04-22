package com.android.forum

import android.app.ActivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.forum.databinding.ActivitySetBinding


/**
 * @Description:
 * @Author: JIULANG
 * @Data: 2023/4/20 18:21
 */
class SetActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "设置"

//        binding.btnBack.setOnClickListener {
//
//        }
    }
}