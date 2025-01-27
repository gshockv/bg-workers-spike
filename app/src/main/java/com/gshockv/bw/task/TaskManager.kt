package com.gshockv.bw.task

import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.gshockv.bw.data.BackgroundWorker
import com.gshockv.bw.data.BackgroundWorkerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TaskManager @Inject constructor(
  private val context: Context,
  private val coroutineScope: CoroutineScope,
  private val workersRepository: BackgroundWorkerRepository
) {

  companion object {
    val TAG = TaskManager::class.simpleName
  }

  fun scheduleBackgroundWorkers() {
    coroutineScope.launch {
      workersRepository.loadActiveWorkers().collect { workers ->
        println("Found ${workers.size} active BG workers")

        workers.forEach { worker ->
          tryToScheduleWorker(worker)
        }
      }
    }
  }

  fun scheduleBackgroundWorker(workerId: Int) {
    coroutineScope.launch {
      val worker = workersRepository.loadWorker(id = workerId)
      tryToScheduleWorker(worker)
    }
  }

  private fun tryToScheduleWorker(worker: BackgroundWorker) {
    println("Trying to schedule BGH worker $worker")

    val workManager = WorkManager.getInstance(context)

    val infos = workManager.getWorkInfosForUniqueWork(worker.uniqueId).get()

    if (infos.isEmpty()) {
      Log.d(TAG, "Worker(${worker.uniqueId}) not found. Schedule it")
      doScheduleWorker(worker)
    }

    infos?.forEach { workInfo ->
      when (workInfo.state) {
        WorkInfo.State.RUNNING -> {
          Log.d(TAG, "Worker(${worker.uniqueId}) is running")
        }

        WorkInfo.State.ENQUEUED -> {
          Log.d(TAG, "Worker(${worker.uniqueId} already enqueued")
        }

        else -> {
          Log.d(TAG, "Worker(${worker.uniqueId}) is not active yet. Schedule it")
          doScheduleWorker(worker)
        }
      }
    }
  }

  private fun doScheduleWorker(worker: BackgroundWorker) {

    Log.d(TAG, "doScheduleWorker. worker ($worker)")

    val timeUnit = when (worker.schedulePeriod.periodUnit) {
      "hour" -> TimeUnit.HOURS
      else -> TimeUnit.MINUTES
    }

    val request = PeriodicWorkRequestBuilder<BackgroundWorkerPayload>(
      worker.schedulePeriod.period.toLong(), timeUnit
    ).setInputData(
      workDataOf(
        BackgroundWorkerPayload.WORKER_ID_PARAM to worker.id,
        BackgroundWorkerPayload.WORKER_INFO_PARAM to worker.uniqueId
      )
    ).build()

    Log.d(TAG, "Work request prepared")

    val result = WorkManager.getInstance(context).enqueueUniquePeriodicWork(
      uniqueWorkName = worker.uniqueId,
      existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
      request = request
    )
    Log.d(TAG, "Worker($worker) enqueue result = ${result.state.value}")
  }

}
