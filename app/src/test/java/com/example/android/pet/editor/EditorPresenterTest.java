package com.example.android.pet.editor;

import com.example.android.pet.data.Pet;
import com.example.android.pet.data.PetRepository;

import util.SynchronousSchedulerProvider;

import static util.PetTest.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Completable;
import io.reactivex.Observable;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class EditorPresenterTest {

    private EditorPresenter presenter;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private PetRepository repository;

    @Mock
    private EditorView view;

    @Before
    public void setUp() throws Exception {
        presenter = getPresenter(0);
        when(repository.insertPet(any(Pet.class))).thenReturn(Completable.complete());
        when(repository.updatePet(any(Pet.class))).thenReturn(Completable.complete());
    }

    @Test
    public void start_editPet_passPetToView() {
        //Arrange
        when(repository.getPet(anyInt())).thenReturn(Observable.just(PET));
        presenter = getPresenter(PET.getId());

        //Act
        presenter.start();

        //Assert
        verify(view).displayPet(PET);
    }

    @Test
    public void start_handleAddPet() {
        //Arrange

        //Act
        presenter.start();

        //Assert
        verify(repository, never()).getPet(anyInt());
    }

    @Test
    public void savePet_handleAddPet() {
        //Arrange
        ArgumentCaptor<Pet> petCaptor = ArgumentCaptor.forClass(Pet.class);

        //Act
        presenter.savePet(PET.getName(), PET.getBreed(), String.valueOf(PET.getAge()));

        //Assert
        verify(view).displayPetSavedMessage();
        verify(view).displayPetCatalog();
        verify(repository).insertPet(petCaptor.capture());
        assertEquals(NEW_PET, petCaptor.getValue());
    }

    @Test
    public void savePet_handleEditPet() {
        //Arrange
        ArgumentCaptor<Pet> petCaptor = ArgumentCaptor.forClass(Pet.class);
        presenter = getPresenter(PET.getId());

        //Act
        presenter.savePet(PET.getName(), PET.getBreed(), String.valueOf(PET.getAge()));

        //Assert
        verify(view).displayPetSavedMessage();
        verify(view).displayPetCatalog();
        verify(repository).updatePet(petCaptor.capture());
        assertEquals(PET, petCaptor.getValue());
    }

    //region invalid arguments tests
    @Test
    public void savePet_handleEmptyName() {
        presenter.savePet("", PET.getBreed(), String.valueOf(PET.getAge()));

        verify(view).displayInvalidArgumentMessage();
        verify(repository, never()).insertPet(any(Pet.class));
    }

    @Test
    public void savePet_handleEmptyBreed() {
        presenter.savePet(PET.getName(), "", String.valueOf(PET.getAge()));

        verify(view).displayInvalidArgumentMessage();
        verify(repository, never()).insertPet(any(Pet.class));
    }

    @Test
    public void savePet_handleEmptyAge() {
        presenter.savePet(PET.getName(), PET.getBreed(), "");

        verify(view).displayInvalidArgumentMessage();
        verify(repository, never()).insertPet(any(Pet.class));
    }
    //endregion

    private EditorPresenter getPresenter(int id) {
        return new EditorPresenter(id, view, repository, new SynchronousSchedulerProvider());
    }
}
