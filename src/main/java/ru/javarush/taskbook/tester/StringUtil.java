package ru.javarush.taskbook.tester;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alex Ieromenko on 13.01.15.
 * parse strings
 *
 * @version 0.2 "primitive two"
 */
final class StringUtil {

    private static Logger LOG = LoggerFactory.getLogger(StringUtil.class);

    /**
     * parse name of source code class
     * ver 0.2
     * <p>
     * One line comments received from frontend always must have \n at end of the line
     *
     * @param sourceCode code to parse, for example: "class \\it's comment\n Solution {}"
     * @return name
     */
    public static String parseName(String sourceCode) {

        String className = "";

        // literal \  in RegExp formed by \\
        // literal \n in ReqExp formed by \\n
        String result = sourceCode.replaceAll("//.*\\\\n", "").replace("[\\r]?\\n", "").replace("\\t", "");

        InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));

        try {
//            https://code.google.com/p/javaparser
            CompilationUnit cu = JavaParser.parse(stream, StandardCharsets.UTF_8.toString(), true);
            className = cu.getTypes().get(0).getName();
            LOG.info("Parsed class name is: {}", className);
        } catch (Throwable e) {
//            LOG.error("Can't parse name of the file", e);
//            throw new IllegalArgumentException("Can't parse name of the file", e);
            String[] arr = sourceCode.split("[ ]+|[\\r?\\n]+");
            int index = -1;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("class")) {
                    index = i;
                    break;
                }
            }
            className = arr[index + 1];
        }

        return className;
    }

    /**
     * parse JUnit results
     *
     * @param sysout  normal stream output
     * @param syserr  error stream output
     * @param saves   check for message for TaskRest.save() method
     * @param libPath value needed to remove full path from answer
     * @param exitVal exit value of the testing process
     * @param props
     * @return parsed results
     */
    public static LinkedHashMap<String, String> parseResults(
            String sysout, String syserr, boolean saves, String libPath, int exitVal, Properties props) {
        LinkedHashMap<String, String> res = new LinkedHashMap<>();
        String testOK = props.getProperty("testOK.regexp");
        String testNotFound = props.getProperty("TestNotFound.regexp");
        String testFail = props.getProperty("testFail.regexp");
        Matcher mOk = Pattern.compile(testOK).matcher(sysout);
        Matcher mNotFind = Pattern.compile(testNotFound).matcher(sysout);
        Matcher mFail = Pattern.compile(testFail).matcher(sysout);
        if (mOk.find()) {
            res.put("tests", "OK");
            if (saves)
                res.put("save", "OK");
            res.put("answer", "Solution was passed");
        } else if (mFail.find()) {
            res.put("tests", "FAILURE");
            if (saves)
                res.put("not_saved", "test_fail");
            res.put("answer", "Solution has not passed tests");
        } else if (mNotFind.find()) {
            res.put("tests", "FAILURE");
            if (saves)
                res.put("not_saved", "test_fail");
            res.put("answer", "Cannot find test class");
        } else if (exitVal != 0) {
            res.put("tests", "FAILURE");
            if (saves)
                res.put("not_saved", "test_fail");
            res.put("answer", "Process is interrupted due to timeout or other error");
        } else {
            res.put("tests", "Error");
            if (saves)
                res.put("not_saved", "test_error");
            res.put("answer", syserr.replaceAll(libPath, ""));
            res.put("answer", sysout.replaceAll(libPath, ""));
        }
        return res;
    }

    /**
     * Create classpath of all libraries for tester utility by detection of their files in /lib directory.
     *
     * @param libPath       path to the /lib directory
     * @param fileSeparator OS dependent system file separator
     * @param pathSeparator OS dependent system path separator
     * @return classpath of all detected jars
     */
    public static String createCP(String libPath, String fileSeparator, String pathSeparator) {
        File dir = new File(libPath);
        String[] jars = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        });
        StringBuilder sb = new StringBuilder();
        for (String s : jars) {
            sb.append(libPath).
                    append(fileSeparator).
                    append(s).
                    append(pathSeparator);
        }
        return sb.toString();
    }
}