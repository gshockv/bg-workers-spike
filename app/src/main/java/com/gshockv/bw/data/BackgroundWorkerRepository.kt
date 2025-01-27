package com.gshockv.bw.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class BackgroundWorkerRepository @Inject constructor(
  private val dao: BackgroundWorkerDao
) {

  fun observeAll(): Flow<List<BackgroundWorker>> {
    return dao.observeAll()
  }

  fun loadActiveWorkers(): Flow<List<BackgroundWorker>> {
    return dao.loadActiveWorkers()
  }

  suspend fun loadWorker(id: Int): BackgroundWorker {
    return dao.load(workerId = id)
  }

  suspend fun saveWorker(worker: BackgroundWorker) {
    dao.upsert(worker)
  }

  suspend fun deleteWorker(worker: BackgroundWorker) {
    dao.delete(worker)
  }
}
