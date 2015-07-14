package ru.javarush.taskbook.web.rest.TaskSortingOptions;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by ilyakhromov on 09.02.15.
 */

public enum TaskSort {

    RATING_HIGH_FIRST("-rating"),
    RATING_LOW_FIRST("rating"),

    VOTERS_AMOUNT_HIGH_FIRST("-votersAmount"),
    VOTERS_AMOUNT_LOW_FIRST("votersAmount"),

    SUCCESSFUL_ATTEMPTS_HIGH_FIRST("-successfulAttempts"),
    SUCCESSFUL_ATTEMPTS_LOW_FIRST("successfulAttempts"),

    TASKNAME_HIGH_FIRST("-taskName"),
    TASKNAME_LOW_FIRST("taskName"),

    AUTHOR_HIGH_FIRST("-author"),
    AUTHOR_LOW_FIRST("author"),

    LEVEL_HIGH_FIRST("-level"),
    LEVEL_LOW_FIRST("level"),

    LIFESTAGE_HIGH_FIRST("-lifeStage"),
    LIFESTAGE_LOW_FIRST("lifeStage"),

    NEW_FIRST("-creationDate"),
    OLD_FIRST("creationDate");

    private String sortOption;

    TaskSort(String sortOption) {
        this.sortOption = sortOption;
    }

    public static TaskSort getTaskSortForName(String s){
        return RATING_HIGH_FIRST.sortOption.equals(s)?RATING_HIGH_FIRST:
                 RATING_LOW_FIRST.sortOption.equals(s)?RATING_LOW_FIRST:
                   VOTERS_AMOUNT_HIGH_FIRST.sortOption.equals(s)?VOTERS_AMOUNT_HIGH_FIRST:
                     VOTERS_AMOUNT_LOW_FIRST.sortOption.equals(s)?VOTERS_AMOUNT_LOW_FIRST:
                       SUCCESSFUL_ATTEMPTS_HIGH_FIRST.sortOption.equals(s)?SUCCESSFUL_ATTEMPTS_HIGH_FIRST:
                         SUCCESSFUL_ATTEMPTS_LOW_FIRST.sortOption.equals(s)?SUCCESSFUL_ATTEMPTS_LOW_FIRST:
                           TASKNAME_HIGH_FIRST.sortOption.equals(s)?TASKNAME_HIGH_FIRST:
                             TASKNAME_LOW_FIRST.sortOption.equals(s)?TASKNAME_LOW_FIRST:
                               AUTHOR_HIGH_FIRST.sortOption.equals(s)?AUTHOR_HIGH_FIRST:
                                 AUTHOR_LOW_FIRST.sortOption.equals(s)?AUTHOR_LOW_FIRST:
                                   LEVEL_HIGH_FIRST.sortOption.equals(s)?LEVEL_HIGH_FIRST:
                                     LEVEL_LOW_FIRST.sortOption.equals(s)?LEVEL_LOW_FIRST:
                                       LIFESTAGE_HIGH_FIRST.sortOption.equals(s)?LIFESTAGE_HIGH_FIRST:
                                         LIFESTAGE_LOW_FIRST.sortOption.equals(s)?LIFESTAGE_LOW_FIRST:
                                           NEW_FIRST.sortOption.equals(s)?NEW_FIRST:
                                             OLD_FIRST.sortOption.equals(s)?OLD_FIRST:null;
                                               // RATING_HIGH_FIRST;// default value


    }

    public Sort getSortingOption(){
        return sortOption.startsWith("-")?
                (new Sort(Sort.Direction.DESC,sortOption.substring(1))):
                (new Sort(Sort.Direction.ASC,sortOption));
    }


    @Override
    public String toString() {
        return sortOption;
    }
}
