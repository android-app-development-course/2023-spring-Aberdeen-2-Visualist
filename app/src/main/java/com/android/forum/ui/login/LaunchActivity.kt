package com.android.forum.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.android.forum.R
import com.android.forum.databinding.ActivityLaunchBinding



class LaunchActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initViewAnimator()
    }

    private fun initViewAnimator(){
        val imageView = mBinding.iv

        val animatorSet = AnimatorSet()
        val rotationAnimator = ObjectAnimator.ofFloat(imageView, View.ROTATION, -90f, 0f)

        rotationAnimator.duration = 1000
        rotationAnimator.interpolator = AccelerateDecelerateInterpolator()
        imageView.pivotX = imageView.width.toFloat()
        imageView.pivotY = 0f
        animatorSet.play(rotationAnimator)

        // 创建渐显动画
        val alphaAnimator = ObjectAnimator.ofFloat(imageView, View.ALPHA, 0f, 1f)
        alphaAnimator.duration = 1000
        alphaAnimator.interpolator = AccelerateInterpolator()
        animatorSet.play(alphaAnimator)
        animatorSet.start()

        imageView.setOnClickListener {
            val loginFragment: LoginFragment = LoginFragment()
            val fm = supportFragmentManager
            fm.beginTransaction()
                .add(R.id.fragment_content, loginFragment)
                .commit()
        }
    }

}