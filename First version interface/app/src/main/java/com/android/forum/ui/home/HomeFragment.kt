package com.android.forum.ui.home

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

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val launcher = XActivityLauncher(this)


    // 拍摄照片
    private val takePicturePreview =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { photoPreview ->
            // 该合约返回的是Bitmap,需要保存要进行额外的处理
            if (photoPreview != null) {
                Constants.mPicture = photoPreview
                toPictureActivity()
            }
        }

    private fun toPictureActivity() {
        val toPictureActivity = Intent(requireContext(), PictureActivity::class.java)
        startActivity(toPictureActivity)
    }

    private lateinit var photoUri: Uri


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val cardPhotograph: CardView = binding.cardPhotograph
        val cardAlbum: CardView = binding.cardAlbum
        cardPhotograph.setOnClickListener {
            ifHaveCameraPermission()
        }
        cardAlbum.setOnClickListener {
            launcher.launch("image/*") {
                it?.let {
                    val photoBmp: Bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        it
                    )

                    Constants.mPicture = photoBmp
                    toPictureActivity()

                }
            }
        }
        homeViewModel.text.observe(viewLifecycleOwner) {

        }
        return root
    }

    // 判断是否有相机权限
    private fun ifHaveCameraPermission() {
        /**
         * AndPermission.hasPermissions：判断是否有相对应的权限
         * Permission.Group.CAMERA：摄像权限
         */
        if (!AndPermission.hasPermissions(this, *Permission.Group.CAMERA)) {
            /**
             * AndPermission：引用权限相关库
             * onGranted：允许权限
             * onDenied：拒绝权限
             */
            // 动态申请权限
            AndPermission.with(this).runtime().permission(*Permission.Group.CAMERA)
                .onGranted { openCamera() }
                .onDenied { denieds: List<String?>? ->
                    if (denieds != null && denieds.isNotEmpty()) {
                        for (i in denieds.indices) {
                            if (!shouldShowRequestPermissionRationale(denieds[i]!!)) {
                                Toast.makeText(requireContext(), "没有拍摄和录制权限", Toast.LENGTH_SHORT)
                                    .show()
                                break
                            }
                        }
                    }
                }.start()
        } else {
            // 有权限 打开相机
            openCamera()
        }
    }


    // 打开相机
    private fun openCamera() {

        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date(System.currentTimeMillis())
        val photoFileName = "photo_" + format.format(date)


        val path: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        val outputImage = File(path, "$photoFileName.jpg")

        if (outputImage.exists()) {
            outputImage.delete()
        }

        try {
            outputImage.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        photoUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(requireContext(), "your_provider", outputImage)
        } else {
            Uri.fromFile(outputImage)
        }

        try {
            // 相机拍摄
            takePicturePreview.launch(null)
//            resultLauncher.launch(photoUri) {
//                it?.let {
//                    Toast.makeText(requireContext(), "启动系统的照相成功", Toast.LENGTH_SHORT).show()
//                }
//            }
        } catch (e: SecurityException) {
            Toast.makeText(requireContext(), "启动系统的照相失败", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}