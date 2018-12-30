package com.example.android.pet.editor;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.pet.R;
import com.example.android.pet.data.Pet;

public class EditorFragment extends Fragment implements EditorView{

    public static final String EXTRA_PET = "EXTRA PET";

    public EditorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void displayPet(Pet pet) {

    }

    @Override
    public void displayPetCatalog() {

    }

    @Override
    public void displayPetSavedMessage() {

    }

    @Override
    public void displayInvalidArgumentMessage() {

    }
}
