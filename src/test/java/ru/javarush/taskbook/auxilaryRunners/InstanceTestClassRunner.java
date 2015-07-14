package ru.javarush.taskbook.auxilaryRunners;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ilyakhromov on 25.12.14.
 */
public class InstanceTestClassRunner extends SpringJUnit4ClassRunner {

    private InstanceTestClassListener instanceSetupListener;

    public InstanceTestClassRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Object createTest() throws Exception {
        Object test = super.createTest();
        if (test instanceof InstanceTestClassListener && instanceSetupListener == null) {
            instanceSetupListener = (InstanceTestClassListener) test;
            instanceSetupListener.beforeClassSetup();
        }
        return test;
    }

    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
        if (instanceSetupListener != null)
            instanceSetupListener.afterClassSetup();
    }
}