package com.example.visualist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckedTextView
import android.widget.ImageView

class editInformation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_information)

        //性别按钮
        val female = findViewById<CheckedTextView>(R.id.toggleButtonFemale)
        val male = findViewById<CheckedTextView>(R.id.toggleButtonMale)
        female.setOnClickListener{
            female.isChecked = true
            male.isChecked  = false
        }
        male.setOnClickListener{
            female.isChecked = false
            male.isChecked  = true
        }

        //返回按钮
        val back = findViewById<ImageView>(R.id.imageView12)
        back.setOnClickListener{
            finish()
        }
    }
}