package com.gshockv.bw.task

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gshockv.bw.data.LogEntry
import com.gshockv.bw.data.LogEntryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime

@HiltWorker
class BackgroundWorkerPayload @AssistedInject constructor(
  @Assisted context: Context,
  @Assisted params: WorkerParameters,
  private val logRepo: LogEntryRepository
) : CoroutineWorker(context, params) {

  init {
    Log.d(TAG, "BackgroundWorkerPayload INITIALIZED...")
  }

  companion object {
    val TAG = BackgroundWorkerPayload::class.simpleName
    const val WORKER_ID_PARAM = "worker_id"
    const val WORKER_INFO_PARAM = "worker_info"
  }

  override suspend fun doWork(): Result {
    val workerId = inputData.getInt(WORKER_ID_PARAM, 0)
    val workerInfo = inputData.getString(WORKER_INFO_PARAM)

    Log.d(TAG, "BG worker($workerInfo) is doing its JOB...")

    val logEntry = LogEntry(
      id = 0,
      workerId = workerId,
      workerInfo = "Worker info = ($workerInfo)",
      recordCreated = LocalDateTime.now()
    )

    Log.d(TAG, "Trying to record log entry (${logEntry.workerInfo})")

    logRepo.writeLogEntry(logEntry)

    return Result.success()
  }

}
