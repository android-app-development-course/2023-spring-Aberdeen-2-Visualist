package com.android.forum.ui.notifications.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.forum.R
import com.android.forum.data.DownloadEntity
import com.android.forum.data.WorksDao
import com.android.forum.data.WorksEntity
import com.android.forum.data.timestampToString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ExamineADapter(val context: Context, val downloads: List<WorksEntity>) : RecyclerView.Adapter<ExamineADapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_download, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val download = downloads[holder.adapterPosition]

        GlobalScope.launch(Dispatchers.Main) {
            if (true){
                val bieBitmap = withContext(Dispatchers.IO){
                    Uri.parse(download.path)
                }
                holder.imageView.setImageURI(bieBitmap)
            }else{
                val bieBitmap = withContext(Dispatchers.IO){
                    convertImageToBitmap(download.path)
                }
                holder.imageView.setImageBitmap(bieBitmap)
            }
        }
        holder.name.text = ""
        holder.time.text  = download.time.timestampToString()
        val shapeDrawable = GradientDrawable()
        shapeDrawable.shape = GradientDrawable.RECTANGLE
        shapeDrawable.cornerRadius = 12f // 圆角半径
        holder.imageView.background = shapeDrawable
    }

    override fun getItemCount(): Int {
        return downloads.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView = itemView.findViewById(R.id.iv_bg)
        val name: TextView = itemView.findViewById(R.id.name)
        val time: TextView = itemView.findViewById(R.id.time)
    }

}

private fun convertImageToBitmap(imagePath: String?): Bitmap? {
    var bitmap: Bitmap? = null
    val options = BitmapFactory.Options()
    options.inPreferredConfig = Bitmap.Config.ARGB_8888
    try {
        bitmap = BitmapFactory.decodeFile(imagePath, options)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return bitmap
}
