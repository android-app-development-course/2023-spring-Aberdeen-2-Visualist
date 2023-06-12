package com.android.forum.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.forum.MainActivity
import com.android.forum.data.AppDatabase
import com.android.forum.data.GlobalData
import com.android.forum.data.User
import com.android.forum.databinding.FragmentLoginBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.random.Random



class LoginFragment : Fragment() {

    fun newInstance(): LoginFragment {
        return LoginFragment()
    }

    private var _binding: FragmentLoginBinding? = null

    private val mBinding get() = _binding!!
    private val verificationCode = "6357"

    val appDatabase = AppDatabase.getInstance().userDao()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAnimation()

        mBinding.etPhone.setText("1")
        mBinding.etCode.setText(verificationCode)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initView() {
        mBinding.etPhone.setBackgroundResource(android.R.color.transparent)
        mBinding.etCode.setBackgroundResource(android.R.color.transparent)
        val button = mBinding.btnGetCode
        button.cornerRadius = 66
        button.setBackgroundColor(Color.BLACK)
        val btnLogin = mBinding.btnLogin
        btnLogin.cornerRadius = 100
        btnLogin.setBackgroundColor(Color.BLACK)
        /**
         * 点击事件
         */
        mBinding.ivBack.setOnClickListener {
            requireActivity().finish()
        }

        mBinding.btnGetCode.setOnClickListener {
            Toast.makeText(requireContext(), "验证码 :$verificationCode", Toast.LENGTH_LONG).show()
        }
        mBinding.btnLogin.setOnClickListener {
            val phone = mBinding.etPhone.text.toString()
            val code = mBinding.etCode.text.toString()

            if (code != verificationCode) {
                Toast.makeText(requireContext(), "验证码有误", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            var isRegister = false

            GlobalScope.launch(Dispatchers.IO) {
                val users = appDatabase.getAll()
                users.forEach {
                    if (it.phone == phone) {
                        //已有账号
                        GlobalData.uid = it.uid
                        isRegister = true
                    }
                }
                if (isRegister) {
                    withContext(Dispatchers.Main) {
                        toActivity(MainActivity::class.java)
                    }
                } else {
                    val uid = Random.nextLong()
                    val user = User(uid, phone, "", phone,"")
                    appDatabase.insert(user)
                    GlobalData.uid = uid
                    withContext(Dispatchers.Main) {
                        toActivity(MainActivity::class.java)
                    }
                }
            }
        }
    }

    private fun initAnimation() {
        val translateAnimation = TranslateAnimation(0f, 0f, 70f, 0f)
        translateAnimation.duration = 1000;  // 时长1s
        translateAnimation.fillAfter = true; // 保持动画结束状态
        mBinding.tvTitle.startAnimation(translateAnimation);
        val edAnimation = TranslateAnimation(0f, 0f, 100f, 0f)
        edAnimation.duration = 1000;  // 时长1s
        edAnimation.fillAfter = true; // 保持动画结束状态
        mBinding.llCode.startAnimation(edAnimation)
        mBinding.llPhone.startAnimation(edAnimation)
        val btnAnimation = TranslateAnimation(0f, 0f, 130f, 0f)
        btnAnimation.duration = 1000;  // 时长1s
        btnAnimation.fillAfter = true; // 保持动画结束状态
        mBinding.btnLogin.startAnimation(btnAnimation)
    }

    private fun toActivity(cls: Class<*>?) {
        val intent = Intent(requireContext(), cls)
        startActivity(intent)
    }
}