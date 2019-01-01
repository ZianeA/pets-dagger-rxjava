package com.example.android.pet.catalog;

import util.SynchronousSchedulerProvider;

import com.example.android.pet.data.PetRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;

import io.reactivex.Observable;

import static org.mockito.Mockito.*;
import static util.PetTest.*;

@RunWith(JUnit4.class)
public class CatalogPresenterTest {

    private CatalogPresenter presenter;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private CatalogView view;

    @Mock
    private PetRepository repository;

    @Before
    public void setUp() throws Exception {
        presenter = new CatalogPresenter(view, repository, new SynchronousSchedulerProvider());
    }

    @Test
    public void getPets_passPetListToView() throws InterruptedException {
        //Arrange
        when(repository.getPets()).thenReturn(Observable.just(PETS));

        //Act
        presenter.loadPets();

        //Assert
        verify(view).displayPets(PETS);
    }

    @Test
    public void getPets_handleNoPets() {
        //Arrange
        when(repository.getPets()).thenReturn(Observable.just(Collections.emptyList()));

        //Act
        presenter.loadPets();

        //Assert
        verify(view).displayNoPets();
    }

    @Test
    public void getPets_handleError() {
        //Arrange
        when(repository.getPets()).thenReturn(Observable.error(new Throwable()));

        //Act
        presenter.loadPets();

        //Assert
        verify(view).displayError();
    }

    @Test
    public void editPet_passPetIdToPetView() {
        //Arrange

        //Act
        presenter.editPet(PET);

        //Assert
        verify(view).displayPetEditor(PET.getId());
    }

    @Test
    public void addPet_passPetIdOfZeroToView() {
        //Arrange

        //Act
        presenter.addPet();

        //Assert
        verify(view).displayPetEditor(0);
    }
}