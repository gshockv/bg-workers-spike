package com.gshockv.bw.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BackgroundWorker::class ], version = 1, exportSchema = false)
abstract class BgWorkerDatabase: RoomDatabase() {
    abstract fun backgroundWorkerDao(): BackgroundWorkerDao
}
