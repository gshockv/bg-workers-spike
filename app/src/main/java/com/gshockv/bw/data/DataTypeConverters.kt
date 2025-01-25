package com.gshockv.bw.data

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DataTypeConverters {
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
