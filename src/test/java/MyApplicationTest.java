import com.kabaiye.controller.UserController;
import com.kabaiye.myInterface.Admin;
import com.kabaiye.util.AnnotationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class MyApplicationTest {

    @Test
    public void testAnnotationUtil2() throws Exception {
        Class<?>[] controllerClasses = {UserController.class};
        String[] res = AnnotationUtil.scanPackage(controllerClasses, Admin.class);
        System.out.println(Arrays.toString(res));
    }

    @Test
    public void testPath() throws IOException {
        String path = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath();
        ClassPathResource classPathResource = new ClassPathResource("static");
        String resource = classPathResource.getURL().getPath();
        System.out.println(System.getProperty("user.dir"));
        System.out.println(resource);
    }
}
