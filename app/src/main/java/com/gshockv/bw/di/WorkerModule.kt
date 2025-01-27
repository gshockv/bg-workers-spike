package com.gshockv.bw.di

import android.content.Context
import com.gshockv.bw.data.BackgroundWorkerRepository
import com.gshockv.bw.task.TaskManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

  @Provides
  fun provideCoroutineScope(): CoroutineScope {
    return CoroutineScope(SupervisorJob() + Dispatchers.Default)
  }

  @Provides
  fun provideTaskManager(
    @ApplicationContext context: Context,
    coroutineScope: CoroutineScope,
    workerRepo: BackgroundWorkerRepository
  ): TaskManager {
    return TaskManager(
      context = context,
      coroutineScope = coroutineScope,
      workersRepository = workerRepo
    )
  }
}
