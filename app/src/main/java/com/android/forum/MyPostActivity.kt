package com.android.forum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.forum.data.AppDatabase
import com.android.forum.data.GlobalData
import com.android.forum.databinding.ActivityMyPostBinding
import com.android.forum.databinding.ActivitySetBinding
import com.android.forum.ui.notifications.adapter.DownloadAdapter
import com.android.forum.ui.notifications.adapter.GridAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyPostActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMyPostBinding

    val downloadDao = AppDatabase.getInstance().downloadDao()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mRecyclerView = binding.recyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        downloadDao.getAll(GlobalData.uid).observe(this){
            mRecyclerView.adapter = DownloadAdapter(this,it)
        }

    }
}