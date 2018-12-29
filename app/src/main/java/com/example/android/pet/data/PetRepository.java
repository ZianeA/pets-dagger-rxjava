package com.example.android.pet.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class PetRepository {

    @Inject
    public PetRepository() {
    }

    public Observable<List<Pet>> getPets() {
        return null;
    }
}
