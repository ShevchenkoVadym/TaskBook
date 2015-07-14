package ru.javarush.taskbook.model.enums;

/**
 * Created by ilyakhromov on 28.01.15.
 */
public enum TestEnvironment {
    JAVA_JUNIT(Language.JAVA,Library.JUNIT),
    JAVA_TESTNG(Language.JAVA,Library.TESTNG),
    JAVA_JAVATESK(Language.JAVA,Library.JAVA_TESK),
    JAVA_SPOCK(Language.JAVA,Library.SPOCK),
    GROOVY_JUNIT(Language.GROOVY,Library.JUNIT),
    GROOVY_TESTNG(Language.GROOVY,Library.TESTNG),
    GROOVY_JAVATESK(Language.GROOVY,Library.JAVA_TESK),
    GROOVY_SPOCK(Language.GROOVY,Library.SPOCK);

    private Library lib;
    private Language lang;

    TestEnvironment(Language lang, Library lib){
        this.lang = lang;
        this.lib = lib;
    }

    public Library getLib() {
        return lib;
    }

    public Language getLang() {
        return lang;
    }

    public static TestEnvironment[] VALUES = TestEnvironment.values();
    public static TestEnvironment[] ACTUALLY_USED_VALUES = {JAVA_JUNIT, JAVA_TESTNG};

}
