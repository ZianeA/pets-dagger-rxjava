package com.example.android.pet.catalog;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.pet.R;
import com.example.android.pet.data.Pet;
import com.example.android.pet.editor.EditorActivity;
import com.example.android.pet.editor.EditorFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class CatalogFragment extends Fragment implements CatalogView {

    @Inject
    CatalogPresenter presenter;

    @BindViews({R.id.tv_no_pets, R.id.iv_no_pets})
    List<View> noPetsViews;

    @VisibleForTesting
    Toast toast;

    private final CatalogEpoxyController epoxyController = new CatalogEpoxyController();
    private Unbinder unbinder;

    public CatalogFragment() {
        // Required empty public constructor
    }

    //region some life cycle methods
    @Override
    public void onResume() {
        super.onResume();
        presenter.loadPets();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
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
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.catalog_fragment_menu, menu);
    }

    //endregion

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_pets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        epoxyController.setOnPetClickListener(presenter::editPet);
        recyclerView.setAdapter(epoxyController.getAdapter());

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_pet);
        fab.setOnClickListener(v -> presenter.addPet());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_pets:
                presenter.deleteAllPets();
                break;
        }

        return true;
    }

    @Override
    public void displayPets(List<Pet> pets) {
        epoxyController.setPets(pets);
        setNoPetsViewsVisibility(View.GONE);
    }


    @Override
    public void displayNoPets() {
        setNoPetsViewsVisibility(View.VISIBLE);
    }

    @Override
    public void displayError() {
        toast = Toast.makeText(getContext(), R.string.load_pets_error,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void displayPetEditor(int petId) {
        Intent intent = new Intent(getContext(), EditorActivity.class);
        intent.putExtra(EditorFragment.EXTRA_PET_ID, petId);
        startActivity(intent);
    }

    @Override
    public void displayPetsDeletedMessage() {
        epoxyController.setPets(Collections.emptyList());

        toast = Toast.makeText(getContext(), R.string.pets_deleted_message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    private void setNoPetsViewsVisibility(int visibility) {
        for (View view : noPetsViews) {
            view.setVisibility(visibility);
        }
    }
}
