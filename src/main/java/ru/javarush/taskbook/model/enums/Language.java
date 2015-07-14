package ru.javarush.taskbook.model.enums;

/**
 * Created by ilyakhromov on 28.01.15.
 */
public enum Language {
    JAVA("Java"),
    GROOVY("Groovy"),
    C("C"),
    RUBY("Ruby"),
    OBJECTIVE_C("Objective-C"),
    CPP("C++"),
    NET(".NET"),
    DELPHY("Delphy"),
    ERLANG("Erlang"),
    PERL("PERL"),
    PHP("PHP"),
    PYTHON("Python"),
    VISUALBASIC("Visual Basic"),
    PLSQL("PL/SQL"),
    TSQL("T-SQL"),
    ACTIONSCRYPT2("ActionScript2.0"),
    ACTIONSCRYPT3("ActionScript3.0"),
    JAVASCRIPT("Javascript");

    private String lang;
    Language(String lang){
        this.lang = lang;
    }

    @Override
    public String toString() {
        return lang;
    }
}
