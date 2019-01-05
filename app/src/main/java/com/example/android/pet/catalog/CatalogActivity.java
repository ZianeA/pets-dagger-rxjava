package com.example.android.pet.catalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import android.os.Bundle;

import com.example.android.pet.R;

import javax.inject.Inject;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        CatalogFragment fragment = (CatalogFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_holder);

        if (fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_holder, new CatalogFragment())
                    .commit();
        }
    }
}
