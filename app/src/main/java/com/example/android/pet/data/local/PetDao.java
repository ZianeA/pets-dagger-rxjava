package com.example.android.pet.data.local;

import com.example.android.pet.data.Pet;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Observable;

@Dao
public interface PetDao {

    @Query("SELECT * FROM Pet")
    Observable<List<Pet>> getPets();

    @Query("SELECT * FROM pet WHERE id = :petId")
    Observable<Pet> getPet(int petId);

    @Insert
    Completable insertPet(Pet pet);

    @Update
    Completable updatePet(Pet pet);
}
