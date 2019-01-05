package com.example.android.pet.di;

import com.example.android.pet.catalog.CatalogActivity;
import com.example.android.pet.catalog.CatalogFragment;
import com.example.android.pet.catalog.CatalogFragmentModule;
import com.example.android.pet.editor.EditorActivity;
import com.example.android.pet.editor.EditorActivityModule;
import com.example.android.pet.editor.EditorFragment;
import com.example.android.pet.editor.EditorFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = CatalogFragmentModule.class)
    abstract CatalogFragment catalogFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = EditorFragmentModule.class)
    abstract EditorFragment editorFragment();
}
