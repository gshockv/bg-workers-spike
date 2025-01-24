package com.gshockv.bw.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
  entities = [BackgroundWorker::class],
  version = 1,
  exportSchema = false
)
@TypeConverters(DataTypeConverter::class)
abstract class BgWorkerDatabase : RoomDatabase() {
  abstract fun backgroundWorkerDao(): BackgroundWorkerDao
}

private class DataTypeConverter {
  @TypeConverter
  fun fromSchedulePeriod(value: SchedulePeriod?): Int? {
    return value?.ordinal
  }

  @TypeConverter
  fun toSchedulePeriod(value: Int?): SchedulePeriod? {
    return value?.let {
      SchedulePeriod.entries[it]
    }
  }
}
