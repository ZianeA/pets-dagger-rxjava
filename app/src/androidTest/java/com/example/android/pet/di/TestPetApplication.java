package com.example.android.pet.di;

import android.app.Activity;
import android.app.Application;

import com.example.android.pet.PetApplication;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class TestPetApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
