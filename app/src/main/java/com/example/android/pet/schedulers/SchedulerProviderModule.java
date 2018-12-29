package com.example.android.pet.schedulers;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class SchedulerProviderModule {

    @Singleton
    @Binds
    abstract SchedulerProvider provideSchedulerProvider(AsynchronousSchedulerProvider schedulerProvider);
}
