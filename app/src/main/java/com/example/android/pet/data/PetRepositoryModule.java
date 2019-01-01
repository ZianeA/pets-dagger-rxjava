package com.example.android.pet.data;

import android.app.Application;

import com.example.android.pet.data.local.LocalPetDataSource;
import com.example.android.pet.data.local.PetDao;
import com.example.android.pet.data.local.PetDatabase;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class PetRepositoryModule {

    @Singleton
    @Binds
    abstract PetDataSource providePetDataSource(LocalPetDataSource localDataSource);

    @Singleton
    @Provides
    static PetDao providePetDao(PetDatabase petDatabase) {
        return petDatabase.petDao();
    }

    @Singleton
    @Provides
    static PetDatabase providePetDatabase(Application application) {
        return Room.databaseBuilder(application, PetDatabase.class, "pet_database")
                .build();
    }
}
