package com.android.forum.ui.dashboard.adapter

import com.android.forum.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.forum.data.PostEntity
import com.android.forum.data.UserAndPostEntity
import com.android.forum.global.Constants
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class VerticalAdapter(val context: Context,val userAndPosts: List<UserAndPostEntity>,val onClick:()-> Unit ={}) : RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vertical, parent, false)
        return VerticalViewHolder(view)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        holder.mHorizontalRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
       val data = userAndPosts[holder.adapterPosition]
        holder.mHorizontalRecyclerView.adapter = HorizontalAdapter(data.post){
            Constants.userDetail = data.user
            onClick.invoke()
        }

        val user = userAndPosts[holder.adapterPosition].user
        if (!TextUtils.isEmpty(user.avatar)){
            val photoBmp: Bitmap = BitmapFactory.decodeFile(user.avatar);
            holder.iv_avatar.setImageBitmap(photoBmp)
        }
        holder.title.text = user.username
        holder.subtitle.text = user.signature
    }

    override fun getItemCount(): Int {
        return userAndPosts.size
    }

    inner class VerticalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mHorizontalRecyclerView: RecyclerView = itemView.findViewById(R.id.horizontalRecyclerView)
        val title: TextView = itemView.findViewById(R.id.title)
        val subtitle: TextView = itemView.findViewById(R.id.subtitle)
        val iv_avatar: ShapeableImageView = itemView.findViewById(R.id.iv_avatar)
    }

    inner class HorizontalAdapter(val posts: List<PostEntity>,val onHorizontalClick:()-> Unit ={}) : RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_horizontal, parent, false)
            return HorizontalViewHolder(view)
        }

        override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
            val post = posts[holder.adapterPosition]

            GlobalScope.launch(Dispatchers.Main) {
                if (post.tag){
                    val bieBitmap = withContext(Dispatchers.IO){
                        Uri.parse(post.path)
                    }
                    holder.imageView.setImageURI(bieBitmap)
                }else{
                    val bieBitmap = withContext(Dispatchers.IO){
                        convertImageToBitmap(post.path)
                    }
                    holder.imageView.setImageBitmap(bieBitmap)
                }
            }
            holder.imageView.setOnClickListener {
                Constants.postDetail = post
                onHorizontalClick.invoke()
            }

        }

        override fun getItemCount(): Int {
            return posts.size
        }

        inner class HorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: AppCompatImageView = itemView.findViewById(R.id.imageView)
        }
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
