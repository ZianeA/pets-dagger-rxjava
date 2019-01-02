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
        return petDao.getPets();
    }

    @Override
    public Observable<Pet> getPet(int petId) {
        return petDao.getPet(petId);
    }

    @Override
    public Completable updatePet(Pet pet) {
        return petDao.updatePet(pet);
    }

    @Override
    public Completable insertPet(Pet pet) {
        return petDao.insertPet(pet);
    }
}
