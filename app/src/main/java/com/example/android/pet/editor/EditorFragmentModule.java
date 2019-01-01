package com.example.android.pet.editor;

import com.example.android.pet.di.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class EditorFragmentModule {

    @FragmentScope
    @Binds
    abstract EditorView provideEditorView(EditorFragment editorFragment);

    @FragmentScope
    @Provides
    static int providePetId(EditorFragment editorFragment){
        return editorFragment.getActivity()
                .getIntent()
                .getIntExtra(EditorFragment.EXTRA_PET_ID, 0);
    }
}
