package com.gshockv.bw.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(
  entities = [
    BackgroundWorker::class,
    LogEntry::class
  ],
  version = 1,
  exportSchema = false
)
@TypeConverters(DataTypeConverter::class)
abstract class BgWorkerDatabase : RoomDatabase() {
  abstract fun backgroundWorkerDao(): BackgroundWorkerDao

  abstract fun logEntryDao(): LogEntryDao
}

private class DataTypeConverter {
  private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

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

  @TypeConverter
  fun fromLocalDateTimeToString(value: LocalDateTime?): String? {
    return value?.format(dateTimeFormatter)
  }

  @TypeConverter
  fun toLocalDateTime(value: String?): LocalDateTime? {
    return value?.let {
      LocalDateTime.parse(it, dateTimeFormatter)
    }
  }
}
