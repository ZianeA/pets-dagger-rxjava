package com.example.android.pet.catalog;

import com.example.android.pet.data.PetRepository;
import com.example.android.pet.di.FragmentScope;
import com.example.android.pet.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@FragmentScope
public class CatalogPresenter {

    private CatalogView view;
    private PetRepository repository;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private SchedulerProvider schedulerProvider;

    @Inject
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

    //TODO call this method in onPause
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
