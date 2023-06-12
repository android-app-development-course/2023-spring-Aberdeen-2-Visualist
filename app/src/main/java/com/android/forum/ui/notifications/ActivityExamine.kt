package com.android.forum.ui.notifications

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.forum.data.AppDatabase
import com.android.forum.data.GlobalData
import com.android.forum.databinding.ActivityExamineBinding
import com.android.forum.databinding.ActivityMyPostBinding
import com.android.forum.ui.notifications.adapter.DownloadAdapter
import com.android.forum.ui.notifications.adapter.ExamineADapter


class ActivityExamine : AppCompatActivity(){

    private lateinit var binding: ActivityExamineBinding

    val worksDao = AppDatabase.getInstance().worksDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExamineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mRecyclerView = binding.recyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        worksDao.getAll(GlobalData.uid).observe(this){
            mRecyclerView.adapter = ExamineADapter(this,it)

        }
    }
}