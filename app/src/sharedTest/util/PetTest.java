package util;

import com.example.android.pet.data.Pet;

import java.util.Collections;
import java.util.List;

public final class PetTest {

    public static final Pet PET = new Pet(101, "name", "breed", 11);

    public static final Pet NEW_PET = new Pet(0, "name", "breed", 11);

    public static final List<Pet> PETS = Collections.singletonList(PET);

}