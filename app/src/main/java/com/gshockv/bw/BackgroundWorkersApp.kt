package com.gshockv.bw

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.gshockv.bw.task.TaskManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BackgroundWorkersApp : Application(), Configuration.Provider {

  @Inject
  lateinit var workerFactory: HiltWorkerFactory

  @Inject
  lateinit var taskManager: TaskManager

  override fun onCreate() {
    super.onCreate()
    taskManager.scheduleBackgroundWorkers()
  }

  override val workManagerConfiguration: Configuration
    get() = Configuration.Builder()
      .setWorkerFactory(workerFactory)
      .setMinimumLoggingLevel(android.util.Log.DEBUG)
      .build()
}
