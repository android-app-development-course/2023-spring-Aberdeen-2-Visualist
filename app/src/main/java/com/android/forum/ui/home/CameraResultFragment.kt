package com.android.forum.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.forum.base.BaseApplication.Companion.client
import com.android.forum.data.AppDatabase
import com.android.forum.data.GlobalData
import com.android.forum.data.WorksEntity
import com.android.forum.databinding.FragmentCameraResultBinding
import com.android.forum.file.downloadImage
import com.android.forum.file.shareImage
import com.android.forum.global.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File



class CameraResultFragment: Fragment() {

    private var _binding: FragmentCameraResultBinding? = null
    private val mBinding get() = _binding!!

    val worksDao = AppDatabase.getInstance().worksDao()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraResultBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bitmap = Constants.mPicture
        mBinding.ivBg.setImageBitmap(bitmap)

        mBinding.ivSearch.setOnClickListener { view->
           requireContext().shareImage(Constants.mPictureUrl)
        }



        initBaidu()


        mBinding.ivSave.setOnClickListener {

            lifecycleScope.launch(Dispatchers.IO) {

                worksDao.insert(WorksEntity(Constants.worksPicturePath,GlobalData.uid,System.currentTimeMillis()))

                withContext(Dispatchers.Main){
                    requireActivity().finish()

                }
            }
        }
    }

    private fun initBaidu() {

        mBinding.flProgress.visibility = View.VISIBLE
        println("__________________initBaidu______________________________")
        lifecycleScope.launch(Dispatchers.IO) {
            val inputStream = requireContext().contentResolver.openInputStream(Constants.mPictureUrl)!!
            val options = HashMap<String, String>()
//            options["pn"] = "1"
//            options["rn"] = "1"
            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var len = 0
            while (inputStream.read(buffer).also { len = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, len)
            }
            val file = byteArrayOutputStream.toByteArray()
            inputStream.close()

            val res = client.similarSearch(file, options)
            println(res.toString(2))
            if (res.has("result")) {
                val result = res.getJSONArray("result")
                val imageUrl = result.getJSONObject(0).getString("brief")
                val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

                val file = File(path, "${System.currentTimeMillis()}.jpg")
                file.createNewFile()
                val success = downloadImage(imageUrl, file)
                if (success) {
                    // 下载成功，使用本地文件显示图片
                    Constants.worksPicturePath = file.path
                    val photoBmp: Bitmap = BitmapFactory.decodeFile(file.path);
                    withContext(Dispatchers.Main){
                        mBinding.ivBg.setImageBitmap(photoBmp)
                        mBinding.flProgress.visibility = View.INVISIBLE
                    }

                } else {
                    // 下载失败，处理异常情况
                    withContext(Dispatchers.Main){
                        mBinding.flProgress.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), "识别失败", Toast.LENGTH_SHORT).show()
                    }


                }

            }

            println("________________________________________________")
        }
    }
}