package com.example.android.pet.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class PetRepository {

    @Inject
    public PetRepository() {
    }

    public Observable<List<Pet>> getPets() {
        return null;
    }

    public Observable<Pet> getPet(int petId) {
        return null;
    }

    public Completable updatePet(Pet pet) {
        return null;
    }

    public Completable insertPet(Pet pet){
        return null;
    }
}
