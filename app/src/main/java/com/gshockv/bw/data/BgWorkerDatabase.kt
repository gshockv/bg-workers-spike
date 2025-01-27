package com.gshockv.bw.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

  companion object {
    @Volatile
    private var instance: BgWorkerDatabase? = null

    fun getInstance(context: Context): BgWorkerDatabase {
      return instance ?: synchronized(this) {
        instance ?: buildDatabase(context).also {
          instance = it
        }
      }
    }

    private fun buildDatabase(context: Context): BgWorkerDatabase {
      return Room.databaseBuilder(
        context,
        BgWorkerDatabase::class.java,
        "BgWorker.db"
      ).build()
    }
  }
}
