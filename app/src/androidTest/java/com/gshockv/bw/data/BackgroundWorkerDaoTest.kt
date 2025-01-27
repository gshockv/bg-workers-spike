package com.gshockv.bw.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BackgroundWorkerDaoTest {
  private lateinit var database: BgWorkerDatabase

  @Before
  fun initDb() {
    database = Room.inMemoryDatabaseBuilder(
      getApplicationContext(),
      BgWorkerDatabase::class.java
    ).allowMainThreadQueries().build()
  }

  @Test
  fun initiallyWorkersListIsEmpty() = runTest {
    assertEquals(0, database.backgroundWorkerDao().observeAll().first().size)
  }

  @Test
  fun initiallyNoWorkersCanBeLoadedById() = runTest {
    assertNull(database.backgroundWorkerDao().load(123))
  }

  @Test
  fun insertWorkerAndGetWorkers() = runTest {
    val worker = BackgroundWorker(
      id = 1,
      name = "Worker 1",
      active = true,
      schedulePeriod = SchedulePeriod.FIFTEEN_MINUTES
    )
    database.backgroundWorkerDao().upsert(worker)

    val workers = database.backgroundWorkerDao().observeAll().first()

    assertEquals(1, workers.size)
    assertEquals(worker, workers[0])
  }

  @Test
  fun insertWorkerAndLoadIt() = runTest {
    val worker = BackgroundWorker(
      id = 1,
      name = "Inserted Worker",
      active = true,
       schedulePeriod = SchedulePeriod.THIRTY_MINUTES
    )
    database.backgroundWorkerDao().upsert(worker)

    val fromDb = database.backgroundWorkerDao().load(1)
    assertEquals(worker, fromDb)
  }

  @Test
  fun updateWorker() = runTest {
    val worker = BackgroundWorker(
      id = 1,
      name = "A Worker",
      active = false,
      schedulePeriod = SchedulePeriod.ONE_HOUR
    )
    database.backgroundWorkerDao().upsert(worker)

    val updated = worker.copy(
      name = "Updated Worker",
      active = false
    )
    database.backgroundWorkerDao().upsert(updated)

    val fromDb = database.backgroundWorkerDao().load(worker.id)
    assertEquals(updated, fromDb)
  }

  @Test
  fun makeWorkerNotActive() = runTest {
    val worker = BackgroundWorker(
      id = 1,
      name = "Active Worker",
      active = true,
      schedulePeriod = SchedulePeriod.FIFTEEN_MINUTES
    )
    database.backgroundWorkerDao().upsert(worker)
    assertTrue(database.backgroundWorkerDao().load(worker.id).active)

    database.backgroundWorkerDao().updateActive(worker.id, false)
    assertFalse(database.backgroundWorkerDao().load(worker.id).active)
  }

  @Test
  fun makeWorkerActive() = runTest {
    val worker = BackgroundWorker(
      id = 1,
      name = "Not Active Worker",
      active = false,
      schedulePeriod = SchedulePeriod.FORTY_FIVE_MINUTES
    )
    database.backgroundWorkerDao().upsert(worker)
    assertFalse(database.backgroundWorkerDao().load(worker.id).active)

    database.backgroundWorkerDao().updateActive(worker.id, true)
    assertTrue(database.backgroundWorkerDao().load(worker.id).active)
  }

  @Test
  fun deleteWorker() = runTest {
     val worker = BackgroundWorker(
       id = 42,
       name = "Bg Worker",
       active = true,
       schedulePeriod = SchedulePeriod.THIRTY_MINUTES
     )
    database.backgroundWorkerDao().upsert(worker)

    assertNotNull(database.backgroundWorkerDao().load(worker.id))

    database.backgroundWorkerDao().delete(worker)
    assertNull(database.backgroundWorkerDao().load(worker.id))
  }

  @Test
  fun loadActiveWorkers() = runTest {
    val testWorkers = prepareWorkers()

    val allWorkersCount = testWorkers.size
    val activeWorkersCount = testWorkers.filter { it.active }.size

    testWorkers.forEach {
      database.backgroundWorkerDao().upsert(it)
    }

    assertEquals(allWorkersCount,
      database.backgroundWorkerDao().observeAll().first().size)
    assertEquals(activeWorkersCount,
      database.backgroundWorkerDao().loadActiveWorkers().first().size)
  }

  private fun prepareWorkers() = listOf(
    prepareTestWorker("worker_1", true),
    prepareTestWorker("worker_2", true),
    prepareTestWorker("worker_3", false),
    prepareTestWorker("worker_4", false),
    prepareTestWorker("worker_5", true)
  )

  private fun prepareTestWorker(name: String, active: Boolean) =
    BackgroundWorker(
      id = 0,
      name = name,
      active = active,
      schedulePeriod = SchedulePeriod.FIFTEEN_MINUTES
    )
}
