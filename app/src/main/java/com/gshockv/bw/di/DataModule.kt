package com.gshockv.bw.di

import android.content.Context
import androidx.room.Room
import com.gshockv.bw.data.BackgroundWorkerDao
import com.gshockv.bw.data.BgWorkerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

  @Singleton
  @Provides
  fun provideDatabase(@ApplicationContext context: Context): BgWorkerDatabase {
    return Room.databaseBuilder(
      context.applicationContext,
      BgWorkerDatabase::class.java,
      "BgWorker.db"
    ).build()
  }

  @Provides
  fun provideBgWorkerDao(database: BgWorkerDatabase): BackgroundWorkerDao = database.backgroundWorkerDao()
}
