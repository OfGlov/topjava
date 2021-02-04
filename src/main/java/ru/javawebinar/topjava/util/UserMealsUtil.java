package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000);
        //mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, List<UserMeal>> map = new HashMap<>();
        meals.forEach(meal -> {
            List<UserMeal> newList = new ArrayList<>();
            newList.add(meal);
            map.merge(meal.getDateTime().toLocalDate(), newList, (oldVal, newVal) -> {
                List<UserMeal> meals1 = new ArrayList<>(oldVal);
                meals1.addAll(newList);
                return meals1;
            });
        });

        List<UserMealWithExcess> filteredMeals = new ArrayList<>();
        for (Map.Entry<LocalDate, List<UserMeal>> entry : map.entrySet()) {
            List<UserMeal> values = entry.getValue();
            int count = 0;
            boolean isExcess = false;
            for (UserMeal meal : values) {
                count += meal.getCalories();
            }
            if (count > caloriesPerDay) {
                isExcess = true;
            }

            for (UserMeal meal : values) {
                if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    UserMealWithExcess userMeal = new UserMealWithExcess(meal.getDateTime(), meal.getDescription(),
                            meal.getCalories(), isExcess);
                    filteredMeals.add(userMeal);
                }
            }
        }
        return filteredMeals;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> filteredMeals = new ArrayList<>();
        meals.stream().collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate())).forEach((key, value) -> {
            int sum = value.stream().mapToInt(UserMeal::getCalories).sum();
            boolean isExcess = false;
            if (sum > caloriesPerDay) {
                isExcess = true;
            }
            boolean finalIsExcess = isExcess;
            value.forEach(meal -> {
                if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    UserMealWithExcess userMeal = new UserMealWithExcess(meal.getDateTime(), meal.getDescription(),
                            meal.getCalories(), finalIsExcess);
                    filteredMeals.add(userMeal);
                }
            });
        });
        return filteredMeals;
    }
}