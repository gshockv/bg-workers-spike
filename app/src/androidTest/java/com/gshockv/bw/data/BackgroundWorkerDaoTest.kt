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
      refreshPeriod = 30,
      refreshTimeUnit = "min"
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
      refreshPeriod = 10,
      refreshTimeUnit = "min"
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
      refreshPeriod = 10,
      refreshTimeUnit = "min"
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
      refreshPeriod = 10,
      refreshTimeUnit = "min"
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
      refreshPeriod = 10,
      refreshTimeUnit = "min"
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
       refreshPeriod = 10,
       refreshTimeUnit = "min"
     )
    database.backgroundWorkerDao().upsert(worker)

    assertNotNull(database.backgroundWorkerDao().load(worker.id))

    database.backgroundWorkerDao().delete(worker)
    assertNull(database.backgroundWorkerDao().load(worker.id))
  }
}
