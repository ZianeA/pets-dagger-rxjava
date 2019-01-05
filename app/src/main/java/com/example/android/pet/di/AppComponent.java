package com.example.android.pet.di;

import android.app.Application;

import com.example.android.pet.PetApplication;
import com.example.android.pet.data.PetRepositoryModule;
import com.example.android.pet.util.schedulers.SchedulerProviderModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, FragmentBindingModule.class,
        SchedulerProviderModule.class, PetRepositoryModule.class})
public interface AppComponent {

    void inject(PetApplication petApplication);

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Application application);
    }
}
