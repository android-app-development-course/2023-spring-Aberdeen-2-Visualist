package com.android.forum.ui.dashboard

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.android.forum.data.AppDatabase
import com.android.forum.data.DownloadEntity
import com.android.forum.data.GlobalData
import com.android.forum.databinding.ActivityForumDetailBinding

import com.android.forum.global.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ForumDetailActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityForumDetailBinding
    val downloadDao = AppDatabase.getInstance().downloadDao()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityForumDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initViewAnimator()
        initData()
    }

    private fun initData() {
        val userDetail = Constants.userDetail
        val post = Constants.postDetail
        if (post.tag) {
            val bieBitmap = Uri.parse(post.path)
            mBinding.ivBg.setImageURI(bieBitmap)
        } else {
            val bieBitmap = convertImageToBitmap(post.path)
            mBinding.ivBg.setImageBitmap(bieBitmap)
        }
        mBinding.tvContent.text = post.content
        userDetail.avatar.let {
            if (!TextUtils.isEmpty(it)) {
                val photoBmp: Bitmap = BitmapFactory.decodeFile(it);
                mBinding.ivAvatar.setImageBitmap(photoBmp)
            }
        }
        mBinding.tvName.text = userDetail.username
        mBinding.btnDowloda.setOnClickListener {
            lifecycleScope.launch {

                withContext(Dispatchers.IO){
                   val  downloadEntity = DownloadEntity(post.path, GlobalData.uid,post.tag,post.content,System.currentTimeMillis())
                    downloadDao.insert(downloadEntity)
                }
                mBinding.successDowload.visibility = View.VISIBLE
                delay(1000)
                mBinding.successDowload.visibility = View.INVISIBLE
            }
        }

    }

    private fun initViewAnimator() {
        val translateAnimation = TranslateAnimation(0f, 0f, 150f, 0f)
        translateAnimation.duration = 1000;  // 时长1s
        translateAnimation.fillAfter = true; // 保持动画结束状态
        mBinding.tvName.startAnimation(translateAnimation);
        val edAnimation = TranslateAnimation(0f, 0f, 200f, 0f)
        edAnimation.duration = 1000;  // 时长1s
        edAnimation.fillAfter = true; // 保持动画结束状态
        mBinding.textWorks.startAnimation(edAnimation)
        mBinding.tvContent.startAnimation(edAnimation)
        val btnAnimation = TranslateAnimation(0f, 0f, 230f, 0f)
        btnAnimation.duration = 1000;  // 时长1s
        btnAnimation.fillAfter = true; // 保持动画结束状态
        mBinding.btnDowloda.startAnimation(btnAnimation)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}

private fun convertImageToBitmap(imagePath: String?): Bitmap? {
    var bitmap: Bitmap? = null
    val options = BitmapFactory.Options()
    options.inPreferredConfig = Bitmap.Config.ARGB_8888
    try {
        bitmap = BitmapFactory.decodeFile(imagePath, options)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return bitmap
}
