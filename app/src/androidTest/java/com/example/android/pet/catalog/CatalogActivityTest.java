package com.example.android.pet.catalog;

import android.content.Intent;
import android.widget.Toast;

import com.example.android.pet.R;
import com.example.android.pet.data.PetRepository;
import com.example.android.pet.util.DaggerTestUtil;
import com.example.android.pet.editor.EditorActivity;
import com.example.android.pet.editor.EditorFragment;
import com.example.android.pet.util.ToastUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import io.reactivex.Completable;
import io.reactivex.Observable;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.android.pet.util.ToastUtil.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.*;
import static util.PetTest.*;

@RunWith(AndroidJUnit4.class)
public class CatalogActivityTest {

    private static final Intent EMPTY_INTENT = new Intent();

    @Rule
    public ActivityTestRule<CatalogActivity> activityRule = new ActivityTestRule<CatalogActivity>(
            CatalogActivity.class, true, false);

    @Mock
    private PetRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        init();

        DaggerTestUtil.buildComponentAndInjectApp(repository);
    }

    @After
    public void tearDown() throws Exception {
        release();

        clearToasts(activityRule.getActivity());
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
        isToastDisplayed(R.string.load_pets_error, activityRule.getActivity());
    }

    @Test
    public void displayPetEditor_handleAddPet() {
        //Arrange
        when(repository.getPets()).thenReturn(Observable.just(PETS));

        //Act
        activityRule.launchActivity(EMPTY_INTENT);
        onView(withId(R.id.fab_add_pet)).perform(click());

        //Assert
        intended(allOf(hasComponent(EditorActivity.class.getName()),
                IntentMatchers.hasExtra(EditorFragment.EXTRA_PET_ID, 0)));
    }

    @Test
    public void displayPetEditor_handleEditPet() {
        //Arrange
        when(repository.getPets()).thenReturn(Observable.just(PETS));
        when(repository.getPet(PET.getId())).thenReturn(Observable.just(PET));

        //Act
        activityRule.launchActivity(EMPTY_INTENT);
        onView(withId(R.id.rv_pets)).perform(scrollTo(hasDescendant(withText(PET.getName()))), click());

        //Assert
        intended(allOf(hasComponent(EditorActivity.class.getName()),
                IntentMatchers.hasExtra(EditorFragment.EXTRA_PET_ID, PET.getId())));
    }

    @Test
    public void displayPetsDeletedMessage() {
        //Arrange
        when(repository.getPets()).thenReturn(Observable.just(PETS));
        when(repository.deleteAllPets()).thenReturn(Completable.complete());

        //Act
        activityRule.launchActivity(EMPTY_INTENT);
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation()
                .getTargetContext());
        onView(withText(R.string.action_delete_all_pets)).perform(click());

        //Assert
        isToastDisplayed(R.string.pets_deleted_message, activityRule.getActivity());
    }
}