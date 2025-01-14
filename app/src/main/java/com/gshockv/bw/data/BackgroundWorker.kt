package com.gshockv.bw.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
   tableName = "background_worker"
)
data class BackgroundWorker(
  @PrimaryKey val id: Int,
  val name: String,
  val active: Boolean,
  val refreshPeriod: Int,
  val refreshTimeUnit: String
)
