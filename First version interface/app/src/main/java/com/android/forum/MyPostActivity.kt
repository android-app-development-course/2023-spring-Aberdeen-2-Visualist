package com.android.forum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.forum.databinding.ActivityMyPostBinding
import com.android.forum.databinding.ActivitySetBinding

/**
 * @Description:
 * @Author: JIULANG
 * @Data: 2023/4/20 18:32
 */
class MyPostActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMyPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "我的帖子"

    }
}