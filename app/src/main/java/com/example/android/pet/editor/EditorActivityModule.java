package com.example.android.pet.editor;

import com.example.android.pet.di.FragmentScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EditorActivityModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = EditorFragmentModule.class)
    abstract EditorFragment editorFragment();
}
