package com.android.forum.ui.dashboard

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.forum.data.GlobalData
import com.android.forum.databinding.FragmentPictureBinding


class PictureFragment  : Fragment() {


    fun newInstance(): PictureFragment {
        return PictureFragment()
    }

    private var _binding: FragmentPictureBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPictureBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setImageBitmap(convertImageToBitmap(GlobalData.picture))
    }

    private fun convertImageToBitmap(imagePath: String?): Bitmap? {
        val options = BitmapFactory.Options()

        val originBitmap = BitmapFactory.decodeFile(imagePath, options)
        val bitmap = Bitmap.createScaledBitmap(originBitmap, 1080, 1080, true)
        originBitmap.recycle()

        return bitmap
    }

}