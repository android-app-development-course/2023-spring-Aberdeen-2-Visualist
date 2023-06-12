package com.android.forum.ui.notifications.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.forum.R
import com.android.forum.data.WorksEntity


class GridAdapter(val context: Context, val works: List<WorksEntity>, val onclick :()->Unit ) : RecyclerView.Adapter<GridAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val path = works[holder.adapterPosition].path
        if (!TextUtils.isEmpty(path)){
            val photoBmp: Bitmap = BitmapFactory.decodeFile(path);
            holder.imageView.setImageBitmap(photoBmp)
        }


    }

    override fun getItemCount(): Int {
        return works.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView = itemView.findViewById(R.id.imageView)
    }

}

