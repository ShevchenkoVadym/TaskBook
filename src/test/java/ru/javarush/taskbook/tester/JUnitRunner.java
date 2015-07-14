package ru.javarush.taskbook.tester;

import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.enums.TestEnvironment;

import java.util.Map;

/**
 * Created by Alex Ieromenko on 25.01.15.
 */
public class JUnitRunner {
        public static void main(String[] args) {
            Task task = new Task();
            task.setSourceCode(classCode);
            task.setTests(testCode);
            task.setTestEnvironment(TestEnvironment.JAVA_JUNIT);
            Map<String, String> testString = TaskTesterUtility.performTest(task, false);
            System.out.println("TEST RESULTS\n" + testString);

            Map<String, String> testString1 = TaskTesterUtility.performTest(task, false);
            System.out.println("TEST RESULTS\n" + testString1);
        }

    private static String classCode = "public class Solution {\n" +
            "    public static void main(String[] args) {\n" +
            "\n" +
            "       // Input your code here\n" +
            "        for (int i = 0;i < 100 ; i++) {\n" +
            "            System.out.println(\"Я никогда не буду работать за копейки. Амиго\");\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "}";

    private static String testCode = "import org.junit.After;\n" +
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
            "    @Test\n" +
            "    public void secondTestMain() throws Exception {\n" +
            "        Solution.main(null);\n" +
            "        result = out.toString().split(\"\\\\r?\\\\n\");\n" +
            "        assertEquals(100, result.length);\n" +
            "    }\n" +
            "}";

}
