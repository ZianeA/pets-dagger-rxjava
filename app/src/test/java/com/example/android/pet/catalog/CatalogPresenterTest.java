package com.example.android.pet.catalog;

import com.example.android.pet.catalog.util.SynchronousSchedulerProvider;
import com.example.android.pet.data.Pet;
import com.example.android.pet.data.PetRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class CatalogPresenterTest {

    private static final Pet PET = new Pet(101, "name", "breed", 11);
    private static final List<Pet> PETS = Collections.singletonList(PET);

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
}