package ru.otus.elena.cactuscatalogue.shell;

import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.elena.cactuscatalogue.dao.CactusDaoJdbc;
import ru.otus.elena.cactuscatalogue.domain.Cactus;
import ru.otus.elena.cactuscatalogue.dao.CactusRepository;

@ShellComponent
public class ShellCommands {

    private final CactusRepository cactusRepository;
    private final CactusDaoJdbc cactusDao;
    private final JobLauncher jobLauncher;
    private final Job migrateJob;

    @Autowired
    public ShellCommands(
            CactusRepository cactusRepository, CactusDaoJdbc cactusDao, JobLauncher jobLauncher, Job migrateJob) {
        this.cactusRepository = cactusRepository;
        this.cactusDao = cactusDao;
        this.jobLauncher = jobLauncher;
        this.migrateJob = migrateJob;
    }

    @ShellMethod("Insert")
    public String insert(
            @ShellOption String cactusname
    ) {
        cactusRepository.deleteAll();
        for (int i = 1; i < 500; i++) {
            Cactus cactus = new Cactus(i, cactusname + String.valueOf(i), i);
            cactusRepository.save(cactus);
        }
        return "saved";
    }

    @ShellMethod("Migrate")
    public String migrate() {
        cactusDao.deleteAll();
        try {
            jobLauncher.run(migrateJob, new JobParameters());
            return "migrated";
        } catch (Exception e) {
            return "has not been migreted " + e.getMessage();
        }
    }

    @ShellMethod("Find")
    public String find(
            @ShellOption long id
    ) {
        Cactus cactus = cactusDao.getById(id);
        if (cactus != null) {
            return cactus.toString();
        } else {
            return "not found";
        }
    }
}
