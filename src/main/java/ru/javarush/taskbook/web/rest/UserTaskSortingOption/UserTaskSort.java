package ru.javarush.taskbook.web.rest.UserTaskSortingOption;

import org.springframework.data.domain.Sort;

/**
 * Created by Александр on 11.03.15.
 */
public enum UserTaskSort {

    TRYIES_COUNT_HIGH_FIRST("-tryiesCount"),
    TRYIES_COUNT_LOW_FIRST("tryiesCount");


    public String sortOption;

    UserTaskSort(String sortOption) {
        this.sortOption = sortOption;
    }

    public static UserTaskSort getUserSortForName(String s)
    {
        return
                TRYIES_COUNT_HIGH_FIRST.sortOption.equals(s) ? TRYIES_COUNT_HIGH_FIRST:
                   TRYIES_COUNT_LOW_FIRST.sortOption.equals(s) ? TRYIES_COUNT_LOW_FIRST : null;
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
