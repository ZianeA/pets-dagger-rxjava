package com.example.android.pet.data.local;

import com.example.android.pet.data.Pet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;
import util.PetTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static util.PetTest.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@RunWith(AndroidJUnit4.class)
public class LocalPetDataSourceTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private PetDatabase petDatabase;
    private LocalPetDataSource localDataSource;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        petDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().getContext(), PetDatabase.class)
                .allowMainThreadQueries()
                .build();

        localDataSource = new LocalPetDataSource(petDatabase.petDao());
    }

    @After
    public void tearDown() throws Exception {
        petDatabase.close();
    }

    @Test
    public void insertPet() {
        //Arrange

        //Act, Assert
        localDataSource.insertPet(PET).test()
                .assertComplete();
    }

    @Test
    public void insertPet_autoIncrementId() {
        //Arrange

        //Act
        localDataSource.insertPet(NEW_PET).test()
                .assertComplete();

        //Assert
        localDataSource.getPet(1).test()
                .assertNoErrors();
    }

    @Test
    public void getPet() {
        //Arrange
        localDataSource.insertPet(PET).subscribe();

        //Act, Assert
        localDataSource.getPet(PET.getId())
                .subscribe(pet -> assertEquals(PET, pet));
    }

    @Test
    public void updatePet() {
        //Arrange
        localDataSource.insertPet(PET);
        Pet updatedPet = new Pet(PET.getId(), "updatedName", "updatedBreed", 201);

        //Act, Assert
        localDataSource.updatePet(updatedPet).test()
                .assertComplete();

        localDataSource.getPet(PET.getId())
                .subscribe(pet -> assertEquals(updatedPet, pet));
    }

    @Test
    public void getPets() {
        //Arrange
        localDataSource.insertPet(PET).subscribe();

        //Act, Assert
        localDataSource.getPets().subscribe(pets -> {
            assertEquals(1, pets.size());
            assertEquals(PET, pets.get(0));
        });
    }

    @Test
    public void getPets_multiplePets() {
        //Arrange
        int petCount = 3;
        for (int i = 0; i < petCount; i++) {
            localDataSource.insertPet(NEW_PET).subscribe();
        }

        //Act, Assert
        localDataSource.getPets()
                .subscribe(pets -> assertEquals(petCount, pets.size()));

    }

    @Test
    public void deleteAllPets() {
        //Arrange
        for (int i = 0; i < 3; i++) {
            localDataSource.insertPet(NEW_PET).subscribe();
        }

        //Act, Assert
        localDataSource.deleteAllPets().test()
                .assertComplete();

        localDataSource.getPets().test()
                .assertValue(List::isEmpty);
    }

    @Test
    public void deletePet() {
        //Arrange
        localDataSource.insertPet(PET).subscribe();

        //Act, Assert
        localDataSource.deletePet(PET.getId()).test()
                .assertComplete();

        localDataSource.getPets().test()
                .assertValue(List::isEmpty);

    }
}