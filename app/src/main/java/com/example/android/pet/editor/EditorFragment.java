package com.example.android.pet.editor;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.pet.R;
import com.example.android.pet.catalog.CatalogActivity;
import com.example.android.pet.data.Pet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

public class EditorFragment extends Fragment implements EditorView {

    public static final String EXTRA_PET = "EXTRA PET";

    @Inject
    EditorPresenter presenter;

    @BindView(R.id.et_name)
    EditText name;
    @BindView(R.id.et_breed)
    EditText breed;
    @BindView(R.id.et_age)
    EditText age;

    @VisibleForTesting()
    Toast toast;

    private Unbinder unbinder;

    public EditorFragment() {
        // Required empty public constructor
    }

    //region life cycle methods
    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editor, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }
    //endregion

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_save_pet);
        fab.setOnClickListener(v -> {
            presenter.savePet(name.getText().toString(), breed.getText().toString(),
                    age.getText().toString());
        });
    }

    @Override
    public void displayPet(Pet pet) {
        name.setText(pet.getName());
        breed.setText(pet.getBreed());
        age.setText(String.valueOf(pet.getAge()));
    }

    @Override
    public void displayPetCatalog() {
        Intent intent = new Intent(getContext(), CatalogActivity.class);
        startActivity(intent);
    }

    @Override
    public void displayPetSavedMessage() {
        toast = Toast.makeText(getContext(), R.string.pet_saved_message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void displayInvalidArgumentMessage() {
        toast = Toast.makeText(getContext(), R.string.invalid_argument_message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
