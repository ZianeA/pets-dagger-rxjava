package util;

import com.example.android.pet.data.Pet;

import org.junit.Assert;

import java.util.Collections;
import java.util.List;

public final class PetTest {

    public static final Pet PET = new Pet(101, "name", "breed", 11);

    public static final Pet NEW_PET = new Pet(0, "name", "breed", 11);

    public static final List<Pet> PETS = Collections.singletonList(PET);

    public static void assertEquals(Pet expected, Pet actual) {
        assertEquals(expected, actual, true);
    }

    public static void assertEquals(Pet expected, Pet actual, boolean assertId) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getBreed(), actual.getBreed());
        Assert.assertEquals(expected.getAge(), actual.getAge());
    }
}