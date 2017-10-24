package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

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
        List<LocalDate> exceedDateList = new ArrayList<>();
        boolean isExceed = false;
        int countCalories = 0;

        Collections.sort(mealList, new Comparator<UserMeal>() {
            @Override
            public int compare(UserMeal o1, UserMeal o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });

        countCalories += mealList.get(0).getCalories();

        for (int i = 1; i < mealList.size(); i++) {
            UserMeal meal = mealList.get(i - 1);
            UserMeal mealNext = mealList.get(i);

            if (!meal.getDateTime().toLocalDate().equals(mealNext.getDateTime().toLocalDate())) {
                countCalories = 0;
            }

            countCalories += mealNext.getCalories();

            if (countCalories > caloriesPerDay) {
                exceedDateList.add(meal.getDateTime().toLocalDate());
            }
        }

        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                isExceed = exceedDateList.contains(meal.getDateTime().toLocalDate());
                mealWithExceedsList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), isExceed));
            }
        }

        return mealWithExceedsList;
    }
}
