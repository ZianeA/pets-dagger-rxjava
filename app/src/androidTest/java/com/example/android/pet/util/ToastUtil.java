package com.example.android.pet.util;

import android.app.Activity;
import android.widget.Toast;

import com.example.android.pet.R;

import java.lang.reflect.Field;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.test.platform.app.InstrumentationRegistry;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

public class ToastUtil {

    public static void isToastDisplayed(@StringRes int toastText, Activity activity) {
        onView(withText(toastText)).inRoot(withDecorView(not(activity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    public static void clearToasts(AppCompatActivity activity)
            throws NoSuchFieldException, IllegalAccessException {

        Fragment fragment = activity.getSupportFragmentManager()
                .findFragmentById(R.id.fragment_holder);

        Field field = fragment.getClass().getDeclaredField("toast");
        field.setAccessible(true);
        Toast toast = (Toast) field.get(fragment);

        if (toast != null) {
            toast.cancel();
        }
    }
}
