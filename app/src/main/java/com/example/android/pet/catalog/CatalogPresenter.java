package com.example.android.pet.catalog;

import com.example.android.pet.data.PetRepository;
import com.example.android.pet.schedulers.SchedulerProvider;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CatalogPresenter {

    private CatalogView view;
    private PetRepository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SchedulerProvider schedulerProvider;

    public CatalogPresenter(CatalogView view, PetRepository repository, SchedulerProvider schedulerProvider) {
        this.view = view;
        this.repository = repository;
        this.schedulerProvider = schedulerProvider;
    }

    public void loadPets() {
        Disposable disposable = repository.getPets()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(pets -> {
                    if (pets.isEmpty()) {
                        view.displayNoPets();
                    } else {
                        view.displayPets(pets);
                    }
                }, throwable -> view.displayError());

        compositeDisposable.add(disposable);
    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
