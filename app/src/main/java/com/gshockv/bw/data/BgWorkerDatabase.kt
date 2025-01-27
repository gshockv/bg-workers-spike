package com.gshockv.bw.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
  entities = [
    BackgroundWorker::class,
    LogEntry::class
  ],
  version = 1,
  exportSchema = false
)
@TypeConverters(DataTypeConverters::class)
abstract class BgWorkerDatabase : RoomDatabase() {
  abstract fun backgroundWorkerDao(): BackgroundWorkerDao

  abstract fun logEntryDao(): LogEntryDao
}
