package com.android.forum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.forum.databinding.ActivityEditInfoBinding
import com.android.forum.databinding.ActivitySetBinding
import com.android.forum.global.*
import com.android.forum.store.AppInfoManage

/**
 * @Description:
 * @Author: JIULANG
 * @Data: 2023/4/20 18:24
 */
class EditInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edUsername.setText(AppInfoManage.username)
        binding.edPersonalSignature.setText(AppInfoManage.personal_signature)
        binding.edCity.setText(AppInfoManage.city)
        binding.edPhoneNumber.setText(AppInfoManage.phoneNumber)
        binding.edMailbox.setText(AppInfoManage.mailbox)

        binding.btnSave.setOnClickListener {

            AppInfoManage.username =binding.edUsername.text.toString()
            AppInfoManage.personal_signature =binding.edPersonalSignature.text.toString()
            AppInfoManage.city =binding.edCity.text.toString()
            AppInfoManage.phoneNumber =binding.edPersonalSignature.text.toString()
            AppInfoManage.mailbox =binding.edMailbox.text.toString()

            UsernameData.value = binding.edUsername.text.toString()
            PersonalSignatureData.value = binding.edPersonalSignature.text.toString()
            CityData.value = binding.edCity.text.toString()
            PhoneNumberData.value = binding.edPhoneNumber.text.toString()
            MailboxData.value = binding.edMailbox.text.toString()

       finish()
        }
    }
}