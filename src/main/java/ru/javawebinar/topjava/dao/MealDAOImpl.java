package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealDAOImpl implements MealDAO {

    private final List<Meal> meals = new CopyOnWriteArrayList<>();

    {
        meals.add(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 600));
        meals.add(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 20));
        meals.add(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public void add(Meal meal) {
        int id = meals.size() + 1;
        meal.setId(id);
        meals.add(meal);
    }

    @Override
    public void update(Meal meal) {
        meals.set(meal.getId()-1, meal);
    }

    @Override
    public List<Meal> getMealsList() {
        return meals;
    }

    @Override
    public void delete(int mealId) {
        meals.remove(mealId - 1);
    }

    @Override
    public Meal getById(int mealId) {
        return meals.get(mealId - 1);
    }
}
