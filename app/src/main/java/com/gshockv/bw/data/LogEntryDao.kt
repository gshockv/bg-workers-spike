package com.gshockv.bw.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface LogEntryDao {
  @Query("SELECT * FROM worker_log_entry WHERE workerId = :workerId")
  fun observeWorkerLogs(workerId: Int): Flow<List<LogEntry>>

  @Upsert
  suspend fun writeLogEntry(entry: LogEntry)

  @Query("DELETE FROM worker_log_entry WHERE workerId = :workerId")
  suspend fun deleteWorkerLogs(workerId: Int)
}
