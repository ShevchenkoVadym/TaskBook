package ru.javarush.taskbook.model.enums;

/**
 * Created by ilyakhromov on 28.01.15.
 */

public enum Library {
    JUNIT("JUnit"),
    TESTNG("TestNG"),
    JAVA_TESK("JavaTESK"),
    SPOCK("Spock"),
    CUNIT("CUint"),
    CTESK("CTESK"),
    CFIX("cfix"),
    UNITY("Unity"),
    MICRO_UNIT("MICRO_UNIT"),
    RSPEC("Rspec"),
    TEST_UNIT("Test::Unit"),
    OCUNIT("OCUnit"),
    CXXTEST("CxxTest"),
    CPPUNIT("CppUnit"),
    BOOST_TEST("Boost Test"),
    GOOGLE("Google C++"),
    NUNIT("MUnit"),
    XUNIT("XUnit"),
    MBUNIT("MBUnit"),
    DUNIT("DUnit"),
    EUNIT("EUnit"),
    TEST("Test"),
    TEST_MORE("Test::More"),
    TEST_SIMPLE("Test::Simple"),
    TEST_UNIT_LIGHT("Test::Unit::Light"),
    SIMPLE_TEST("SimpleTest"),
    PHP_UNIT("PHPUnit"),
    PYUNIT("PyUnit"),
    PYTEST("PyTest"),
    NOSE("Nose"),
    VBUNIT("vbUnit"),
    UTPLSQL("utPLSQL"),
    TSQLUNIT("TSQLUnit"),
    SPUNIT("SPUnit"),
    ASUNIT("AsUnit"),
    AS2UNIT("AS2Unit"),
    FLEXUNIT("FlexUnit"),
    MOCHA("Mocha"),
    CHAI("Chai"),
    SINONJS("Simon.JS"),
    KARMARUNNER("Karma runner"),
    QUNIT("QUnit"),
    JSUNIT("JsUnit"),
    JASMINE("Jasmine"),
    DOH("D.O.H");

    private String lib;
    Library(String lib){
        this.lib = lib;
    }

    @Override
    public String toString() {
        return lib;
    }
}
