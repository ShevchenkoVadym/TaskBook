package ru.javarush.taskbook.tester;

import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.enums.TestEnvironment;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alex Ieromenko on 19.01.15.
 * Just a main method
 */
public class MultithreadJUnitRunner {
    public static void main(String[] args) throws InterruptedException {
//        Task task = new Task();
//        Task task1 = new Task();
//        task.setSourceCode(classCode);
//        task.setTests(testCode);
//        task1.setSourceCode(classCode1);
//        task1.setTests(testCode1);
//        TaskTesterUtility utility = new TaskTesterUtility();
//        Map<String, String> testString = utility.test(task, false);
//        Map<String, String> testString1 = utility.test(task1, false);
//        System.out.println("TEST RESULTS\n" + testString);
//        System.out.println("TEST 1 RESULTS\n" + testString1);
        long i = System.nanoTime();

        Multitest m = new Multitest();
        Multitest m2 = new Multitest();
        Multitest m3 = new Multitest();
        Multitest m4 = new Multitest();
        Multitest m5 = new Multitest();
        Multitest m6 = new Multitest();
        Multitest m7 = new Multitest();
        Multitest m8 = new Multitest();
        Multitest m9 = new Multitest();
        Multitest m10 = new Multitest();
        m.start();
        m2.start();
        m3.start();
        m4.start();
        m5.start();
        m6.start();
        m7.start();
        m8.start();
        m9.start();
        m10.start();
        m.join();
        m2.join();
        m3.join();
        m4.join();
        m5.join();
        m6.join();
        m7.join();
        m8.join();
        m9.join();
        m10.join();
        i = (System.nanoTime() - i) / 1000000;
        System.out.println("SPEEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDd: " + i);
    }

    private static class Multitest extends Thread{
        @Override
        public void run() {
            Task task = new Task();
            task.setSourceCode(classCode1);
            task.setTests(testCode1);
            task.setTestEnvironment(TestEnvironment.JAVA_JUNIT);
            Map<String, String> testString = TaskTesterUtility.performTest(task, false);

            System.out.println("TEST RESULTS\n" + testString);

            Task task1 = new Task();
            task1.setSourceCode(classCode);
            task1.setTests(testCode);
            task1.setTestEnvironment(TestEnvironment.JAVA_JUNIT);
            Map<String, String> testString1 = TaskTesterUtility.performTest(task1, false);

            System.out.println("TEST RESULTS\n" + testString1);

            Map<String, String> testString2 = TaskTesterUtility.performTest(task, false);

            System.out.println("TEST RESULTS\n" + testString2);

            Map<String, String> testString3 = TaskTesterUtility.performTest(task1, false);

            System.out.println("TEST RESULTS\n" + testString3);
        }
    }

    private static String classCode =
            "public class InnerClass {\n" +
                    "    public InnerClass() {\n" +
                    "        System.out.println(\"INSIDE INNERCLASS CONSTRUCTOR\");\n" +
                    "    }\n" +
                    " static { System.out.println(\"TASK LOADED\"); }\n" +
                    "     public static void main(String[] args) {\n" +
                    "        System.out.println(\"HURRA\");\n" +
                    "    }\n" +
                    "    public static class TestThread {  }\n" +
                    "}";


    private static String testCode =
            "import org.junit.Assert;\n" +
                    "import org.junit.Test;\n" +
                    "import java.lang.reflect.Field;\n" +
                    "import org.junit.Ignore;\n" +
                    "\n" +
                    "public class InnerClassTest {\n" +
                    " static { System.out.println(\"TEST LOADED\"); }\n" +
                    "    Class[] c = InnerClass.class.getDeclaredClasses();\n" +
                    "    Field[] f = InnerClass.class.getDeclaredFields();\n" +
                    "\n" +
                    "    @Test\n" +
                    "    public void testMain() throws Exception {\n" +
                    "        Assert.assertEquals(0, f.length);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Test\n" +
                    "    public void testClass() throws Exception {\n" +
                    "        Assert.assertTrue(c[0].getSimpleName().equals(\"TestThread\"));\n" +
                    "    }\n" +
                    "}";

    private static String classCode1 = "public class Solution {\n" +
            "    public static void main(String[] args) {\n" +
            "\n" +
            "       // Input your code here\n" +
            "        for (int i = 0;i < 100 ; i++) {\n" +
            "            System.out.println(\"Я никогда не буду работать за копейки. Амиго\");\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "}";

    private static String testCode1 = "import org.junit.After;\n" +
            "import org.junit.Before;\n" +
            "import org.junit.Test;\n" +
            "\n" +
            "import java.io.ByteArrayOutputStream;\n" +
            "import java.io.PrintStream;\n" +
            "\n" +
            "import static org.junit.Assert.assertEquals;\n" +
            "\n" +
            "public class SolutionTest {\n" +
            "    //catch console output\n" +
            "    PrintStream ps;  // save ref to sout\n" +
            "    ByteArrayOutputStream out;  // create  byte array for info\n" +
            "    PrintStream printStream;\n" +
            "\n" +
            "    String[] result; // take result of console output\n" +
            "\n" +
            "    @Before\n" +
            "    public void setUp() throws Exception {\n" +
            "        //output data\n" +
            "        ps = System.out;  // save ref to sout\n" +
            "\n" +
            "        out = new ByteArrayOutputStream();  // create  byte array for info\n" +
            "        printStream = new PrintStream(out);\n" +
            "\n" +
            "        System.setOut(printStream);\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    @After\n" +
            "    public void tearDown() throws Exception {\n" +
            "        System.setOut(ps);\n" +
            "    }\n" +
            "\n" +
            "    @Test\n" +
            "    public void testMain() throws Exception {\n" +
            "        Solution.main(null);\n" +
            "        result = out.toString().split(\"\\\\r?\\\\n\");\n" +
            "        assertEquals(result[0], \"Я никогда не буду работать за копейки. Амиго\");\n" +
            "\n" +
            "    }\n" +
            "\n" +
//            "    @Test\n" +
//            "    public void secondTestMain() throws Exception {\n" +
//            "        Solution.main(null);\n" +
//            "        result = out.toString().split(\"\\\\r?\\\\n\");\n" +
//            "        assertEquals(100, result.length);\n" +
//            "    }\n" +
            "}";
}
