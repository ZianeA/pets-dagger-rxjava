package com.example.android.pet.catalog;

import com.example.android.pet.data.PetRepository;

public class CatalogPresenter {

    private CatalogView view;
    private PetRepository repository;

    public CatalogPresenter(CatalogView view, PetRepository repository) {
        this.view = view;
        this.repository = repository;
    }
}
