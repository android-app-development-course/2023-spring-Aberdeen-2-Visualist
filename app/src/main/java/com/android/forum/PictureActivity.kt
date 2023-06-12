package com.android.forum

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.forum.databinding.ActivityPictuerBinding
import com.android.forum.global.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView


class PictureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPictuerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPictuerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "照片详细"

        val navView: ImageView = binding.ivPicture
        navView.setImageBitmap(Constants.mPicture)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}