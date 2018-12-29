package com.example.android.pet.catalog;

import com.example.android.pet.di.FragmentScope;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CatalogFragmentModule {

    @FragmentScope
    @Binds
    abstract CatalogView provideCatalogView(CatalogFragment fragment);
}
