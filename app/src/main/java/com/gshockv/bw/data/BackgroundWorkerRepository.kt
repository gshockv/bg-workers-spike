package com.gshockv.bw.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BackgroundWorkerRepository @Inject constructor(
  private val dao: BackgroundWorkerDao
) {

  fun observeAll(): Flow<List<BackgroundWorker>> {
    return dao.observeAll()
  }

  suspend fun createNewWorker(name: String, active: Boolean, refreshPeriod: Int, refreshTimeUnit: String) {
    val worker = BackgroundWorker(
      id = 0, name, active, refreshPeriod, refreshTimeUnit
    )
    dao.upsert(worker)
  }
}
