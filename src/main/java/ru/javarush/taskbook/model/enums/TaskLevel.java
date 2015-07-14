package ru.javarush.taskbook.model.enums;

/**
 * Created by ilyakhromov on 20.01.15.
 */
public enum TaskLevel {
        BEGINNER("Beginner"),
        //PRE_JUNIOR("Pre junior"),
        JUNIOR("Junior"),
        //PRE_MIDDLE("Pre middle"),
        MIDDLE("Middle"),
        //PRE_SENIOR("Pre senior"),
        SENIOR("Senior");

    private final String level;

    TaskLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return level;
    }
}
