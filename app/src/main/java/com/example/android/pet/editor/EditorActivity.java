package com.example.android.pet.editor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import android.os.Bundle;

import com.example.android.pet.R;

import javax.inject.Inject;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        EditorFragment editorFragment = (EditorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_holder);

        if (editorFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_holder, new EditorFragment())
                    .commit();
        }
    }
}
