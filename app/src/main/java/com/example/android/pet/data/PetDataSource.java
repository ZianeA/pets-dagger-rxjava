package com.example.android.pet.data;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface PetDataSource {
    Observable<List<Pet>> getPets();

    Observable<Pet> getPet(int petId);

    Completable updatePet(Pet pet);

    Completable insertPet(Pet pet);
}
