package com.android.forum.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomWarnings
import androidx.room.Update


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Insert
    suspend fun insert(users: List<User>)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>


    @Query("SELECT * FROM user WhERE uid = :uid")
    suspend fun getUser(uid: Long): User

    @Query("SELECT * FROM user WhERE uid = :uid")
    fun getUserLive(uid: Long): LiveData<User>

    @Update
    suspend fun update(user: User): Int
}