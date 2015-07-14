package testTask;
public class InnerClass {
    public InnerClass() {
        System.out.println("INSIDE INNERCLASS CONSTRUCTOR");
    }
 static { System.out.println("TASK LOADED"); }
     public static void main(String[] args) {
        System.out.println("HURRA");
    }
    public static class TestThread {  }
}