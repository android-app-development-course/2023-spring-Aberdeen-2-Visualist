package com.android.forum.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @ColumnInfo(name = "uid")
    var uid: Long,

    @ColumnInfo(name = "phone")
    var phone :String,

    @ColumnInfo(name = "password")
    var password :String,

    @ColumnInfo(name = "username")
    var username :String,

    @ColumnInfo(name = "signature")
    var signature :String,

    @ColumnInfo(name = "avatar")
    var avatar :String = "",

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0
)

data class UserAndPostEntity(
    var user :User,
    var post :List<PostEntity>
)
