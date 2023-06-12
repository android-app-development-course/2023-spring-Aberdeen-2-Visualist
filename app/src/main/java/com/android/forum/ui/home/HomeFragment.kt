package com.android.forum.ui.home

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.forum.PictureActivity
import com.android.forum.databinding.FragmentHomeBinding
import com.android.forum.file.XActivityLauncher
import com.android.forum.file.XActivityTakePicture
import com.android.forum.global.Constants
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAnimation()
        binding.imageView.setOnClickListener {
            toActivity(CameraUIActivity::class.java)
        }

        // 判断是否有存储权限
        if (!AndPermission.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // 请求存储权限
            AndPermission.with(this)
                .runtime().permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .onGranted { /* 存储权限成功 */ }
                .onDenied { /* 存储权限失败 */ }
                .start()
        }
    }

    private fun initAnimation(){
        val translateAnimation = TranslateAnimation(0f, 0f, 150f, 0f)
        translateAnimation.duration = 1000;  // 时长1s
        translateAnimation.fillAfter = true; // 保持动画结束状态
        binding.cardView.startAnimation(translateAnimation);
        val edAnimation = TranslateAnimation(0f, 0f, 200f, 0f)
        edAnimation.duration = 1000;  // 时长1s
        edAnimation.fillAfter = true; // 保持动画结束状态
        binding.textView.startAnimation(edAnimation)
        binding.textView2.startAnimation(edAnimation)
        val btnAnimation = TranslateAnimation(0f, 0f, 230f, 0f)
        btnAnimation.duration = 1000;  // 时长1s
        btnAnimation.fillAfter = true; // 保持动画结束状态
        binding.textView3.startAnimation(btnAnimation)
    }
    private fun toActivity(cls: Class<*>?) {
        val intent = Intent(requireContext(), cls)
        startActivity(intent)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}