package com.example.android.pet.catalog;

import com.example.android.pet.data.Pet;

import java.util.List;

interface CatalogView {

    void displayPets(List<Pet> pets);

    void displayNoPets();

    void displayError();

    void displayPetEditor(int petId);

    void displayPetsDeletedMessage();
}
