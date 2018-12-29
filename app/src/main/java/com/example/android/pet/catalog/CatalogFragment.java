package com.example.android.pet.catalog;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.support.AndroidSupportInjection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.pet.R;
import com.example.android.pet.data.Pet;

import java.util.List;

import javax.inject.Inject;

public class CatalogFragment extends Fragment implements CatalogView {

    @Inject
    CatalogPresenter presenter;

    public CatalogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void displayPets(List<Pet> pets) {

    }

    @Override
    public void displayNoPets() {

    }

    @Override
    public void displayError() {

    }
}
