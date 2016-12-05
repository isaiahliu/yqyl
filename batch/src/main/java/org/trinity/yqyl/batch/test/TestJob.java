package org.trinity.yqyl.batch.test;

import java.util.Calendar;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class TestJob {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("testJob").incrementer(new RunIdIncrementer()).listener(listener()).flow(testStep()).end().build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListener() {
            @Override
            public void afterJob(final JobExecution jobExecution) {
            }

            @Override
            public void beforeJob(final JobExecution jobExecution) {
                System.out.println(Calendar.getInstance().get(Calendar.SECOND));
            }
        };
    }

    @Bean
    public TestProcessor processor() {
        return new TestProcessor();
    }

    @Bean
    public TestReader reader() {
        return new TestReader();
    }

    // @Scheduled(cron = "0,15,30,45 * * * * *")
    @Scheduled(fixedDelay = 5000)
    public void startJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException,
            JobParametersInvalidException {
        final JobParameters jobParameters = new JobParameters();
        jobParameters.getParameters().put("timestamp", new JobParameter(new Date()));
        jobLauncher.run(importUserJob(), jobParameters);
    }

    @Bean
    public Step testStep() {
        return stepBuilderFactory.get("step1").<String, String> chunk(1).reader(reader()).processor(processor()).writer(writer())
                .allowStartIfComplete(true).build();
    }

    @Bean
    public TestWriter writer() {
        return new TestWriter();
    }
}
