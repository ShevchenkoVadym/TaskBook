package ru.javarush.taskbook.model.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: blacky
 * Date: 23.01.15
 */
public enum LifeStage {
    DRAFT(1),
    VERIFICATION(2),
    APPROVED(4),
    REJECTED(8),
    HIDDEN(16);

    private final int stage;
    private static final List<LifeStage> list = new ArrayList<LifeStage>(){
            {
                add(DRAFT);
                add(VERIFICATION);
                add(APPROVED);
                add(REJECTED);
                add(HIDDEN);
            }
    };

    public static final List<LifeStage> LIFE_STAGES = Collections.unmodifiableList(list);

    LifeStage(int stage) {
        this.stage = stage;
    }

    public static List<LifeStage> getLifeStages() {
        return LIFE_STAGES;
    }

    @Override
    public String toString() {
        return Integer.toString(stage);
    }
}
