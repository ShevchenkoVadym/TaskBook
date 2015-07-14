package testTask;
import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Field;
import org.junit.Ignore;

public class InnerClassTest {
 static { System.out.println("TEST LOADED"); }
    Class[] c = InnerClass.class.getDeclaredClasses();
    Field[] f = InnerClass.class.getDeclaredFields();

    @Test
    public void testMain() throws Exception {
        Assert.assertEquals(0, f.length);
    }

    @Test
    public void testClass() throws Exception {
        Assert.assertTrue(c[0].getSimpleName().equals("TestThread"));
    }
}