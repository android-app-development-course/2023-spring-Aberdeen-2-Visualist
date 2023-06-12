package com.android.forum.ui.home

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.android.forum.R
import com.android.forum.databinding.ActivityCameraBinding
import com.android.forum.file.XActivityLauncher
import com.android.forum.file.uriToFile
import com.android.forum.global.Constants
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class CameraUIActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCameraBinding

    private val launcher = XActivityLauncher(this)

    private lateinit var photoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initView()
        initViewAnimator()
    }

    private fun initView() {
        mBinding.ivCamera.setOnClickListener {
            ifHaveCameraPermission()
        }
        mBinding.ivPicture.setOnClickListener { view ->
            launcher.launch("image/*") {
                it?.let {
                    Constants.mPictureUrl = it
                    val photoBmp: Bitmap = MediaStore.Images.Media.getBitmap(
                        view.context.contentResolver,
                        it
                    )
                    Constants.mPicture = photoBmp

                    uriToFile(it, view.context).let { file ->
                        if (file != null) {
                            Constants.worksPicturePath = file.path
                        }
                    }
                    toCameraResultFragment()
                }
            }

        }
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
                                Toast.makeText(this, "没有拍摄权限", Toast.LENGTH_SHORT)
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

        val values = ContentValues()


        if (Build.VERSION.SDK_INT >= 29) {
            val contentResolver: ContentResolver = this.contentResolver
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, "$photoFileName.jpg")
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            photoUri =contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)!!

        } else {
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)

              photoUri= FileProvider.getUriForFile(
                this, "com.android.forum.fileprovider", File(
                    path.absolutePath,
                    "$photoFileName.jpg"
                )
            )
        }




        try {
            // 相机拍摄
//            takePicturePreview.launch(null)
            launcherTakePicture.launch(photoUri)

        } catch (e: SecurityException) {
            Toast.makeText(this, "启动系统的照相失败", Toast.LENGTH_SHORT).show()
        }
    }


    private val launcherTakePicture: ActivityResultLauncher<Uri> =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                Constants.mPictureUrl = photoUri
                uriToFile(photoUri, this).let { file ->
                    if (file != null) {
                        Constants.worksPicturePath = file.path
                    }
                }
                val photoBmp: Bitmap = MediaStore.Images.Media.getBitmap(
                    this.contentResolver,
                    photoUri
                )
                Constants.mPicture = photoBmp
                toCameraResultFragment()
            }

        }

    private fun toCameraResultFragment() {
        val fragment = CameraResultFragment()
        val fm = supportFragmentManager
        fm.beginTransaction()
            .add(R.id.fragment_content, fragment)
            .commit()
    }

    private fun initViewAnimator() {

    }
}