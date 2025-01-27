package com.gshockv.bw.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
   tableName = "background_worker"
)
data class BackgroundWorker(
  @PrimaryKey(autoGenerate = true) val id: Int,
  val name: String,
  val active: Boolean,
  val schedulePeriod: SchedulePeriod
) {
  override fun toString(): String {
    return uniqueId
  }

  val uniqueId: String
    get() = "$id-$name:$schedulePeriod"
}
