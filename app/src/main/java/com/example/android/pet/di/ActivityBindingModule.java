package com.example.android.pet.di;

import com.example.android.pet.catalog.CatalogActivity;
import com.example.android.pet.catalog.CatalogActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = CatalogActivityModule.class)
    abstract CatalogActivity catalogActivity();
}
