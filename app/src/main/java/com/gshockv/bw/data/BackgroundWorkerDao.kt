package com.gshockv.bw.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BackgroundWorkerDao {

  @Query("SELECT * FROM background_worker")
  fun observeAll(): Flow<List<BackgroundWorker>>

  @Query("SELECT * FROM background_worker WHERE id = :workerId")
  suspend fun load(workerId: Int): BackgroundWorker

  @Upsert
  suspend fun upsert(worker: BackgroundWorker)

  @Query("UPDATE background_worker SET active = :active WHERE id = :workerId")
  suspend fun updateActive(workerId: Int, active: Boolean)

  @Delete
  suspend fun delete(worker: BackgroundWorker)

}
