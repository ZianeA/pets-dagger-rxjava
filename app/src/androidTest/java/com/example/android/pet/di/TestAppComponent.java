package com.example.android.pet.di;

import android.app.Application;

import com.example.android.pet.data.PetRepository;
import com.example.android.pet.util.schedulers.SchedulerProviderModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class, AndroidSupportInjectionModule.class, ActivityBindingModule.class,
        SchedulerProviderModule.class})
public interface TestAppComponent {

    void inject(TestPetApplication testPetApplication);

    @Component.Builder
    interface Builder {
        TestAppComponent build();

        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder petRepository(PetRepository repository);
    }
}
