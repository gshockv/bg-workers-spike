package com.gshockv.bw.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
  tableName = "worker_log_entry"
)
data class LogEntry(
  @PrimaryKey(autoGenerate = true) val id: Long,
  val workerId: Int,
  val workerInfo: String,
  val recordCreated: LocalDateTime
)
