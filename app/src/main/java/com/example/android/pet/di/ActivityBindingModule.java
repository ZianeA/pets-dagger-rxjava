package com.example.android.pet.di;

import com.example.android.pet.catalog.CatalogActivity;
import com.example.android.pet.catalog.CatalogActivityModule;
import com.example.android.pet.editor.EditorActivity;
import com.example.android.pet.editor.EditorActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = CatalogActivityModule.class)
    abstract CatalogActivity catalogActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = EditorActivityModule.class)
    abstract EditorActivity editorActivity();
}
