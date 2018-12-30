package com.example.android.pet.catalog;

import android.content.Intent;

import com.example.android.pet.R;
import com.example.android.pet.data.Pet;
import com.example.android.pet.data.PetRepository;
import com.example.android.pet.di.DaggerTestAppComponent;
import com.example.android.pet.di.TestAppComponent;
import com.example.android.pet.di.TestPetApplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import io.reactivex.Observable;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class CatalogActivityTest {

    private static final Pet PET = new Pet(101, "name", "breed", 11);
    private static final List<Pet> PETS = Collections.singletonList(PET);
    public static final Intent EMPTY_INTENT = new Intent();

    @Rule
    public ActivityTestRule<CatalogActivity> activityRule = new ActivityTestRule<CatalogActivity>(
            CatalogActivity.class, true, false);

    @Mock
    PetRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        TestPetApplication application = (TestPetApplication) InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getApplicationContext();

        DaggerTestAppComponent.builder()
                .application(application)
                .petRepository(repository)
                .build()
                .inject(application);
    }

    @Test
    public void displayPets_hideNoPetsMessage() {
        //Arrange
        when(repository.getPets()).thenReturn(Observable.just(PETS));

        //Act
        activityRule.launchActivity(EMPTY_INTENT);

        //Assert
        onView(withId(R.id.rv_pets)).perform(scrollTo(hasDescendant(withText(PET.getName()))));
        onView(withId(R.id.tv_no_pets)).check(matches(not(isDisplayed())));
    }

    @Test
    public void displayNoPets() {
        //Arrange
        when(repository.getPets()).thenReturn(Observable.just(Collections.emptyList()));

        //Act
        activityRule.launchActivity(EMPTY_INTENT);

        //Assert
        onView(withId(R.id.tv_no_pets)).check(matches(isDisplayed()));
    }

    @Test
    public void displayError() {
        //Arrange
        when(repository.getPets()).thenReturn(Observable.error(new Throwable()));

        //Act
        activityRule.launchActivity(EMPTY_INTENT);

        //Assert
        onView(withText(R.string.load_pets_error)).inRoot(withDecorView(not(activityRule.getActivity()
                .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}