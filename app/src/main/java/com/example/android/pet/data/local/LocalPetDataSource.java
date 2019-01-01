package com.example.android.pet.data.local;

import com.example.android.pet.data.Pet;
import com.example.android.pet.data.PetDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class LocalPetDataSource implements PetDataSource {

    private PetDao petDao;

    @Inject
    public LocalPetDataSource(PetDao petDao) {
        this.petDao = petDao;
    }

    @Override
    public Observable<List<Pet>> getPets() {
        return null;
    }

    @Override
    public Observable<Pet> getPet(int petId) {
        return null;
    }

    @Override
    public Completable updatePet(Pet pet) {
        return null;
    }

    @Override
    public Completable insertPet(Pet pet) {
        return null;
    }
}
