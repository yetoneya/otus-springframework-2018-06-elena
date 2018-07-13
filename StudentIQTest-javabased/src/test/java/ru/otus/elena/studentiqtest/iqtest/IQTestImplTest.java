
package ru.otus.elena.studentiqtest.iqtest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.elena.studentiqtest.Main;

public class IQTestImplTest {
    
    public IQTestImplTest() {
    }

    @Test
    public void testIQtest() throws IOException{
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
            IQTestImpl iqt=context.getBean(IQTestImpl.class);
            Method methodReadCSV = IQTestImpl.class.getDeclaredMethod("readCSV");
            methodReadCSV.setAccessible(true);
            Map<String, Integer> map = (Map<String, Integer>) methodReadCSV.invoke(iqt);
            ArrayList<String>numbers=new ArrayList<>();
            map.forEach((k, v) -> {
                System.out.println(k + "=" + v);
                numbers.add(String.valueOf(v));
            });
            Path path=Paths.get("src/main/resources/test.txt");
            Files.write(path, numbers, Charset.forName("UTF-8"));            
            Scanner scanner=new Scanner(path);            
            Method methodDoTest = IQTestImpl.class.getDeclaredMethod("doTest", Map.class, Scanner.class);
            methodDoTest.setAccessible(true);
            int result=(int) methodDoTest.invoke(iqt, map, scanner);
            System.out.println("result="+result);
            context.close();
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
