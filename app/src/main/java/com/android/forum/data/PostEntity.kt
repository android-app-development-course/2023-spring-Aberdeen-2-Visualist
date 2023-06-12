package com.android.forum.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "post_entity")
data class PostEntity(
    @ColumnInfo(name = "path") var path: String,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "tag") var tag: Boolean,
    @ColumnInfo(name = "time") var time: Long,
    @ColumnInfo(name = "likeCount") var likeCount: Int,
    @ColumnInfo(name = "uid") var uid: Long,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0
)

@Entity(tableName = "works")
data class WorksEntity(
    @ColumnInfo(name = "path") var path: String,
    @ColumnInfo(name = "uid") var uid: Long,
    @ColumnInfo(name = "time") var time: Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0
)

@Entity(tableName = "download_entity")
data class DownloadEntity(
    @ColumnInfo(name = "path") var path: String,
    @ColumnInfo(name = "uid") var uid: Long,
    @ColumnInfo(name = "tag") var tag: Boolean,
    @ColumnInfo(name = "content") var content: String,
    @ColumnInfo(name = "time") var time: Long,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0
)

