package com.example.android.pet.catalog;

import com.example.android.pet.di.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class CatalogActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = CatalogFragmentModule.class)
    abstract CatalogFragment catalogFragment();
}
