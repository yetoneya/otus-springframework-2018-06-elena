
package ru.otus.elena.studentiqtest.shell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.elena.studentiqtest.studentiqtest.StudentIQTest;

@ShellComponent
public class StartCommands {

    private final StudentIQTest studentIQTest;

    @Autowired
    public StartCommands(StudentIQTest studentIQTest) {
        this.studentIQTest = studentIQTest;
    }

    @ShellMethod("Translate text from one language to another.")
    public void language(
            @ShellOption String name
    ) {
        studentIQTest.start(name);
    }
}

