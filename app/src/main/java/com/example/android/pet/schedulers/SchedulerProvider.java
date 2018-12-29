package com.example.android.pet.schedulers;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler io();

    Scheduler ui();

    Scheduler computation();
}
