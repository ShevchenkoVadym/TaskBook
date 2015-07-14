package ru.javarush.taskbook.model.auditors;

import org.springframework.data.domain.AuditorAware;
import ru.javarush.taskbook.util.CurrentUser;

/**
 * Created by ilyakhromov on 12.02.15.
 */
public class TaskAuditor implements AuditorAware<String> {
    @Override
    public String getCurrentAuditor() {
        return CurrentUser.get() != null?CurrentUser.get().getUsername():"Populator";
    }
}
