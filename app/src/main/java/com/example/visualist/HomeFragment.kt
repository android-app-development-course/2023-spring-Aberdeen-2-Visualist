package com.example.visualist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 点击头像后跳转编辑资料的页面
        val avatar = view.findViewById<ImageView>(R.id.imageView2)
        avatar.setOnClickListener{
            val intent = Intent(getActivity(), editInformation::class.java)
            startActivity(intent)
        }

        //点击历史下载
        val history = view.findViewById<ImageView>(R.id.imageView4)
        history.setOnClickListener{
            val intent = Intent(getActivity(), historyDownload::class.java)
            startActivity(intent)
        }

        //点击审核进度
        val review = view.findViewById<ImageView>(R.id.imageView5)
        review.setOnClickListener{
            val intent = Intent(getActivity(), reviewProcess::class.java)
            startActivity(intent)
        }
    }
}
