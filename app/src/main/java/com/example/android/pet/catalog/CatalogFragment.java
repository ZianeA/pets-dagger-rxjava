package com.example.android.pet.catalog;


import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.pet.R;
import com.example.android.pet.data.Pet;

import java.util.List;

import javax.inject.Inject;

public class CatalogFragment extends Fragment implements CatalogView {

    @Inject
    CatalogPresenter presenter;

    @BindViews({R.id.tv_no_pets, R.id.iv_no_pets})
    List<View> noPetsViews;

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
        return view;
    }
    //endregion

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv_pets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(epoxyController.getAdapter());
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
        Toast.makeText(getContext(), getResources().getString(R.string.load_pets_error),
                Toast.LENGTH_SHORT).show();
    }

    private void setNoPetsViewsVisibility(int visibility) {
        for (View view : noPetsViews) {
            view.setVisibility(visibility);
        }
    }
}
