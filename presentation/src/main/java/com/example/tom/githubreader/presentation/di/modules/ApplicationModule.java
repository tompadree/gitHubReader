
package com.example.tom.githubreader.presentation.di.modules;

import android.content.Context;
import com.example.data.cache.ResultCache;
import com.example.data.cache.ResultCacheImpl;
import com.example.data.executor.JobExecutor;
import com.example.data.repository.ResultDataRepository;
import com.example.domain.executor.PostExecutionThread;
import com.example.domain.executor.ThreadExecutor;
import com.example.domain.repository.ResultRepository;
import com.example.tom.githubreader.presentation.AndroidApplication;
import com.example.tom.githubreader.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides @Singleton Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton
  ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides @Singleton
  PostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides @Singleton
  ResultCache provideResultCache(ResultCacheImpl resultCache) {
    return resultCache;
  }

  @Provides @Singleton
  ResultRepository provideResultRepository(ResultDataRepository resultDataRepository) {
    return resultDataRepository;
  }
}
