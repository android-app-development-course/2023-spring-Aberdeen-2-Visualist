package com.android.forum.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomWarnings


@Dao
interface WorksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: WorksEntity)

    @Insert
    suspend fun insert(post: List<WorksEntity>)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)

    @Query("SELECT * FROM works WHERE uid = :uid")
     fun getAll(uid: Long): LiveData<List<WorksEntity>>

}
@Dao
interface DownloadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: DownloadEntity)

    @Insert
    suspend fun insert(post: List<DownloadEntity>)

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM download_entity WHERE uid = :uid")
     fun getAll(uid: Long): LiveData<List<DownloadEntity>>
}

