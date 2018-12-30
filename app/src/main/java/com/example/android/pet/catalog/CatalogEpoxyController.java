package com.example.android.pet.catalog;

import com.airbnb.epoxy.AsyncEpoxyController;
import com.example.android.pet.data.Pet;

import java.util.Collections;
import java.util.List;

public class CatalogEpoxyController extends AsyncEpoxyController {

    private List<Pet> pets = Collections.emptyList();

    @Override
    protected void buildModels() {
        for (Pet pet : pets) {
            new PetEpoxyModel_()
                    .id(pet.getId())
                    .name(pet.getName())
                    .breed(pet.getBreed())
                    .addTo(this);
        }
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
        requestModelBuild();
    }
}
