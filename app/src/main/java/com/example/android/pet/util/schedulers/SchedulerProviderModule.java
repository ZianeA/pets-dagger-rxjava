package com.example.android.pet.util.schedulers;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SchedulerProviderModule {

    @Singleton
    @Binds
    abstract SchedulerProvider provideSchedulerProvider(AsynchronousSchedulerProvider schedulerProvider);
}
