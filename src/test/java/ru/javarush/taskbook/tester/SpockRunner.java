package ru.javarush.taskbook.tester;

import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.enums.TestEnvironment;

import java.util.Map;

/**
 * Created by Alex Ieromenko on 19.01.15.
 * Just a main method
 */
public class SpockRunner {
    public static void main(String[] args) {
        Task task = new Task();
        task.setSourceCode(classCode);
        task.setTests(testCode);
        task.setTestEnvironment(TestEnvironment.GROOVY_SPOCK);
        Map<String, String> testString = TaskTesterUtility.performTest(task, false);
        System.out.println("TEST RESULTS\n" + testString);

//        Multitest m = new Multitest();
//        Multitest m2 = new Multitest();
//        Multitest m3 = new Multitest();
//        Multitest m4 = new Multitest();
//        Multitest m5 = new Multitest();
//        Multitest m6 = new Multitest();
//        Multitest m7 = new Multitest();
//        Multitest m8 = new Multitest();
//        Multitest m9 = new Multitest();
//        Multitest m10 = new Multitest();
//        m.start();
//        m2.start();
//        m3.start();
//        m4.start();
//        m5.start();
//        m6.start();
//        m7.start();
//        m8.start();
//        m9.start();
//        m10.start();
    }

    private static class Multitest extends Thread{
        @Override
        public void run() {
            Task task = new Task();
            task.setSourceCode(classCode);
            task.setTests(testCode);
            Map<String, String> testString = TaskTesterUtility.performTest(task, false);
            System.out.println("TEST RESULTS\n" + testString);
        }
    }

    private static String classCode =
            "class Publisher {\n" +
                    "  def subscribers = []\n" +
                    "\n" +
                    "  def send(event) {\n" +
                    "    subscribers.each {\n" +
                    "      try {\n" +
                    "        it.receive(event)\n" +
                    "      } catch (Exception e) {}\n" +
                    "    }\n" +
                    "  }\n" +
                    "}\n" +
                    "\n" +
                    "interface Subscriber {\n" +
                    "  def receive(event)\n" +
                    "}";


    private static String testCode =
            "import spock.lang.Specification\n" +
                    "public class PublisherSpec extends Specification {\n" +
                    "  def pub = new Publisher()\n" +
                    "  def sub1 = Mock(Subscriber)\n" +
                    "  def sub2 = Mock(Subscriber)\n" +
                    "\n" +
                    "  def setup() {\n" +
                    "    pub.subscribers << sub1 << sub2\n" +
                    "  }\n" +
                    "\n" +
                    "  def \"delivers events to all subscribers\"() {\n" +
                    "    when:\n" +
                    "    pub.send(\"event\")\n" +
                    "\n" +
                    "    then:\n" +
                    "    1 * sub1.receive(\"event\")\n" +
                    "    1 * sub2.receive(\"event\")\n" +
                    "  }\n" +
                    "\n" +
                    "  def \"can cope with misbehaving subscribers\"() {\n" +
                    "    sub1.receive(_) >> { throw new Exception() }\n" +
                    "\n" +
                    "    when:\n" +
                    "    pub.send(\"event1\")\n" +
                    "    pub.send(\"event2\")\n" +
                    "\n" +
                    "    then:\n" +
                    "    1 * sub2.receive(\"event1\")\n" +
                    "    1 * sub2.receive(\"event2\")\n" +
                    "  }\n" +
                    "}";

}
