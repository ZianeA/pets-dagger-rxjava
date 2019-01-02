package com.example.android.pet.util;

import com.example.android.pet.data.PetRepository;
import com.example.android.pet.di.DaggerTestAppComponent;
import com.example.android.pet.di.TestPetApplication;

import androidx.test.platform.app.InstrumentationRegistry;

public final class DaggerTestUtil {

    public static void buildComponentAndInjectApp(PetRepository repository){
        TestPetApplication application = (TestPetApplication) InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getApplicationContext();

        DaggerTestAppComponent.builder()
                .application(application)
                .petRepository(repository)
                .build()
                .inject(application);
    }
}
