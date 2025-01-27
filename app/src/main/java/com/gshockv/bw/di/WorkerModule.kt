package com.gshockv.bw.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
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
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoScope

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

  @IoScope
  @Provides
  fun provideIoScope(): CoroutineScope {
    return CoroutineScope(SupervisorJob() + Dispatchers.IO)
  }

  @Provides
  fun provideTaskManager(
    @ApplicationContext context: Context,
    @IoScope coroutineScope: CoroutineScope,
    workerRepo: BackgroundWorkerRepository
  ): TaskManager {
    return TaskManager(
      context = context,
      coroutineScope = coroutineScope,
      workersRepository = workerRepo
    )
  }

  @Provides
  fun provideWorkManagerConfiguration(workerFactory: HiltWorkerFactory) =
    Configuration.Builder()
      .setWorkerFactory(workerFactory)
      .build()

}
