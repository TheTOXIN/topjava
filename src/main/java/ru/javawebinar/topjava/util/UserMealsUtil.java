package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TheTOXIN
 * 24.10.2017.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> mealWithExceedsList = new ArrayList<>();
        HashMap<LocalDate, Integer> hashMeal = new HashMap<>();

        mealList
                .forEach(meal -> hashMeal.put(meal.getDateTime().toLocalDate(), (hashMeal.get(meal.getDateTime().toLocalDate()) == null ? 0 : hashMeal.get(meal.getDateTime().toLocalDate())) + meal.getCalories()));
        
        mealList
                .stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .forEach(meal -> mealWithExceedsList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), !(hashMeal.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))));

        return mealWithExceedsList;
    }
}
