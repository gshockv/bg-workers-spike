package com.gshockv.bw.data

enum class SchedulePeriod(val period: Int, val periodUnit: String) {
  FIFTEEN_MINUTES(15, "min"),
  THIRTY_MINUTES(30, "min"),
  FORTY_FIVE_MINUTES(45, "min"),
  ONE_HOUR(1, "hour"),
  ONE_AND_HALF_HOUR(90, "min");

  override fun toString(): String {
    return "$period $periodUnit"
  }
}
