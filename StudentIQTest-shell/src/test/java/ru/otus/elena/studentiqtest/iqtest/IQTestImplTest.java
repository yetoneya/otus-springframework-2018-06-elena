
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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties={InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED+"=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED+"=false"})
public class IQTestImplTest {
    
    public IQTestImplTest() {
    }
    @Autowired
    private IQTest iqt;

    @Test
    public void testIQtest() throws IOException{
        try {
            iqt.setLanguage("ru");
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
        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
