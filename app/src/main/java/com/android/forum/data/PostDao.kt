package com.android.forum.data

import androidx.room.*


@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)
    @Insert
    suspend fun insert(post: List<PostEntity>)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM post_entity")
    suspend fun getAll(): List<PostEntity>

    @Query("SELECT * FROM post_entity WHERE uid = :uid")
    suspend fun getAllByUid(uid: Long): List<PostEntity>

    @Query("SELECT * FROM post_entity WHERE id = :id")
    suspend fun getPostById(id: Long): PostEntity

    @Query("DELETE FROM post_entity WHERE id = :id")
    suspend fun deletePostById(id: Long)

    @Query("UPDATE post_entity SET likeCount = :likeCount WHERE id = :id")
    suspend fun updatePostLikeCount(id: Long, likeCount: Int)

}

