
import com.kabaiye.util.ClassUtils;
import org.junit.jupiter.api.Test;


import java.io.IOException;


public class TestClass{

    @Test
    public void test2() throws IOException {
        System.out.println(ClassUtils.getClasses("com.kabaiye.controller"));
    }
}





