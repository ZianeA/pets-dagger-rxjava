package com.example.android.pet.util.schedulers;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

    Scheduler io();

    Scheduler ui();

    Scheduler computation();
}
