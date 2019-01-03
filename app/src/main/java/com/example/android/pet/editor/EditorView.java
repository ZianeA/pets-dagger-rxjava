package com.example.android.pet.editor;

import com.example.android.pet.data.Pet;

interface EditorView {
    void displayPet(Pet pet);

    void displayPetCatalog();

    void displayPetSavedMessage();

    void displayInvalidArgumentMessage();

    void displayPetDeletedMessage();
}
