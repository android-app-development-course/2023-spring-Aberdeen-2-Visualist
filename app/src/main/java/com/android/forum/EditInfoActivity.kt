package com.android.forum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.forum.data.AppDatabase
import com.android.forum.data.GlobalData
import com.android.forum.data.User
import com.android.forum.databinding.ActivityEditInfoBinding
import com.android.forum.databinding.ActivitySetBinding
import com.android.forum.global.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Description:
 * @Author: JIULANG
 * @Data: 2023/4/20 18:24
 */
class EditInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditInfoBinding

    val userDao = AppDatabase.getInstance().userDao()
    lateinit var user :User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.Main) {

            user = withContext(Dispatchers.IO) {
                userDao.getUser(GlobalData.uid)
            }
            binding.edUsername.setText(user.username)
            binding.edPersonalSignature.setText(user.signature)
            binding.edPhoneNumber.setText(user.phone)

        }

        binding.btnSave.setOnClickListener {
            val username =binding.edUsername.text.toString()
            val signature =binding.edPersonalSignature.text.toString()
            val phone  =binding.edPhoneNumber.text.toString()


            lifecycleScope.launch(Dispatchers.IO) {
                user.username = username
                user.signature = signature
                user.phone = phone
                userDao.update(user)
            }


       finish()
        }
    }
}