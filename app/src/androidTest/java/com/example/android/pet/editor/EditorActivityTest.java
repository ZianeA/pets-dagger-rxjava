package com.example.android.pet.editor;

import android.content.Intent;
import android.widget.Toast;

import com.example.android.pet.R;
import com.example.android.pet.catalog.CatalogActivity;
import com.example.android.pet.data.Pet;
import com.example.android.pet.data.PetRepository;
import com.example.android.pet.util.DaggerTestUtil;
import com.example.android.pet.util.ToastUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import io.reactivex.Completable;
import io.reactivex.Observable;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.pet.util.ToastUtil.*;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static util.PetTest.*;

@RunWith(AndroidJUnit4.class)
public class EditorActivityTest {

    @Rule
    public ActivityTestRule<EditorActivity> activityRule = new ActivityTestRule<>(EditorActivity.class,
            true, false);

    @Mock
    private PetRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        DaggerTestUtil.buildComponentAndInjectApp(repository);
        when(repository.getPets()).thenReturn(Observable.just(PETS));
    }

    @After
    public void tearDown() throws Exception {
        clearToasts(activityRule.getActivity());
    }

    @Test
    public void displayPet_fillEditTexts() {
        //Arrange
        when(repository.getPet(PET.getId())).thenReturn(Observable.just(PET));

        //Act
        activityRule.launchActivity(getIntent(PET.getId()));

        //Assert
        onView(withId(R.id.et_name)).check(matches(withText(PET.getName())));
        onView(withId(R.id.et_breed)).check(matches(withText(PET.getBreed())));
        onView(withId(R.id.et_age)).check(matches(withText(String.valueOf(PET.getAge()))));
    }

    @Test
    public void displayPetSavedMessage() {
        //Arrange
        when(repository.insertPet(any(Pet.class))).thenReturn(Completable.complete());

        //Act
        activityRule.launchActivity(getIntent(0));
        onView(withId(R.id.et_name)).perform(typeText(PET.getName()));
        onView(withId(R.id.et_breed)).perform(typeText(PET.getBreed()));
        onView(withId(R.id.et_age)).perform(typeText(String.valueOf(PET.getAge())), closeSoftKeyboard());
        onView(withId(R.id.fab_save_pet)).perform(click());

        //Assert
        isToastDisplayed(R.string.pet_saved_message, activityRule.getActivity());
    }

    @Test
    public void displayPetCatalog() {
        //Arrange
        init();
        when(repository.updatePet(any(Pet.class))).thenReturn(Completable.complete());
        when(repository.getPet(PET.getId())).thenReturn(Observable.just(PET));

        //Act
        activityRule.launchActivity(getIntent(PET.getId()));
        onView(withId(R.id.fab_save_pet)).perform(click());

        //Assert
        intended(hasComponent(CatalogActivity.class.getName()));
        release();
    }

    @Test
    public void displayInvalidArgumentMessage() {
        //Arrange

        //Act
        activityRule.launchActivity(getIntent(0));
        onView(withId(R.id.fab_save_pet)).perform(click());

        //Assert
        isToastDisplayed(R.string.invalid_argument_message, activityRule.getActivity());
    }

    @Test
    public void displayPetDeletedMessage() {
        //Arrange
        when(repository.getPet(PET.getId())).thenReturn(Observable.just(PET));
        when(repository.deletePet(PET.getId())).thenReturn(Completable.complete());

        //Act
        activityRule.launchActivity(getIntent(PET.getId()));
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation()
                .getTargetContext());
        onView(withText(R.string.action_delete_pet)).perform(click());

        //Assert
        isToastDisplayed(R.string.pet_deleted_message, activityRule.getActivity());
    }

    private Intent getIntent(int petId) {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(),
                EditorActivity.class);
        intent.putExtra(EditorFragment.EXTRA_PET_ID, petId);

        return intent;
    }
}