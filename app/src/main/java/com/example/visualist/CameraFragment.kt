package com.example.visualist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment

class CameraFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 在这里可以对布局中的控件进行操作和设置事件监听器
        val button = view.findViewById<ImageView>(R.id.imageView)
        button.setOnClickListener {
            //要在 Fragment 中显示 Toast，你可以使用 requireContext() 方法获取上下文对象，并将其作为第一个参数传递给 makeText() 方法。
            Toast.makeText(requireContext(), "someone clicked", Toast.LENGTH_SHORT).show()
        }


    }
}
