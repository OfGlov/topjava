package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDAO {
    void add(Meal meal);

    void update(Meal meal);

    List<Meal> getMealsList();

    void delete(int mealId);

    Meal getById(int mealId);
}
