package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;

    public static final Meal breakfast = new Meal(MEAL_ID, LocalDateTime.of(2021, Month.MARCH, 1, 8, 0), "Завтрак", 500);
    public static final Meal lunch = new Meal(MEAL_ID + 1, LocalDateTime.of(2021, Month.MARCH, 1, 13, 0), "Обед", 500);
    public static final Meal dinner = new Meal(MEAL_ID + 2, LocalDateTime.of(2021, Month.MARCH, 1, 19, 0), "Ужин", 500);

    public static Meal getUpdated() {
        Meal updated = new Meal(breakfast);
        updated.setId(MEAL_ID);
        updated.setCalories(600);
        updated.setDescription("Полдник");
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("userId").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("userId").isEqualTo(expected);
    }
}
