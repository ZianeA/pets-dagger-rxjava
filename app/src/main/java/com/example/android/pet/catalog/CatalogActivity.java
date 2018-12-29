package com.example.android.pet.catalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.pet.R;

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
