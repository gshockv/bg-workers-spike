package com.gshockv.bw.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogEntryRepository @Inject constructor(
  private val logDao: LogEntryDao
) {
  fun observeWorkerLogs(workerId: Int): Flow<List<LogEntry>> {
    return logDao.observeWorkerLogs(workerId)
  }

  suspend fun writeLogEntry(log: LogEntry) {
    logDao.writeLogEntry(log)
  }

  suspend fun deleteWorkerLogs(workerId: Int) {
    logDao.deleteWorkerLogs(workerId)
  }
}
