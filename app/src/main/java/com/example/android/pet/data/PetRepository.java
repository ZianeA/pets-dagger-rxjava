package com.example.android.pet.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class PetRepository {

    private PetDataSource localDataSource;

    @Inject
    public PetRepository(PetDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }

    public Observable<List<Pet>> getPets() {
        return localDataSource.getPets();
    }

    public Observable<Pet> getPet(int petId) {
        return localDataSource.getPet(petId);
    }

    public Completable updatePet(Pet pet) {
        return localDataSource.updatePet(pet);
    }

    public Completable insertPet(Pet pet) {
        return localDataSource.insertPet(pet);
    }

    public Completable deleteAllPets() {
        return localDataSource.deleteAllPets();
    }
}
