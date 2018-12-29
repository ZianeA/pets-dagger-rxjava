package com.example.android.pet.di;

import android.app.Application;

import com.example.android.pet.PetApplication;
import com.example.android.pet.schedulers.SchedulerProvider;
import com.example.android.pet.schedulers.SchedulerProviderModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AppModule.class, AndroidSupportInjectionModule.class, ActivityBindingModule.class,
        SchedulerProviderModule.class})
public interface AppComponent {

    void inject(PetApplication petApplication);

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Application application);
    }
}
