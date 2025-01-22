package com.gshockv.bw.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class BackgroundWorkerRepository @Inject constructor(
  private val dao: BackgroundWorkerDao
) {

  fun observeAll(): Flow<List<BackgroundWorker>> {

    // TODO: For UI debug purpose only
    return flowOf(testWorkers)
//    return flowOf(emptyList())

    //return dao.observeAll()
  }

  suspend fun createWorker(name: String, active: Boolean, refreshPeriod: Int,
                           refreshTimeUnit: String) {
    val worker = BackgroundWorker(
      id = 0, name, active, refreshPeriod, refreshTimeUnit
    )
    dao.upsert(worker)
  }

  suspend fun loadWorker(id: Int): BackgroundWorker {
    return dao.load(workerId = id)
  }
}

val testWorkers = listOf(
  BackgroundWorker(
    id = 1,
    name = "First Worker",
     active = true,
    refreshPeriod = 30,
    refreshTimeUnit = "min"
  ),
  BackgroundWorker(
    id = 2,
    name = "Second Worker",
    active = true,
    refreshPeriod = 2,
    refreshTimeUnit = "hour"
  ),
  BackgroundWorker(
    id = 3,
    name = "Not Active Worker",
    active = false,
    refreshPeriod = 45,
    refreshTimeUnit = "min"
  ),
  BackgroundWorker(
    id = 4,
    name = "Another Staff",
    active = false,
    refreshPeriod = 30,
    refreshTimeUnit = "hour"
  ),
  BackgroundWorker(
    id = 5,
    name = "Second Floor",
    active = true,
    refreshPeriod = 15,
    refreshTimeUnit = "min"
  )
)
