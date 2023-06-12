package com.android.forum.ui.store

import com.android.forum.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment


class StoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 跳转到相机详情页
        val pic1 = view.findViewById<ImageView>(R.id.camera2)
        pic1.setOnClickListener {
            val intent = Intent(activity, Details::class.java)
            startActivity(intent)
        }


    }
}
