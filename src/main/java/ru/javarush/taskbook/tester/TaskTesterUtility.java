package ru.javarush.taskbook.tester;

import ru.javarush.taskbook.logger.LoggerWrapper;
import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.enums.Language;
import ru.javarush.taskbook.model.enums.Library;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alex Ieromenko on 24.12.14.
 * Test class.
 * It`s a complex utility that performs a series of actions with the sourceCode placed
 * in the Task that passed to utility.
 * 1) parse for a name of the class. By Java Specification the name of the class must be the
 * same with the name of the file that contains that class. If the parser cannot find class
 * it DOES NOT throw any exceptions, it passes the wrong name to the compiler, so
 * file will not compile then and compilation error will be returned to user.
 * 2) create directory and files on the hard disc.
 * 3) compile source .java files.
 * 4) iff compilation successful testing is being performed, else it removes all files and
 * directory that contains them.
 * 5) execute compiled files in the new VM with security manager turned ON.
 * 6) remove all the files and directory and return map with results.
 */
public final class TaskTesterUtility {
    private static final LoggerWrapper LOG = LoggerWrapper.get(TaskTesterUtility.class);

    private static final String TEST_FOLDER_NAME = "testTask";
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String PATH_SEPARATOR = System.getProperty("path.separator");
    private static final String LIB_PATH = System.getProperty("test_utility") == null ?
            System.getenv("TASKBOOK") + FILE_SEPARATOR + "lib" : System.getProperty("test_utility") + FILE_SEPARATOR + "lib";

    private static final String LIB_CP = StringUtil.createCP(LIB_PATH, FILE_SEPARATOR, PATH_SEPARATOR);

    private static final AtomicInteger folderCounter = new AtomicInteger(0);

    private final String folderName = "test" + (folderCounter.getAndIncrement()); // unique folder name
    private final String folderPath = LIB_PATH + FILE_SEPARATOR + TEST_FOLDER_NAME + FILE_SEPARATOR + folderName; // D:\\lib\testTask\test0
    private final String className;
    private final String testName;
    private final String sourceCode;
    private final String testSourceCode;
    private final String fileExtension;
    private boolean save;

    private final Properties prop = new Properties();

    /**
     * @param task task to test
     * @param save does task need to be saved?
     */
    private TaskTesterUtility(Task task, boolean save) {
        this.save = save;
        sourceCode = task.getSourceCode();
        testSourceCode = task.getTests();
        Library testLibrary = task.getTestEnvironment().getLib();
        Language language = task.getTestEnvironment().getLang();
        fileExtension = language.toString().toLowerCase();

        try (InputStream input = new FileInputStream(
                LIB_PATH + FILE_SEPARATOR +
                        "libProperties" + FILE_SEPARATOR +
                        language + FILE_SEPARATOR +
                        testLibrary + ".properties")) {
            prop.load(input);
        } catch (IOException e) {
            LOG.error("[TASK#" + folderName + "] IOException: ", e);
        }
        // Refactor through JavaParser
        className = StringUtil.parseName(sourceCode);
        testName = StringUtil.parseName(testSourceCode);
    }

    private static final int MAX_SIMULTANEOUS_TESTS = 30;
    private static boolean cleanedAlready = false; // clean directory before launch 1st test
    private static final AtomicInteger alreadyTestingClassesCount = new AtomicInteger(0);

    /**
     * Main test method to test user tasks.
     * !for this method to work you must read the README of the project!
     *
     * @param task task to test
     * @param save does task need to be saved?
     * @return map with answers from different services
     */
    public static Map<String, String> performTest(Task task, boolean save) {
        try {
            while (alreadyTestingClassesCount.get() >= MAX_SIMULTANEOUS_TESTS) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int i = alreadyTestingClassesCount.incrementAndGet();
            LOG.debug("CurrentClassesCount " + i);

            TaskTesterUtility utility = new TaskTesterUtility(task, save);

            // clean directory before running 1st test
            if (!cleanedAlready) {
                synchronized (TaskTesterUtility.class) {
                    if (!cleanedAlready) {
                        utility.performClean();
                        cleanedAlready = true;
                    }
                }
            }
            if (folderCounter.get() == Integer.MAX_VALUE - 1024) folderCounter.getAndSet(0);

            return utility.prepareAndRunTest();

        } finally {
            int j = alreadyTestingClassesCount.decrementAndGet();
            LOG.debug("CurrentClassesCount " + j);
        }
    }


    private final Map<String, String> results = new LinkedHashMap<>();

    /**
     * Main test method to test user tasks.
     * !for this method to work you must read the README of the project!
     *
     * @return map with answers from different services
     */
    private Map<String, String> prepareAndRunTest() {

        createFiles();

        boolean needToCompile = false;
        try {
            needToCompile = Boolean.parseBoolean(prop.getProperty("compile"));
        } catch (MissingResourceException e) {
            LOG.error("[TASK#" + folderName + "] Exception: ", e);
        }
        boolean compile = false;
        if (needToCompile) compile = compile();


        if (needToCompile && !compile) {
            removeFiles(directory);
            return results;
        } else {
            runTest();
            removeFiles(directory);
            return results;
        }
    }

    private static final int MAX_HEAP_SPACE_PER_TEST_Mb = 16;
    private static final Runtime runtime = Runtime.getRuntime();

    /**
     * for this method to work you must read the README of the project!
     */
    private void runTest() {
        String libRunner = "";
        String testLibArgs = "";
        try {
            libRunner = prop.getProperty("runner.name");
            testLibArgs = prop.getProperty("testLibArgs");
        } catch (MissingResourceException e) {
            LOG.error("[TASK#" + folderName + "] Exception: ", e);
        }

        String runMethod = "";
        if (libRunner.length() > 0) {
            // TestNG
            runMethod = String.format("%s %s %s", libRunner, testLibArgs, testName);
        } else {
            // JUnit, Spock
            runMethod = String.format("%s%s%s", folderPath, FILE_SEPARATOR, testName);
        }
        // An old command:
        //String runParameters = fileExtension + " -Djava.security.manager -Djava.security.policy=" +
        //        LIB_PATH + FILE_SEPARATOR + "test-sandbox.policy -Xmx" + MAX_HEAP_SPACE_PER_TEST_Mb + "M -cp " + LIB_CP +
        //        folderPath + " " + runMethod;

        // TODO: runMethod doesn't used! update this code later!
        // because this code work only for JUnit

        List<String> params = new ArrayList<>();
        params.add(fileExtension);          // ex: java
        params.add("-cp");                  // classpath
        params.add(LIB_CP + folderPath);    // enumerate jar libs
        // !!! NEVER DELETE THIS SECURITY MANAGER
        params.add("-Djava.security.manager");
        params.add("-Djava.security.policy=" + LIB_PATH + FILE_SEPARATOR + "test-sandbox.policy");
        params.add("-Xmx" + MAX_HEAP_SPACE_PER_TEST_Mb + "M");
        params.add(libRunner);      // ex: org.junit.runner.JUnitCore
        params.add(testName);       // ex: SolutionTest

        // Using ProcessBuilder to set new working directory
        ProcessBuilder builder = new ProcessBuilder( params );
        // !!! SET WORKING DIRECTORY where user can WRITE or READ files
        // this is where you set the root folder for the executable to run with
        builder.directory( new File( folderPath ).getAbsoluteFile() ); // ex: %TASKBOOK%/lib/testTask/task42

        //for (String item : builder.command())
        //    LOG.info("param: " + item);

        LOG.debug("params: " + builder.command());
        //LOG.info("[TASK#" + folderName + "] EXECUTE:" + runParameters);

        try {
            Process process = builder.start();
            //Process process = runtime.exec(runParameters);
//            Process process = Runtime.getRuntime().exec(runParameters);

            // any error message?
            StreamTaker errorTaker = new StreamTaker(process.getErrorStream());
            // any output?
            StreamTaker outputTaker = new StreamTaker(process.getInputStream());

            BufferedReader outputReader = null;
            BufferedReader errorReader = null;
            try {
                errorReader = errorTaker.call();
                outputReader = outputTaker.call();
            } catch (Exception e) {
                LOG.error("[TASK#" + folderName + "] Exception: ", e);
            }
            // Process.waitFor() with timeout
            int exitVal = -1;
            int i = 0;
            boolean deadYet = false;
            try {
                do {
                    Thread.sleep(50);
                    try {
                        exitVal = process.exitValue();
                        deadYet = true;
                    } catch (IllegalThreadStateException e) {
                        if (++i >= 200) process.destroy();
                    }
                } while (!deadYet);
            } catch (InterruptedException e) {
                LOG.error("[TASK#" + folderName + "] Exception: ", e);
            }

            LOG.info("[TASK#" + folderName + "] Process exitValue: " + exitVal);

            String line;
            String lineErr;
            StringBuilder sb = new StringBuilder();
            StringBuilder sbErr = new StringBuilder();

            while ((line = outputReader != null ? outputReader.readLine() : null) != null) {
                sb.append(line).append("\n");
            }
            while ((lineErr = errorReader != null ? errorReader.readLine() : null) != null) {
                sbErr.append(lineErr).append("\n");
            }
            if (sbErr.length() > 0)
                LOG.debug("[TASK#" + folderName + "] Testing error:\n" + sbErr.toString());
            if (sb.length() > 0)
                LOG.debug("[TASK#" + folderName + "] Test process output:\n" + sb.toString()); // for debugging only

            LinkedHashMap<String, String> parsedTestResult =
                    StringUtil.parseResults(sb.toString(), sbErr.toString(), save, LIB_PATH, exitVal, prop);
            results.putAll(parsedTestResult);

        } catch (IOException e) {
            LOG.error("[TASK#" + folderName + "] Exception: ", e);
        }
    }

    private static final File root = new File(LIB_PATH + FILE_SEPARATOR + TEST_FOLDER_NAME);
    private File source;
    private File test;
    private File directory;

    private void createFiles() {
        source = new File(root, folderName + FILE_SEPARATOR + className + "." + fileExtension);
        test = new File(root, folderName + FILE_SEPARATOR + testName + "." + fileExtension);
        directory = new File(root, folderName + FILE_SEPARATOR);

        boolean success = directory.mkdir();
        if (!success) LOG.error("[TASK#" + folderName + "] Cannot create directory: " + directory.getAbsolutePath());

        boolean sourceCreated = false;
        boolean testCreated = false;
        if (!source.exists() && !test.exists()) {
            try {
                sourceCreated = source.createNewFile();
                testCreated = test.createNewFile();
            } catch (IOException e) {
                LOG.error("[TASK#" + folderName + "] Exception: ", e);
            }

            try (BufferedOutputStream sourceOutput = new BufferedOutputStream(new FileOutputStream(source));
                 BufferedOutputStream testOutput = new BufferedOutputStream(new FileOutputStream(test))
            ) {
                if (sourceCreated) {
                    sourceOutput.write(sourceCode.getBytes());
                } else {
                    LOG.error("[TASK#" + folderName + "] Cannot write source code file does not exist!");
                }

                if (testCreated) {
                    testOutput.write(testSourceCode.getBytes());
                } else {
                    LOG.error("[TASK#" + folderName + "] Cannot write source code file does not exist!");
                }
            } catch (IOException e) {
                LOG.error("[TASK#" + folderName + "] Exception: ", e);
            }
        }
    }


    private static final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private static final StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

    private boolean compile() {
        File[] arr = {source, test};
        List<String> optionList = new ArrayList<>();
        String[] options = {};
        try {
            options = prop.getProperty("compiler.options").split(",");
        } catch (MissingResourceException e) {
            LOG.error("[TASK#" + folderName + "] Exception: ", e);
        }
        optionList.addAll(Arrays.asList("-classpath", LIB_CP));
        optionList.addAll(Arrays.asList(options)); // for additional options

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(
                Arrays.asList(arr));

        // CATCH compilation errors
        PrintStream saveErr = System.err;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(stream);
        System.setErr(ps);

        Boolean call = compiler.getTask(null, fileManager, null, optionList, null, compilationUnits).call();
        try {
            fileManager.close();
        } catch (IOException e) {
            LOG.error("[TASK#" + folderName + "] Exception: ", e);
        }

        // PRINT compilation errors
        System.setErr(saveErr);
        if (stream.size() > 0)
            LOG.error("[TASK#" + folderName + "] Compilation error:\n" + stream);

        if (call.equals(true)) {
            LOG.info("[TASK#" + folderName + "] Compilation: OK");
            results.put("compilation", "OK");
        } else {
            LOG.info("[TASK#" + folderName + "] Compilation: Fail");
            results.put("compilation", "FAILURE");
            if (save)
                results.put("not_saved", "cannot_compile");
            results.put("answer", "Compilation failed!");
        }
        return call;
    }

    private void removeFiles(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    removeFiles(f);
                } else {
                    boolean success = f.delete();
                    if (!success) LOG.error("[TASK#" + folderName + "] Cannot delete file: " + f.getAbsolutePath());
                }
            }
        }
        boolean success = dir.delete();
        if (!success) LOG.error("[TASK#" + folderName + "] Cannot delete folder: " + dir.getAbsolutePath());
    }

    /**
     * clean test directory after redeployment
     */
    private void performClean() {
        if (!root.exists() || !root.isDirectory()) {
            boolean success = root.mkdir();
            if (success) {
                LOG.info("[TASK#" + folderName + "] Restored root folder");
            } else {
                LOG.error("[TASK#" + folderName + "] Failed to restore root folder!");
            }
        } else {
            LOG.info("[TASK#" + folderName + "] Cleaning test folder");
            File[] directories = root.listFiles();
            if (directories != null) {
                for (File d : directories) {
                    removeFiles(d);
                }
            }
        }
    }
}



