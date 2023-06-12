package com.android.forum.ui.dashboard

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.forum.data.AppDatabase
import com.android.forum.data.GlobalData
import com.android.forum.data.PostEntity
import com.android.forum.databinding.ActivityEditBinding
import com.android.forum.databinding.ActivityPictuerBinding
import com.android.forum.file.XActivityLauncher
import com.android.forum.file.uriToFile
import com.android.forum.global.Constants

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private val launcher = XActivityLauncher(this)

    var path:String= ""
    val appDatabase = AppDatabase.getInstance().postDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardAlbum.setOnClickListener { view->

            launcher.launch("image/*") { data->
                data?.let { it ->

                    uriToFile(data,view.context).let {file ->
                        if (file != null) {
                            path = file.path
                        }

                    }
                    val photoBmp: Bitmap = MediaStore.Images.Media.getBitmap(
                        view.context.contentResolver,
                        it
                    )
                    binding.ivPicture.setImageBitmap(photoBmp)
                }
            }
        }
        binding.btnSave.setOnClickListener {
            save()
        }
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun save(){
        GlobalScope.launch(Dispatchers.IO) {
            val content = binding.etContent.text.toString()
            val post = PostEntity(path,content,false,System.currentTimeMillis(),0, GlobalData.uid)
            appDatabase.insertPost(post)
            finish()
        }
    }
}