package com.android.forum.ui.dashboard

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.forum.R
import com.android.forum.data.GlobalData
import com.android.forum.data.PostEntity
import com.android.forum.data.timestampToString


class PostAdapter(
    private val posts: List<PostEntity>, private val itemClickListener: PostItemClickListener
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pathView: ImageView = view.findViewById(R.id.iv_picture)
        val avatar: ImageView = view.findViewById(R.id.iv_avatar)
        val name: TextView = view.findViewById(R.id.tv_name)
        val contextTextView: TextView = view.findViewById(R.id.tv_content)
        val timeTextView: TextView = view.findViewById(R.id.tv_time)
//        val likeCountTextView: TextView = view.findViewById(R.id.likeCountTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]


        if (post.tag){
            holder.pathView.setImageURI(Uri.parse(post.path))
        }else{
            holder.pathView.setImageBitmap(convertImageToBitmap(post.path))
        }
        holder.pathView.setOnClickListener {
            GlobalData.picture
            itemClickListener.onItemClick(post)
        }
        holder.contextTextView.text = post.content
        holder.timeTextView.text = post.time.timestampToString()
        post.likeCount

    }


    override fun getItemCount(): Int {
        return posts.size
    }

}
interface PostItemClickListener {
    fun onItemClick(post: PostEntity)
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
