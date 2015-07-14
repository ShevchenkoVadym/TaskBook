package ru.javarush.taskbook.web.rest.UserSortingOption;

import org.springframework.data.domain.Sort;

/**
 * Created by Александр on 16.02.15.
 */
public enum UserSort {

    RATING_HIGH_FIRST("-rating"),
    RATING_LOW_FIRST("rating"),

    TASKS_SOLVED_HIGH_FIRST("-tasksSolved"),
    TASKS_SOLVED_LOW_FIRST("tasksSolved"),

    USERNAME_HIGH_FIRST("-username"),
    USERNAME_LOW_FIRST("username"),

    COUNTRY_HIGH_FIRST("-country"),
    COUNTRY_LOW_FIRST("country");

    public String sortOption;

    UserSort(String sortOption) {
        this.sortOption = sortOption;
    }

    public static UserSort getUserSortForName(String s)
    {
        return RATING_HIGH_FIRST.sortOption.equals(s) ? RATING_HIGH_FIRST:
                 RATING_LOW_FIRST.sortOption.equals(s) ? RATING_LOW_FIRST:
                   TASKS_SOLVED_HIGH_FIRST.sortOption.equals(s) ? TASKS_SOLVED_HIGH_FIRST:
                     TASKS_SOLVED_LOW_FIRST.sortOption.equals(s) ? TASKS_SOLVED_LOW_FIRST:
                       USERNAME_HIGH_FIRST.sortOption.equals(s) ? USERNAME_HIGH_FIRST:
                         USERNAME_LOW_FIRST.sortOption.equals(s) ? USERNAME_LOW_FIRST:
                           COUNTRY_HIGH_FIRST.sortOption.equals(s) ? COUNTRY_HIGH_FIRST:
                             COUNTRY_LOW_FIRST.sortOption.equals(s) ? COUNTRY_LOW_FIRST : null;
    }


    public Sort getSortingOption()
    {
        return sortOption.startsWith("-") ?
                new Sort(Sort.Direction.DESC, sortOption.substring(1)):
                new Sort(Sort.Direction.ASC,sortOption);
    }


    @Override
    public String toString() {
        return sortOption;
    }

}
