package com.example.android.pet.editor;

import android.util.Log;

import com.example.android.pet.data.Pet;
import com.example.android.pet.data.PetRepository;
import com.example.android.pet.di.FragmentScope;
import com.example.android.pet.util.schedulers.SchedulerProvider;
import com.google.common.primitives.Ints;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

@FragmentScope
public class EditorPresenter {

    private static final String TAG = EditorPresenter.class.getSimpleName();

    private int petId;
    private EditorView view;
    private PetRepository repository;
    private SchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public EditorPresenter(int petId, EditorView view, PetRepository repository, SchedulerProvider schedulerProvider) {
        this.petId = petId;
        this.view = view;
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
    }

    public void start() {
        if (isAddPet()) return;

        Disposable disposable = repository.getPet(petId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(view::displayPet, throwable ->
                {
                    Log.d(TAG, "start: " + throwable.getMessage());
                    //TODO implement onError
                });


        compositeDisposable.add(disposable);
    }

    public void savePet(String name, String breed, String ageText) {
        if (!areArgumentsValid(name, breed, ageText)) {
            view.displayInvalidArgumentMessage();
            return;
        }

        Pet pet = new Pet(petId, name, breed, Ints.tryParse(ageText));
        Completable savePet;
        if (isAddPet()) {
            savePet = repository.insertPet(pet);
        } else {
            savePet = repository.updatePet(pet);
        }

        Disposable disposable = savePet
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(() -> {
                    view.displayPetSavedMessage();
                    view.displayPetCatalog();
                });

        compositeDisposable.add(disposable);
    }

    public void deletePet() {
        if (isAddPet()) {
            view.displayPetCatalog();
            return;
        }

        Disposable disposable = repository.deletePet(petId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(() -> {
                    view.displayPetDeletedMessage();
                    view.displayPetCatalog();
                });

        compositeDisposable.add(disposable);
    }

    //Todo call unsubscribe inside onPause
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private boolean areArgumentsValid(String name, String breed, String ageText) {
        return !(name.isEmpty() || breed.isEmpty() || ageText.isEmpty());
    }

    private boolean isAddPet() {
        return petId == 0;
    }
}
